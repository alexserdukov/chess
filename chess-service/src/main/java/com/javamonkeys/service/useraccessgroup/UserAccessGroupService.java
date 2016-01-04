package com.javamonkeys.service.useraccessgroup;

import com.javamonkeys.dao.useraccessgroup.IUserAccessGroupDao;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccessGroupService implements IUserAccessGroupService {

    @Autowired
    IUserAccessGroupDao userAccessGroupDao;

    @Override
    @Transactional
    public UserAccessGroup createUserAccessGroup(String name, Boolean isAdmin) {
        if (name == null
                || isAdmin == null
                || name.isEmpty())
            return null;

        if (userAccessGroupDao.getUserAccessGroupByName(name) != null)
            return null;

        UserAccessGroup group = new UserAccessGroup(name, isAdmin);
        return userAccessGroupDao.createUserAccessGroup(group);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccessGroup getUserAccessGroupById(Integer id) {
        if (id == null)
            return null;

        return userAccessGroupDao.getUserAccessGroupById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccessGroup getUserAccessGroupByName(String name) {
        if (name == null)
            return null;

        return userAccessGroupDao.getUserAccessGroupByName(name);
    }

    @Override
    @Transactional
    public boolean updateUserAccessGroup(Integer id, UserAccessGroup group) {
        if (id == null || group == null)
            return false;

        UserAccessGroup existGroup = userAccessGroupDao.getUserAccessGroupById(id);
        if (existGroup == null)
            return false;

        existGroup.setName(group.getName());
        existGroup.setIsAdmin(group.getIsAdmin());

        return userAccessGroupDao.updateUserAccessGroup(existGroup);
    }

    @Override
    @Transactional
    public boolean deleteUserAccessGroup(Integer id) {
        if (id == null)
            return false;

        UserAccessGroup group = userAccessGroupDao.getUserAccessGroupById(id);
        if (group == null)
            return false;

        return userAccessGroupDao.deleteUserAccessGroup(group);
    }
}
