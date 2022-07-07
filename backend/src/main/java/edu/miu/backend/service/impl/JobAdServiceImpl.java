package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.Tag;
import edu.miu.backend.entity.User;
import edu.miu.backend.enums.BucketName;
import edu.miu.backend.repo.JobAdRepo;
import edu.miu.backend.repo.StudentRepo;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public JobAdDto save(JobAdDto jobAdDto) throws IOException {

        JobAd jobAd = modelMapper.map(jobAdDto, JobAd.class);
        User student = studentRepo.findById(jobAd.getId()).get();
//
//        String tagIdList = request.getParameter("tagIds").trim();

//        List<Integer> tagIds = new ArrayList<>();
//        for(String tagid: tagIdList){
//            tagIds.add(Integer.valueOf(tagid));
//        }

        var tagList = jobAdDto.getTagIds()
                .stream()
                .map(id->tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobAd.setTags(tagList);
        jobAd.setCreator(student);

        List<String> fileUrlList = new ArrayList<>();
        String url = "https://waa-alumni.s3.amazonaws.com/";
        for(MultipartFile file: jobAdDto.getFiles()) {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            String keyName = generateFileName(file);
            try {
                var res = amazonS3Client.putObject(BucketName.ALUMNI.getBucketName(), keyName, file.getInputStream(), metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileUrlList.add(url + keyName);
            System.out.println("File uploaded: " + keyName);
        }
        jobAd.setFiles(fileUrlList);

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
//    public JobAdDto save(HttpServletRequest request, MultipartFile[] files) {
//
//       request.getParameter("description");
//       request.getParameter("benefits");
//       request.getParameter("creatorId");
//       String[] tagIds = request.getParameterValues("tagIds");


//        for(MultipartFile file: files) {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            String keyName = generateFileName(file);
//            try {
//                var res = amazonS3Client.putObject(BucketName.ALUMNI.getBucketName(), keyName, file.getInputStream(), metadata);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("File uploaded: " + keyName);
//        }
        // https://waa-alumni.s3.amazonaws.com/1657158181442-Assignment_4_-_IAM.pdf
//        return null;
//    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
