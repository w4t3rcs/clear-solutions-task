package com.w4t3rcs.task.repository;

import com.w4t3rcs.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE users.birth_date >= :from AND users.birth_date <= :to", nativeQuery = true)
    List<User> findAllByBirthDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
