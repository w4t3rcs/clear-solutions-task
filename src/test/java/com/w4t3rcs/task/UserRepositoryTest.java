package com.w4t3rcs.task;

import com.w4t3rcs.task.entity.User;
import com.w4t3rcs.task.exception.UserNotFoundException;
import com.w4t3rcs.task.repository.UserRepository;
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
class UserRepositoryTest {
    private static User testUser;
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeAll
    public static void init() {
        testUser = User.builder()
                .firstName("First")
                .lastName("Last")
                .email("test@email.mail")
                .birthDate(LocalDate.of(1900, 10, 10))
                .build();
    }

    @Test
    @Order(1)
    void createUser() {
        testUser = userRepository.save(testUser);
        log.debug(testUser.toString());
        Assertions.assertTrue(userRepository.existsById(testUser.getId()));
    }

    @Test
    @Order(2)
    void readUser() {
        Long id = testUser.getId();
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        log.debug(user.toString());
        Assertions.assertEquals(testUser, user);
    }

    @Test
    @Order(3)
    void updateUser() {
        testUser.setFirstName("Upd First");
        testUser = userRepository.save(testUser);
        log.debug(testUser.toString());
        Assertions.assertTrue(userRepository.existsById(testUser.getId()));
    }

    @Test
    @Order(4)
    void deleteUser() {
        Long id = testUser.getId();
        userRepository.deleteById(id);
        log.debug(id.toString());
        Assertions.assertFalse(userRepository.existsById(testUser.getId()));
    }
}
