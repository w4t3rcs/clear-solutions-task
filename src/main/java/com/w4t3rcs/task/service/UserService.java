package com.w4t3rcs.task.service;

import com.w4t3rcs.task.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    List<UserDto> getAllUsers();

    List<UserDto> getAllUsersBetweenDates(LocalDate from, LocalDate to);

    UserDto getUser(Long id);

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);
}
