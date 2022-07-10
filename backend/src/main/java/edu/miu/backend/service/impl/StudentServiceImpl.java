package edu.miu.backend.service.impl;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.StudentResponseDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repo.DepartmentRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepo.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        if (studentDto.getMajorId() != null){
            Department department = departmentRepo.findById(studentDto.getMajorId()).get();
            student.setMajor(department);
        }
        return modelMapper.map(studentRepo.save(student), StudentDto.class);
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    @Transactional
    public StudentResponseDto update(StudentDto studentDto, int id) throws Exception {
        Student student = studentRepo.findById(id).orElseThrow(()-> new Exception("Student not found!"));
        Student stMapped = modelMapper.map(studentDto, Student.class);
        Department department = departmentRepo.findById(studentDto.getMajorId()).orElseThrow(()-> new Exception("Department not found!"));

        student.setEmail(stMapped.getEmail());
        student.setFirstName(stMapped.getFirstName());
        student.setLastName(stMapped.getLastName());
        student.setGpa(stMapped.getGpa());
        student.setMajor(department);
        student.setAddress(stMapped.getAddress());
        student.setCv("http://aws.sardor/sarodbek/Resume_Apple.pdf");

        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        studentResponseDto.setMajorId(department.getId());
        return studentResponseDto;
    }


}
