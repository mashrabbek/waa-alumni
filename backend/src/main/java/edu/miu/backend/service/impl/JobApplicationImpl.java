package edu.miu.backend.service.impl;

import edu.miu.backend.dto.JobApplicationDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repo.JobAdRepo;
import edu.miu.backend.repo.JobApplicationRepo;
import edu.miu.backend.repo.JobApplicationRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationImpl implements JobApplicationService {

    private final JobApplicationRepo jobApplicationRepo;
    private final JobAdRepo jobAdRepo;
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<JobApplicationDto> findAll() {
        List<JobApplication> tags = (List<JobApplication>) jobApplicationRepo.findAll();
        var result = tags.stream().map(tag -> modelMapper.map(tag, JobApplicationDto.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public JobApplicationDto findById(int id) {
        JobApplication tag = jobApplicationRepo.findById(id).orElse(null);
        return modelMapper.map(tag, JobApplicationDto.class);
    }

    @Override
    public JobApplicationDto save(JobApplicationDto jobApplicationDto) {
        JobAd jobAds = jobAdRepo.findById(jobApplicationDto.getJobAdId()).get();
        Student student = studentRepo.findById(jobApplicationDto.getStudentId()).get();

        JobApplication jobApplication = modelMapper.map(jobApplicationDto, JobApplication.class);
        jobApplication.setJobAds(jobAds); jobApplication.setStudent(student);

        JobApplicationDto savedJobApplicationDto = modelMapper.map(jobApplicationRepo.save(jobApplication), JobApplicationDto.class);
        savedJobApplicationDto.setJobAdId(jobAds.getId()); savedJobApplicationDto.setStudentId(student.getId());

        return modelMapper.map(jobApplicationRepo.save(jobApplication), JobApplicationDto.class);
    }

    @Override
    public void delete(int id) {
        jobApplicationRepo.deleteById(id);
    }

    @Override
    public JobApplicationDto update(JobApplicationDto jobApplicationDto, int id) throws Exception {
        return null;
    }

}
