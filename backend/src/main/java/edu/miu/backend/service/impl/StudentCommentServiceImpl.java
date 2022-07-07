package edu.miu.backend.service.impl;
import edu.miu.backend.dto.StudentCommentDto;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.StudentComment;
import edu.miu.backend.repo.FacultyRepo;
import edu.miu.backend.repo.StudentCommentRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.service.StudentCommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentCommentServiceImpl implements StudentCommentService {
    
    private final StudentCommentRepo studentCommentRepo;
    private final StudentRepo studentRepo;
    private final FacultyRepo facultyRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentCommentDto> findAll() {
        List<StudentComment> tags = (List<StudentComment>) studentCommentRepo.findAll();
        var result = tags.stream().map(tag -> modelMapper.map(tag, StudentCommentDto.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public StudentCommentDto findById(int id) {
        StudentComment tag = studentCommentRepo.findById(id).orElse(null);
        return modelMapper.map(tag, StudentCommentDto.class);
    }

    @Override
    public StudentCommentDto save(StudentCommentDto tagDto) {
        Student student = studentRepo.findById(tagDto.getStudentId()).get();
        Faculty faculty = facultyRepo.findById(tagDto.getFacultyId()).get();

        StudentComment studentComment = modelMapper.map(tagDto, StudentComment.class);
        studentComment.setStudent(student); studentComment.setFaculty(faculty);

        var savedStudentCommentDto = modelMapper.map(studentCommentRepo.save(studentComment), StudentCommentDto.class);
        savedStudentCommentDto.setStudentId(student.getId()); savedStudentCommentDto.setFacultyId(faculty.getId());

        return savedStudentCommentDto;
    }

    @Override
    public void delete(int id) {
        studentCommentRepo.deleteById(id);
    }

    @Override
    public StudentCommentDto update(StudentCommentDto tagDto, int id) throws Exception {
        return null;
    }
    
}