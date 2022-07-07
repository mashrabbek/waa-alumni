package edu.miu.backend.service.impl;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.Tag;
import edu.miu.backend.entity.User;
import edu.miu.backend.repo.JobAdRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepo jobAdRepo;
    private final ModelMapper modelMapper;
    private final StudentRepo studentRepo;
    private final TagRepo tagRepo;


    @Override
    public List<JobAd> findAll() {
        return (List<JobAd>) jobAdRepo.findAll();
    }

    @Override
    public JobAdDto findById(int id) {
        return modelMapper.map(jobAdRepo.findById(id).get(), JobAdDto.class);
    }

    @Override
    public JobAdDto save(JobAdDto jobAdDto) {
        JobAd jobAd = modelMapper.map(jobAdDto, JobAd.class);
        User student = studentRepo.findById(jobAdDto.getCreatorId()).get();

        var tagList = jobAdDto.getTagIds()
                .stream()
                .map(id->tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobAd.setTags(tagList);
        jobAd.setCreator(student);
        return modelMapper.map(jobAdRepo.save(jobAd), JobAdDto.class);
    }

    @Override
    public void delete(int id) {
        jobAdRepo.deleteById(id);
    }

    @Override
    public JobAdDto update(JobAdDto jobAdDto, int id) throws Exception {
        JobAd jobAd = jobAdRepo.findById(id)
                .orElseThrow(() -> new Exception("JobAd not found"));

        var tagList = jobAdDto.getTagIds()
                .stream()
                .map(tagId->tagRepo.findById(tagId).get())
                .collect(Collectors.toList());

        jobAd.setBenefits(jobAdDto.getBenefits());
        jobAd.setDescription(jobAdDto.getDescription());
//        jobAd.setFiles(jobAdDto.get());
        jobAd.setTags(tagList);

        return jobAdDto;
    }


}
