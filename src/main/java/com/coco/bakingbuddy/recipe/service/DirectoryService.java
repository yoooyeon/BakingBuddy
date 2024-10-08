package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.dto.request.CreateDirectoryRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.repository.DirectoryQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.DUPLICATE_DIRECTORY;
import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class DirectoryService {
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final DirectoryQueryDslRepository directoryQueryDslRepository;

    @Transactional(readOnly = true)
    public List<SelectDirectoryResponseDto> selectByUserId(Long userId) {
        List<Directory> directories = directoryQueryDslRepository.findByUserId(userId);
        return directories.stream()
                .map(SelectDirectoryResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SelectDirectoryResponseDto> selectById(Long id) {
        return directoryRepository.findById(id).stream()
                .map(SelectDirectoryResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public CreateDirectoryResponseDto create(Long userId, CreateDirectoryRequestDto dto) {
        // 디렉토리 이름이 중복되는지 확인
        boolean directoryExists = directoryRepository.existsByNameAndUserId(dto.getName(), userId);
        if (directoryExists) {
            throw new CustomException(DUPLICATE_DIRECTORY);
        }
        Directory directory = Directory.builder()
                .name(dto.getName())
                .build();
        Directory save = directoryRepository.save(directory);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        save.setUser(user);
        return CreateDirectoryResponseDto.fromEntity(save);
    }
}
