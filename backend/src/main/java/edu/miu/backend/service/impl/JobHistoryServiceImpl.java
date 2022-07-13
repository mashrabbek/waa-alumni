package edu.miu.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.Tag;
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
    private final ObjectMapper objectMapper;
    private final TagRepo tagRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<JobHistoryResponseDto> findAll() {
        List<JobHistory> histories =  (List<JobHistory>) jobHistoryRepo.findAll();
        List<JobHistoryResponseDto> collect = histories
                .stream()
                .map(jobHistory -> {
                    JobHistoryResponseDto jobHistoryResponseDto = modelMapper.map(jobHistory, JobHistoryResponseDto.class);
                        jobHistoryResponseDto.setTags(jobHistory.getTags().stream().map(tag -> {
                            try {
                                return objectMapper.writeValueAsString(tag.getName());
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList()));
                    return jobHistoryResponseDto;
                })
                .collect(Collectors.toList());
        return collect;
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
        Student student = studentRepo.findByUsername(username).orElse(null);
        return jobHistoryRepo.findByStudent(student).stream().map(jobHistory ->modelMapper.map(jobHistory, JobHistoryResponseDto.class)).collect(Collectors.toList());
    }
}
