package edu.miu.backend.service.impl;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repo.JobHistoryRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepo jobHistoryRepo;
    private final StudentRepo studentRepo;
    private final TagRepo tagRepo;
    private final ModelMapper modelMapper;


    @Override
    public List<JobHistoryDto> findAll() {
        List<JobHistory> histories =  (List<JobHistory>) jobHistoryRepo.findAll();
        return histories.stream().map(jobHistory -> modelMapper.map(jobHistory, JobHistoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public JobHistoryDto findById(int id) {
        return modelMapper.map(jobHistoryRepo.findById(id).get(), JobHistoryDto.class);
    }

    @Override
    public void deleteByID(int id) {
        jobHistoryRepo.deleteById(id);
    }

    @Override
    public JobHistoryDto update(JobHistoryDto jobHistoryDto, String username) {
//        TODO:
//        jobHistory.setCompanyName(jobHistoryDto.getCompanyName());
//        jobHistory.setReasonToLeave(jobHistoryDto.getReasonToLeave());
//        jobHistory.setStartDate(jobHistoryDto.getStartDate());
        return null;
    }

    @Override
    public JobHistoryResponseDto save(JobHistoryDto jobHistoryDto, String username) {

        Optional<Student> student = studentRepo.findByUsername(username);
        JobHistory jobHistory = modelMapper.map(jobHistoryDto, JobHistory.class);

        Student newStudent = null;
        if (student.isPresent()) {
            jobHistory.setStudent(student.get());
        }else {
            newStudent = new Student();
            newStudent.setUsername(username);
            jobHistory.setStudent(studentRepo.save(newStudent));
        }

        var tagList = jobHistoryDto.getTagIds()
                .stream()
                .map(id->tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobHistory.setTags(tagList);
        JobHistory jobHistory1 = jobHistoryRepo.save(jobHistory);
        JobHistoryResponseDto jobHistoryResponseDto = modelMapper.map(jobHistory1, JobHistoryResponseDto.class);

        return jobHistoryResponseDto;
    }

    @Override
    public List<JobHistoryResponseDto> findByUsername(String username) {
        Student student = studentRepo.findByUsername(username).get();
        return jobHistoryRepo.findByStudent(student).stream().map(jobHistory ->modelMapper.map(jobHistory, JobHistoryResponseDto.class)).collect(Collectors.toList());
    }
}
