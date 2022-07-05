package edu.miu.backend.service.impl;

import edu.miu.backend.dto.StudentDto;
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
        return studentRepo.findById(id).get();
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        Department department = departmentRepo.findById(studentDto.getMajorId()).get();
        student.setMajor(department);
        return modelMapper.map(studentRepo.save(student), StudentDto.class);
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    @Transactional
    public StudentDto update(StudentDto studentDto, int id) throws Exception {
        Student st = studentRepo.findById(id).orElseGet(null);
        Student stMapped = modelMapper.map(studentDto, Student.class);

        if(st == null){
            throw new Exception("Student not found!");
        }
        Department depRef = departmentRepo.findById(studentDto.getMajorId()).get();

        if(depRef == null){
            throw new Exception("Department not found!");
        }
        st.setEmail(stMapped.getEmail());
        st.setFirstName(stMapped.getFirstName());
        st.setLastName(stMapped.getLastName());
        st.setGpa(stMapped.getGpa());
        st.setMajor(depRef);
        st.setAddress(stMapped.getAddress());

        return studentDto;
    }


}
