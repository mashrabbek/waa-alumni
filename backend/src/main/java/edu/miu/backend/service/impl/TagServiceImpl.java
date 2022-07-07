package edu.miu.backend.service.impl;

import edu.miu.backend.dto.TagDto;
import edu.miu.backend.entity.Tag;
import edu.miu.backend.repo.TagRepo;
import edu.miu.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = (List<Tag>) tagRepo.findAll();
        var result = tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public TagDto findById(int id) {
        Tag tag = tagRepo.findById(id).orElse(null);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public TagDto save(TagDto tagDto) {
        return modelMapper.map(tagRepo.save(modelMapper.map(tagDto, Tag.class)), TagDto.class);
    }

    @Override
    public void delete(int id) {
        tagRepo.deleteById(id);
    }

    @Override
    public TagDto update(TagDto tagDto, int id) throws Exception {
        return null;
    }
}
