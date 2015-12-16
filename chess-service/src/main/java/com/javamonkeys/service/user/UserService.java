package com.javamonkeys.service.user;

import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    @Transactional
    public User createUser(String email, String password, Date birthDate, UserAccessGroup userAccessGroup) {
        if (email == null
                || password == null
                || email.isEmpty()
                || password.isEmpty())
            return null;

        email = email.trim();
        password = password.trim();
        if (userDao.getUserByEmail(email) != null)
            return null;

        User user = new User(email, password, birthDate, userAccessGroup);
        return userDao.createUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        if (id == null)
            return null;

        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        if (email == null || email.isEmpty())
            return null;

        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public boolean updateUser(Integer id, User user) {
        if (id == null || user == null)
            return false;

        User existUser = userDao.getUserById(id);
        if (existUser == null)
            return false;

        existUser.setEmail(user.getEmail());
        existUser.setPassword(user.getPassword());
        existUser.setName(user.getName());
        existUser.setBirthDate(user.getBirthDate());
        existUser.setUserAccessGroup(user.getUserAccessGroup());

        return userDao.updateUser(existUser);
    }

    @Override
    @Transactional
    public boolean deleteUser(Integer id) {
        if (id == null)
            return false;

        User user = userDao.getUserById(id);
        if (user == null)
            return false;

        return userDao.deleteUser(user);
    }
}
