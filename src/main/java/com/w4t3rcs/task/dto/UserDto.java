package com.w4t3rcs.task.dto;

import com.w4t3rcs.task.entity.User;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    public static UserDto fromUser(@Valid User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Valid
    public User toUser() {
        return User.builder()
                .id(this.getId())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .birthDate(this.getBirthDate())
                .address(this.getAddress())
                .phoneNumber(this.getPhoneNumber())
                .build();
    }
}
