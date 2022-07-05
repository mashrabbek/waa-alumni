package edu.miu.backend.controller;

import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.repo.JobHistoryRepo;
import edu.miu.backend.service.JobHistoryService;
import edu.miu.backend.service.impl.JobHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class JobHistoryController {

    @Autowired
    private JobHistoryService jobHistoryService;

    @GetMapping("")
    public ResponseEntity<List<JobHistory>> getAll(){
        return ResponseEntity.ok().body(jobHistoryService.findAll());
    }

    public ResponseEntity<JobHistory> getById(int id){
        return ResponseEntity.ok().body(jobHistoryService.findById(id));
    }

}
