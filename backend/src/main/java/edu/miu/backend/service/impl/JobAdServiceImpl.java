package edu.miu.backend.service.impl;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.Tag;
import edu.miu.backend.repo.JobAdRepo;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepo jobAdRepo;

    private final TagRepo tagRepo;


    @Override
    public List<JobAd> findAll() {
        return (List<JobAd>) jobAdRepo.findAll();
    }

    @Override
    public JobAd findById(int id) {
        return jobAdRepo.findById(id).get();
    }

    @Override
    public JobAd save(JobAd jobAd) {
        return jobAdRepo.save(jobAd);
    }

    @Override
    public void delete(int id) {
        jobAdRepo.deleteById(id);
    }

    @Override
    public JobAdDto update(JobAdDto jobAdDto, int id) throws Exception {
        JobAd jobAd = jobAdRepo.findById(id).orElseGet(null);
        if(jobAd == null){
            throw new Exception("JobAd not found");
        }

        List<Tag> tags = new ArrayList<>();
        for(Integer k: jobAdDto.getTagIds()){
            tags.add(tagRepo.findById(k).get());
        }
        jobAd.setBenefits(jobAdDto.getBenefits());
        jobAd.setDescription(jobAdDto.getDescription());
//        jobAd.setFiles(jobAdDto.get());
        jobAd.setTags(tags);

        return jobAdDto;
    }


}
