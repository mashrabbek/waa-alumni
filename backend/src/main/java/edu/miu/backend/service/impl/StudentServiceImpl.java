package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.backend.dto.AddressDto;
import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
import edu.miu.backend.entity.Address;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Student;
import edu.miu.backend.enums.BucketName;
import edu.miu.backend.repo.DepartmentRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private AmazonS3 amazonS3Client;
    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, DepartmentRepo departmentRepo, ModelMapper modelMapper, AmazonS3 amazonS3Client,ObjectMapper objectMapper) {
        this.studentRepo = studentRepo;
        this.departmentRepo = departmentRepo;
        this.modelMapper = modelMapper;
        this.amazonS3Client = amazonS3Client;
        this.objectMapper = objectMapper;
    }

    private String AWS_URL = String.format("https://%s.s3.amazonaws.com/", BucketName.ALUMNI.getBucketName());

    @Override
    public StudentResponseDto findByUsername(String username) {
      Optional<Student> student = studentRepo.findByUsername(username);
      if (!student.isPresent()){ return null;}

      StudentResponseDto studentDto = modelMapper.map(student.get(), StudentResponseDto.class);
      String address = null;
      if (student.get().getAddress() != null) {
          try {
              address = objectMapper.writeValueAsString(modelMapper.map(student.get().getAddress(), AddressDto.class));
          } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
          }
      }
        studentDto.setAddress(address);
      if (student.get().getMajor() != null) {
          studentDto.setMajorId(student.get().getMajor().getId());
      }
        return studentDto;
    }

    @Override
    public List<StudentResponseDto> findAll() {
        List<Student> students = (List<Student>) studentRepo.findAll() ;
        return students.stream().map(student -> modelMapper.map(student, StudentResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public Student findById(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public StudentResponseDto save(StudentDto studentDto) throws SQLException {

        AddressDto address = null;
        try {
            address = objectMapper.readValue(studentDto.getAddress(), AddressDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (studentRepo.findByUsername(studentDto.getUsername()).isPresent()){
            throw new SQLException("Student with username " + studentDto.getUsername()+ " already exists");
        }
        Student student = modelMapper.map(studentDto, Student.class);
        if (studentDto.getMajorId() != null){
            Department department = departmentRepo.findById(studentDto.getMajorId()).get();
            student.setMajor(department);
        }

        if (studentDto.getFile() != null) {
            String cvUrl = uploadFileToAWSAndGetUrl(studentDto.getFile());
            student.setCv(cvUrl);
        }

        student.setDeleted(Boolean.FALSE);
        student.setAddress(modelMapper.map(address, Address.class));

        Student savedStudent = studentRepo.save(student);
        StudentResponseDto studentResponseDto = modelMapper.map(savedStudent, StudentResponseDto.class);
        if(student.getMajor() != null) {
            studentResponseDto.setMajorId(savedStudent.getMajor().getId());
        }
        return studentResponseDto;
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    public StudentResponseDto update(StudentDto studentDto, String username) throws Exception {

        AddressDto address = null;
        try {
            address = objectMapper.readValue(studentDto.getAddress(), AddressDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Optional<Student> studentRef = studentRepo.findByUsername(username);
        if (!studentRef.isPresent()){
             return save(studentDto);
        }
        Student student = studentRef.get();
        Student stMapped = modelMapper.map(studentDto, Student.class);
        Department department = departmentRepo.findById(studentDto.getMajorId()).orElseThrow(()-> new Exception("Department not found!"));

        student.setGpa(stMapped.getGpa());
        student.setMajor(department);
        student.setAddress(modelMapper.map(address, Address.class));

        if (studentDto.getFile() != null) {
            String cvUrl = uploadFileToAWSAndGetUrl(studentDto.getFile());
            student.setCv(cvUrl);
        }

        student.setDeleted(Boolean.FALSE);

        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        studentResponseDto.setMajorId(department.getId());
        return studentResponseDto;
    }

    @Override
    public void deleteByUsername(String username) {
        studentRepo.deleteByUsername(username);
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private String uploadFileToAWSAndGetUrl(MultipartFile multipartFile){

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        String keyName = generateFileName(multipartFile);
        try {
            var result = amazonS3Client
                    .putObject(BucketName.ALUMNI.getBucketName(), keyName, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AWS_URL.concat(keyName);
    }

}
