package edu.miu.backend.service;

import edu.miu.backend.dto.StudentDto;
import edu.miu.backend.dto.TagDto;
import edu.miu.backend.entity.Student;

import java.util.List;

public interface TagService {
    List<TagDto> findAll();

    TagDto findById(int id);

    TagDto save(TagDto tagDto);

    void delete(int id);

    TagDto update(TagDto tagDto, int id) throws Exception;
}
