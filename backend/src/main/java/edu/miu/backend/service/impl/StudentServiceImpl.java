package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.StudentResponseDto;
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
    private AmazonS3 amazonS3Client;
    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, DepartmentRepo departmentRepo, ModelMapper modelMapper, AmazonS3 amazonS3Client) {
        this.studentRepo = studentRepo;
        this.departmentRepo = departmentRepo;
        this.modelMapper = modelMapper;
        this.amazonS3Client = amazonS3Client;
    }

    private String AWS_URL = String.format("https://%s.s3.amazonaws.com/", BucketName.ALUMNI.getBucketName());

    @Override
    public Student findByUsername(String username) {
        return studentRepo.findByUsername(username).orElse(null);
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
        if (studentRepo.findByUsername(studentDto.getUsername()).isPresent()){
            throw new SQLException("Student with username " + studentDto.getUsername()+ " already exists");
        }
        Student student = modelMapper.map(studentDto, Student.class);
        if (studentDto.getMajorId() != null){
            Department department = departmentRepo.findById(studentDto.getMajorId()).get();
            student.setMajor(department);
        }
        String cvUrl = uploadFileToAWSAndGetUrl(studentDto.getFile());
        student.setCv(cvUrl);

        StudentResponseDto studentResponseDto = modelMapper.map(studentRepo.save(student), StudentResponseDto.class);
        studentResponseDto.setMajorId(student.getMajor().getId());
        return studentResponseDto;
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    public StudentResponseDto update(StudentDto studentDto, String username) throws Exception {
        Optional<Student> studentRef = studentRepo.findByUsername(username);
        if (!studentRef.isPresent()){
             return save(studentDto);
        }
        Student student = studentRef.get();
        Student stMapped = modelMapper.map(studentDto, Student.class);
        Department department = departmentRepo.findById(studentDto.getMajorId()).orElseThrow(()-> new Exception("Department not found!"));

        student.setGpa(stMapped.getGpa());
        student.setMajor(department);
        student.setAddress(stMapped.getAddress());

        String cvUrl = uploadFileToAWSAndGetUrl(studentDto.getFile());
        student.setCv(cvUrl);

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
//        try {
//            var result = amazonS3Client
//                    .putObject(BucketName.ALUMNI.getBucketName(), keyName, multipartFile.getInputStream(), metadata);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return AWS_URL.concat(keyName);
    }

}
