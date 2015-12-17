package com.javamonkeys.service.user;

import com.javamonkeys.entity.user.User;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;

import java.util.Date;

public interface IUserService {
    User createUser(String email, String password, Date birthDate, String name, UserAccessGroup userAccessGroup);

    User getUserById(Integer id);

    User getUserByEmail(String email);

    boolean updateUser(Integer id, User user);

    boolean deleteUser(Integer id);
}
