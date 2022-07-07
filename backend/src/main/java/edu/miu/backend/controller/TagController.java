package edu.miu.backend.controller;
import edu.miu.backend.dto.TagDto;
import edu.miu.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<List<TagDto>> getAll(){
        return ResponseEntity.ok().body(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById(@PathVariable int id){
        return ResponseEntity.ok().body(tagService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<TagDto> addTag(@RequestBody TagDto tagDto){
        return ResponseEntity.ok().body(tagService.save(tagDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@RequestBody TagDto tagDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok().body(tagService.update(tagDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id){
        tagService.delete(id);
    }

}
