package edu.miu.backend.controller;

import edu.miu.backend.dto.JobHistoryDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.service.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "*")
public class JobHistoryController {

    @Autowired
    private JobHistoryService jobHistoryService;

    @GetMapping("")
    public ResponseEntity<List<JobHistoryDto>> getAll(){
        return ResponseEntity.ok().body(jobHistoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobHistoryDto> getById(int id){
        return ResponseEntity.ok().body(jobHistoryService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobHistoryDto> updateJobHistory(@RequestBody JobHistoryDto jobHistoryDto, @PathVariable int id){
        return ResponseEntity.ok().body(jobHistoryService.update(jobHistoryDto, id));
    }


}
