package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    List<User> findAllUsersByParams(int limit, int offset);

    User findById(Long id);

    User findByUsername(String username);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    Long numberOfUsers();
}
