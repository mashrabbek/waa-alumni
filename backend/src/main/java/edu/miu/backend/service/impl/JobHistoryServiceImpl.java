package edu.miu.backend.service.impl;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.repo.JobHistoryRepo;
import edu.miu.backend.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepo jobHistoryRepo;
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
    public JobHistoryDto update(JobHistoryDto jobHistoryDto, int id) {
        JobHistory jobHistory = jobHistoryRepo.findById(id).get();
        JobHistory jobHistoryUpdate = modelMapper.map(jobHistoryDto, JobHistory.class);
        jobHistory.setCompanyName(jobHistoryUpdate.getCompanyName());
        jobHistory.setReasonToLeave(jobHistoryUpdate.getReasonToLeave());
        jobHistory.setStartDate(jobHistoryUpdate.getStartDate());
//        Todo:
//        jobHistory.setTags();
        ;
        return modelMapper.map(jobHistoryRepo.save(jobHistory), JobHistoryDto.class);
    }

    @Override
    public void deleteByID(int id) {
        jobHistoryRepo.deleteById(id);
    }
}
