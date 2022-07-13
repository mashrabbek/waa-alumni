package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.dto.responseDto.JobAdResponseAppliedDto;
import edu.miu.backend.dto.responseDto.JobAdResponseDto;
import edu.miu.backend.entity.*;
import edu.miu.backend.enums.BucketName;
import edu.miu.backend.repo.*;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobAdServiceImpl implements JobAdService {

    private JobAdRepo jobAdRepo;
    private ModelMapper modelMapper;
    private StudentRepo studentRepo;
    private TagRepo tagRepo;
    private FacultyRepo facultyRepo;
    private ObjectMapper objectMapper;
    private JobApplicationRepo jobApplicationRepo;
    private AmazonS3 amazonS3Client;
    @Autowired
    public JobAdServiceImpl(JobAdRepo jobAdRepo, ModelMapper modelMapper, StudentRepo studentRepo, TagRepo tagRepo, AmazonS3 amazonS3Client,ObjectMapper objectMapper,FacultyRepo facultyRepo,JobApplicationRepo jobApplicationRepo) {
        this.jobAdRepo = jobAdRepo;
        this.modelMapper = modelMapper;
        this.studentRepo = studentRepo;
        this.tagRepo = tagRepo;
        this.objectMapper = objectMapper;
        this.amazonS3Client = amazonS3Client;
        this.facultyRepo = facultyRepo;
        this.jobApplicationRepo = jobApplicationRepo;
    }

    private String AWS_URL = String.format("https://%s.s3.amazonaws.com/", BucketName.ALUMNI.getBucketName());

    @Override
    public List<JobAdResponseDto> findAll() {
        List<JobAd> jobAds = (List<JobAd>) jobAdRepo.findAll();
        return jobAds
                .stream()
                .map(jobAd -> {
                    JobAdResponseDto jobAdResponseDto = modelMapper.map(jobAd, JobAdResponseDto.class);
                    if (jobAd.getTags() != null) {
                        jobAdResponseDto.setTags(jobAd.getTags().
                                stream().map(tag -> {
                                    try {
                                        return objectMapper.writeValueAsString(tag.getName());
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).collect(Collectors.toList()));
                    }
                    jobAdResponseDto.setCreatorUsername(jobAd.getCreator().getUsername());
                    return jobAdResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public JobAdDto findById(int id) {
        return modelMapper.map(jobAdRepo.findById(id).get(), JobAdDto.class);
    }

    @Override
    public JobAdResponseDto save(JobAdDto jobAdDto) throws IOException {

        JobAd jobAd = modelMapper.map(jobAdDto, JobAd.class);
        User student = studentRepo.findByUsername(jobAdDto.getOwnerUsername()).get();
        jobAd.setCreator(student);

        var tagList = jobAdDto.getTagIds()
                .stream()
                .map(id->tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobAd.setTags(tagList);

        if (jobAdDto.getFiles() != null) {
            List<String> fileUrlList = Arrays
                    .stream(jobAdDto.getFiles()).map((multipartFile) -> {

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(multipartFile.getSize());
                        String keyName = generateFileName(multipartFile);
                        try {
                            var result = amazonS3Client
                                    .putObject(BucketName.ALUMNI.getBucketName(), keyName, multipartFile.getInputStream(), metadata);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return AWS_URL.concat(keyName);
                    })
                    .collect(Collectors.toList());

            jobAd.setFiles(fileUrlList);
        }

        JobAdResponseDto responseDto = modelMapper.map(jobAdRepo.save(jobAd), JobAdResponseDto.class);
        responseDto.setCreatorUsername(jobAd.getCreator().getUsername());
        return responseDto;
    }


    @Override
    public void delete(int id) {
        jobAdRepo.deleteById(id);
    }

    //TODO file change handle
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

    @Override
    public List<JobAdResponseDto> findByUsername(String username) {
        Optional<Student> student = studentRepo.findByUsername(username);
        Optional<Faculty> faculty = facultyRepo.findByUsername(username);
        List<JobAd> jobAds = null;
        if (student.isPresent()){
            jobAds = jobAdRepo.findByCreator(student.get());
        }else if (faculty.isPresent()){
            jobAds = jobAdRepo.findByCreator(faculty.get());
        }else {return null;}

        return jobAds.stream()
                .map(jobAd -> {
                    JobAdResponseDto jobAdResponseDto = modelMapper.map(jobAd, JobAdResponseDto.class);
                    jobAdResponseDto.setTags(jobAd.getTags()
                            .stream()
                            .map(tag -> {
                                try {
                                    return objectMapper.writeValueAsString(tag.getName());
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }).collect(Collectors.toList()));
                    jobAdResponseDto.setCreatorUsername(jobAd.getCreator().getUsername());
                    return jobAdResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<JobAdResponseDto> findAllByUsernameExcluded(String username) {

        Optional<Student> student = studentRepo.findByUsername(username);
        Optional<Faculty> faculty = facultyRepo.findByUsername(username);
        List<JobAd> jobAds = null;
        if (student.isPresent()){
            jobAds = jobAdRepo.findByCreatorExcluded(student.get());
        }else if (faculty.isPresent()){
            jobAds = jobAdRepo.findByCreatorExcluded(faculty.get());
        }else {return null;}

        return jobAds.stream()
                .map(jobAd -> {
                    JobAdResponseDto jobAdResponseDto = modelMapper.map(jobAd, JobAdResponseDto.class);
                    jobAdResponseDto.setTags(jobAd.getTags()
                            .stream()
                            .map(tag -> {
                                try {
                                    return objectMapper.writeValueAsString(tag.getName());
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }).collect(Collectors.toList()));
                    jobAdResponseDto.setCreatorUsername(jobAd.getCreator().getUsername());
                    return jobAdResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<JobAdResponseAppliedDto> findByIdApplied(int id) {
        Optional<JobAd> jobAd = jobAdRepo.findById(id);
        if (!jobAd.isPresent()){
            return null;
        }
        Optional<List<JobApplication>> jobApplication = jobApplicationRepo.findByJobAds(jobAd.get());
        if (!jobApplication.isPresent()){
            return null;
        }

        List<JobAdResponseAppliedDto> collect = jobApplication.get().stream().map(jobApplications -> {
            JobAdResponseAppliedDto jobAdResponseAppliedDto = new JobAdResponseAppliedDto();
            jobAdResponseAppliedDto.setUsername(jobApplications.getStudent().getUsername());
            jobAdResponseAppliedDto.setCv(jobApplications.getStudent().getCv());
            return jobAdResponseAppliedDto;
        }).collect(Collectors.toList());


        return collect;
    }

    @Override
    public List<JobAdResponseDto> findTopAll() {
        List<JobAd> jobAds = (List<JobAd>) jobAdRepo.findAll();
        return jobAds
                .stream()
                .map(jobAd -> {
                    JobAdResponseDto jobAdResponseDto = modelMapper.map(jobAd, JobAdResponseDto.class);
                    if (jobAd.getTags() != null) {
                        jobAdResponseDto.setTags(jobAd.getTags().
                                stream().map(tag -> {
                                    try {
                                        return objectMapper.writeValueAsString(tag.getName());
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).collect(Collectors.toList()));
                    }
                    jobAdResponseDto.setCreatorUsername(jobAd.getCreator().getUsername());
                    return jobAdResponseDto;
                }).limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobAdResponseDto> findRecentAll() {
        List<JobAd> jobAds = jobApplicationRepo.findAllJobAds();
        return jobAds
                .stream()
                .map(jobAd -> {
                    JobAdResponseDto jobAdResponseDto = modelMapper.map(jobAd, JobAdResponseDto.class);
                    if (jobAd.getTags() != null) {
                        jobAdResponseDto.setTags(jobAd.getTags().
                                stream().map(tag -> {
                                    try {
                                        return objectMapper.writeValueAsString(tag.getName());
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).collect(Collectors.toList()));
                    }
                    jobAdResponseDto.setCreatorUsername(jobAd.getCreator().getUsername());
                    return jobAdResponseDto;
                }).limit(10)
                .collect(Collectors.toList());
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
