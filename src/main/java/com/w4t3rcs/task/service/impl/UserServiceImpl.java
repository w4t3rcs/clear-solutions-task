package com.w4t3rcs.task.service.impl;

import com.w4t3rcs.task.dto.UserDto;
import com.w4t3rcs.task.entity.User;
import com.w4t3rcs.task.exception.FromDateIsAfterToDateException;
import com.w4t3rcs.task.exception.UserNotFoundException;
import com.w4t3rcs.task.repository.UserRepository;
import com.w4t3rcs.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Caching(cacheable = @Cacheable(value = "UserService::getUser", key = "#user.id"))
    public UserDto createUser(UserDto user) {
        return UserDto.fromUser(userRepository.save(user.toUser()));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public List<UserDto> getAllUsersBetweenDates(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) throw new FromDateIsAfterToDateException();
        return userRepository.findAllByBirthDateBetweenFromAndTo(from, to)
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    @Cacheable(value = "UserService::getUser", key = "#id")
    public UserDto getUser(Long id) {
        return UserDto.fromUser(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut(value = "UserService::getUser", key = "#id"))
    public UserDto updateUser(Long id, UserDto user) {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getFirstName())
                .ifPresent(userFromDb::setFirstName);
        Optional.ofNullable(user.getLastName())
                .ifPresent(userFromDb::setLastName);
        Optional.ofNullable(user.getEmail())
                .ifPresent(userFromDb::setEmail);
        Optional.ofNullable(user.getBirthDate())
                .ifPresent(userFromDb::setBirthDate);
        Optional.ofNullable(user.getAddress())
                .ifPresent(userFromDb::setAddress);
        Optional.ofNullable(user.getPhoneNumber())
                .ifPresent(userFromDb::setPhoneNumber);

        return UserDto.fromUser(userRepository.save(userFromDb));
    }

    @Override
    @Caching(evict = @CacheEvict(value = "UserService::getUser", key = "#id"))
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
