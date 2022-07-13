package edu.miu.backend.service.impl;

import edu.miu.backend.config.MQConfig;
import edu.miu.backend.dto.JobApplicationDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.Student;
import edu.miu.backend.notification.CustomMessage;
import edu.miu.backend.repo.*;
import edu.miu.backend.repo.JobApplicationRepo;
import edu.miu.backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationImpl implements JobApplicationService {

    private final JobApplicationRepo jobApplicationRepo;
    private final JobAdRepo jobAdRepo;
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;
    private final RabbitTemplate template;
    private final FacultyRepo facultyRepo;

    @Override
    public List<JobApplicationDto> findAll() {

        List<JobApplication> tags = (List<JobApplication>) jobApplicationRepo.findAll();
        var result = tags
                .stream()
                .map(tag -> modelMapper.map(tag, JobApplicationDto.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public JobApplicationDto findById(int id) {
        JobApplication tag = jobApplicationRepo.findById(id).orElse(null);
        return modelMapper.map(tag, JobApplicationDto.class);
    }

    @Override
    public JobApplicationDto save(JobApplicationDto jobApplicationDto) {

        Optional<JobAd> jobAd = jobAdRepo.findById(jobApplicationDto.getJobAdId());
        if (!jobAd.isPresent()){
            return null;
        }
        Student student = studentRepo.findByUsername(jobApplicationDto.getStudentUsername()).get();
//        jobAdRepo.findByCreator()

        JobApplication jobApplication = modelMapper.map(jobApplicationDto, JobApplication.class);
        jobApplication.setJobAds(jobAd.get()); jobApplication.setStudent(student);
        Optional<JobApplication> jobAdsAndStudent = jobApplicationRepo.findByJobAdsAndStudent(jobAd.get(), student);
        if (jobAdsAndStudent.isPresent()){
            return null;
        }

        JobApplicationDto savedJobApplicationDto = modelMapper.map(jobApplicationRepo.save(jobApplication), JobApplicationDto.class);
        savedJobApplicationDto.setJobAdId(jobAd.get().getId()); savedJobApplicationDto.setStudentUsername(student.getUsername());

        String message = "Notification will be sent to " + jobAd.get().getCreator().getUsername() + " applied student: " + student.getUsername() + " position " + jobAd.get().getDescription();

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessageId(UUID.randomUUID().toString());
        customMessage.setMessage(message);
        customMessage.setMessageDate(new Date());

        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, customMessage);

        JobApplicationDto applicationDto = modelMapper.map(jobApplicationRepo.save(jobApplication), JobApplicationDto.class);
        applicationDto.setJobAdId(jobAd.get().getId());
        applicationDto.setStudentUsername(student.getUsername());
        return applicationDto;
    }

    @Override
    public void delete(int id) {
        jobApplicationRepo.deleteById(id);
    }

    @Override
    public JobApplicationDto update(JobApplicationDto jobApplicationDto, int id) throws Exception {
        return null;
    }

    @Override
    public List<JobApplicationDto> findByUsername(String username) {
        return null;
    }

}
