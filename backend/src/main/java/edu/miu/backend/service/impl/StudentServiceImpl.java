package edu.miu.backend.service.impl;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;


    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepo.findAll();
    }

    @Override
    public Student findByID(int id) {
        return studentRepo.findById(id).get();
    }

    @Override
    public Student save(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    public Student update(StudentDto student, int id) throws Exception {
        Student st = studentRepo.findById(id).orElseGet(null);
        if(st == null){
            throw new Exception("Student not found");
        }
//        st.setGpa(student);
//        st.setMajor(student.getMajor());
//        st.setActive();
        return st;
    }


}
