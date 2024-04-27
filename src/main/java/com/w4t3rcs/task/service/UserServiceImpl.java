package com.w4t3rcs.task.service;

import com.w4t3rcs.task.dto.UserDto;
import com.w4t3rcs.task.entity.User;
import com.w4t3rcs.task.exception.FromDateIsAfterToDateException;
import com.w4t3rcs.task.exception.InvalidAgeException;
import com.w4t3rcs.task.exception.UserNotFoundException;
import com.w4t3rcs.task.properties.UserProperties;
import com.w4t3rcs.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProperties userProperties;

    @Override
    @Caching(cacheable = @Cacheable(value = "UserService::getUser", key = "#user.id"))
    public UserDto createUser(UserDto user) {
        Period between = Period.between(user.getBirthDate(), LocalDate.now());
        if (between.getYears() < userProperties.getAllowedAge()) throw new InvalidAgeException();
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
        if (user.getFirstName() != null) {
            userFromDb.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userFromDb.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            userFromDb.setEmail(user.getEmail());
        }
        if (user.getBirthDate() != null) {
            Period between = Period.between(user.getBirthDate(), LocalDate.now());
            if (between.getYears() < userProperties.getAllowedAge()) throw new InvalidAgeException();
            userFromDb.setBirthDate(user.getBirthDate());
        }
        if (user.getAddress() != null) {
            userFromDb.setAddress(user.getAddress());
        }
        if (user.getPhoneNumber() != null) {
            userFromDb.setPhoneNumber(user.getPhoneNumber());
        }

        System.out.println(userFromDb);
        return UserDto.fromUser(userRepository.save(userFromDb));
    }

    @Override
    @Caching(evict = @CacheEvict(value = "UserService::getUser", key = "#id"))
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
