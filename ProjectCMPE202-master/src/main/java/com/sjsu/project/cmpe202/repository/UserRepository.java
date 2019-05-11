package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

    User findUserById(Integer id);
}