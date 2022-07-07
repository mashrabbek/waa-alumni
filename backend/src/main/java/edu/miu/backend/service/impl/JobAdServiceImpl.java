package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import edu.miu.backend.Config;
import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.dto.JobAdResponseDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.User;
import edu.miu.backend.repo.JobAdRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepo jobAdRepo;
    private final ModelMapper modelMapper;
    private final StudentRepo studentRepo;
    private final TagRepo tagRepo;
    private final AmazonS3 amazonS3Client;

    @Override
    public List<JobAd> findAll() {
        return (List<JobAd>) jobAdRepo.findAll();
    }

    @Override
    public JobAdDto findById(int id) {
        return modelMapper.map(jobAdRepo.findById(id).get(), JobAdDto.class);
    }

    @Override
    public JobAdResponseDto save(JobAdDto jobAdDto) throws IOException {

        JobAd jobAd = modelMapper.map(jobAdDto, JobAd.class);
        User student = studentRepo.findById(jobAdDto.getCreatorId()).get();
        jobAd.setCreator(student);

        var tagList = jobAdDto.getTagIds()
                .stream()
                .map(id->tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobAd.setTags(tagList);

        List<String> fileUrlList = Arrays
                .stream(jobAdDto.getFiles()).map((multipartFile) -> {

                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(multipartFile.getSize());
                    String keyName = generateFileName(multipartFile);
//        try {
//            var result = amazonS3Client
//                    .putObject(BucketName.ALUMNI.getBucketName(), keyName, multipartFile.getInputStream(), metadata);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
                    return Config.PUBLIC_AWS_URL + keyName;
                })
                .collect(Collectors.toList());

        jobAd.setFiles(fileUrlList);

        JobAdResponseDto responseDto = modelMapper.map(jobAdRepo.save(jobAd), JobAdResponseDto.class);
        responseDto.setCreatorId(jobAd.getCreator().getId());
        return responseDto;
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
//    private Function<MultipartFile, String> uploadFilesToAWSAndGetUrls = (multipartFile) -> {
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(multipartFile.getSize());
//        String keyName = generateFileName(multipartFile);
////        try {
////            var result = amazonS3Client
////                    .putObject(BucketName.ALUMNI.getBucketName(), keyName, multipartFile.getInputStream(), metadata);
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        return Config.PUBLIC_AWS_URL + keyName;
//    };

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
