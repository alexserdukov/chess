package com.javamonkeys.dao.user;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractDao implements IUserDao {

    @Override
    public User createUser(User user) {
        save(user);
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return (User) getSession().get(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        Query query = getSession().createQuery("from User where LOWER(email) = :email");
        query.setParameter("email", email.toLowerCase());

        List result = query.list();
        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    @Override
    public boolean updateUser(User user) {
        persist(user);
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        delete(user);
        return true;
    }
}
