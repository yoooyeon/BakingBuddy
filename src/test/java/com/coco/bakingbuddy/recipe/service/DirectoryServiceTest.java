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
import com.coco.bakingbuddy.user.service.RoleType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.coco.bakingbuddy.global.error.ErrorCode.DUPLICATE_DIRECTORY;
import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;
import static com.coco.bakingbuddy.user.service.RoleType.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class DirectoryServiceTest {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DirectoryQueryDslRepository directoryQueryDslRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(User.builder()
                .username("TestUser")
                .password(passwordEncoder.encode("TestUser!"))
                .nickname("TestUser")
                .role(ROLE_USER)
                .uuid(UUID.randomUUID())
                .build());
    }

    @AfterEach
    void tearDown() {
        directoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createDirectorySuccessfully() {
        // given
        CreateDirectoryRequestDto requestDto = new CreateDirectoryRequestDto();
        requestDto.setName("New Directory");

        // when
        CreateDirectoryResponseDto responseDto = directoryService.create(testUser.getId(), requestDto);

        // then
        Directory savedDirectory = directoryRepository.findById(responseDto.getId()).orElseThrow();
        assertThat(savedDirectory.getName()).isEqualTo("New Directory");
        assertThat(savedDirectory.getUser()).isEqualTo(testUser);
    }

    @Test
    void createDirectoryThrowsDuplicateException() {
        // given
        Directory existingDirectory = directoryRepository
                .save(Directory.builder()
                        .name("Directory1")
                        .user(testUser)
                        .build());
        CreateDirectoryRequestDto requestDto = new CreateDirectoryRequestDto();
        requestDto.setName("Directory1");

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> directoryService.create(testUser.getId(), requestDto));
        assertThat(exception.getCode()).isEqualTo(DUPLICATE_DIRECTORY);
    }

    @Test
    void createDirectoryThrowsUserNotFoundException() {
        // given
        CreateDirectoryRequestDto requestDto = new CreateDirectoryRequestDto();
        requestDto.setName("New Directory");

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> directoryService.create(-1L, requestDto));
        assertThat(exception.getCode()).isEqualTo(USER_NOT_FOUND);
    }

    @Test
    void selectDirectoriesByUserId() {
        // given
        Directory directory = directoryRepository
                .save(Directory.builder()
                        .name("Directory1")
                        .user(testUser)
                        .build());

        Directory directory2 = directoryRepository
                .save(Directory.builder()
                        .name("Directory2")
                        .user(testUser)
                        .build());

        // when
        List<SelectDirectoryResponseDto> directories = directoryService.selectByUserId(testUser.getId());

        // then
        assertThat(directories).hasSize(2);
        assertThat(directories).extracting("name").containsExactlyInAnyOrder("Directory1", "Directory2");
    }

    @Test
    void selectDirectoryById() {
        // given
        Directory directory = directoryRepository
                .save(Directory.builder()
                        .name("Directory")
                        .build());

        // when
        List<SelectDirectoryResponseDto> response = directoryService.selectById(directory.getId());

        // then
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getName()).isEqualTo("Directory");
    }
}
