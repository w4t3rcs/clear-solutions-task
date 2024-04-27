package com.w4t3rcs.task;

import com.w4t3rcs.task.dto.UserDto;
import com.w4t3rcs.task.repository.UserRepository;
import com.w4t3rcs.task.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    private static UserDto testUser;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeAll
    public static void init() {
        testUser = UserDto.builder()
                .firstName("First")
                .lastName("Last")
                .email("test@email.mail")
                .birthDate(LocalDate.of(1900, 10, 10))
                .build();
    }

    @Test
    @Order(1)
    void createUser() {
        testUser = userService.createUser(testUser);
        log.debug(testUser.toString());
        Assertions.assertTrue(userRepository.existsById(testUser.getId()));
    }

    @Test
    @Order(2)
    void readUser() {
        Long id = testUser.getId();
        UserDto user = userService.getUser(id);
        log.debug(user.toString());
        Assertions.assertEquals(testUser, user);
    }

    @Test
    @Order(3)
    void updateUser() {
        Long id = testUser.getId();
        testUser.setFirstName("Upd First");
        testUser = userService.updateUser(id, testUser);
        log.debug(testUser.toString());
        Assertions.assertTrue(userRepository.existsById(id));
    }

    @Test
    @Order(4)
    void deleteUser() {
        Long id = testUser.getId();
        userService.deleteUser(id);
        log.debug(id.toString());
        Assertions.assertFalse(userRepository.existsById(testUser.getId()));
    }
}
