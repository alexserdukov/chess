package com.javamonkeys.controller.user;

import com.javamonkeys.entity.user.User;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<User> createUser(String authorization);

    ResponseEntity<User> getUserById(Integer id);

    ResponseEntity<User> getUserByEmail(String email);

    ResponseEntity updateUser(Integer id, User user);

    ResponseEntity deleteUser(Integer id);
}
