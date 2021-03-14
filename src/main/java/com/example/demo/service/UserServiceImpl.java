package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // List all
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // List by params
    @Override
    public List<User> findAllUsersByParams(int limit, int offset) {
        Pageable pageable = new OffsetBasedPageRequest(limit, offset);
        return userRepository.findAll(pageable).getContent();
    }

    // find by id
    @Override
    public User findById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // find by username
    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // create
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // save = create or update
    @Override
    public User updateUser(final User user) {
        return userRepository.save(user);
    }

    // delete
    @Override
    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    // total number
    @Override
    public Long numberOfUsers() {
        return userRepository.count();
    }
}
