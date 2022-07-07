package edu.miu.backend.controller;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobAd")
public class JobAdController {
    private final JobAdService jobAdService;


    @GetMapping("")
    public ResponseEntity<List<JobAd>> getAll(){
        return ResponseEntity.ok().body(jobAdService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAdDto> getById(@PathVariable int id){
        return ResponseEntity.ok().body(jobAdService.findById(id));
    }

//    @PostMapping(path = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<JobAdDto> addJobAd(@RequestBody JobAdDto jobAdDto) throws IOException {
//        return ResponseEntity.ok().body(jobAdService.save(jobAdDto));
//    }
    @PostMapping(path = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobAdDto> addJobAd(@RequestPart("description") String description, @RequestPart("benefits") String benefits,
    @RequestPart("creatorId") Integer creatorId, @RequestPart("tagIds")  List<Integer> tagIds,
    @RequestPart("files") MultipartFile[] files) throws IOException {

        JobAdDto jobAdDto = new JobAdDto(null,description,benefits,creatorId,tagIds, files);
        return ResponseEntity.ok().body(jobAdService.save(jobAdDto));
    }



    @PutMapping("/{id}")
    public ResponseEntity<JobAdDto> updateJobAd(@RequestBody JobAdDto jobAdDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(jobAdService.update(jobAdDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteJobAd(@PathVariable int id){
        jobAdService.delete(id);
    }

}
