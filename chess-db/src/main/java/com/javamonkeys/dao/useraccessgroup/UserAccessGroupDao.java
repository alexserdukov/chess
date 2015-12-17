package com.javamonkeys.dao.useraccessgroup;

import com.javamonkeys.dao.AbstractDao;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccessGroupDao extends AbstractDao implements IUserAccessGroupDao {

    public UserAccessGroup createUserAccessGroup(UserAccessGroup group) {
        save(group);
        return group;
    }

    public UserAccessGroup getUserAccessGroupById(Integer id) {
        return (UserAccessGroup) getSession().get(UserAccessGroup.class, id);
    }

    public UserAccessGroup getUserAccessGroupByName(String name) {
        Query query = getSession().createQuery("from UserAccessGroup where name = :name");
        query.setParameter("name", name);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (UserAccessGroup)result.get(0);
        }
    }

    public boolean updateUserAccessGroup(UserAccessGroup group) {
        persist(group);
        return true;
    }

    public boolean deleteUserAccessGroup(UserAccessGroup group) {
        delete(group);
        return true;
    }
}
