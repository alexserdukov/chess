package com.javamonkeys.dao.user;

import com.javamonkeys.entity.user.User;

public interface IUserDao {
    User createUser(User user);

    User getUserById(Integer id);

    User getUserByEmail(String email);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
