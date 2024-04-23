package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.dto.request.CreateDirectoryRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.repository.DirectoryQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DirectoryService {
    private final DirectoryRepository directoryRepository;
    private final DirectoryQueryDslRepository directoryQueryDslRepository;

    public List<SelectDirectoryResponseDto> selectByUserId(Long userId) {
        List<Directory> directories = directoryQueryDslRepository.findByUserId(userId);
        return directories.stream().map(SelectDirectoryResponseDto::fromEntity).collect(Collectors.toList());
    }

    public List<SelectDirectoryResponseDto> selectById(Long id) {
        return directoryRepository.findById(id).stream().map(SelectDirectoryResponseDto::fromEntity).collect(Collectors.toList());

    }

    public CreateDirectoryResponseDto create(CreateDirectoryRequestDto dto) {
        return CreateDirectoryResponseDto.fromEntity(directoryRepository.save(Directory.builder()
                .name(dto.getName())
                .build()));
    }
}
