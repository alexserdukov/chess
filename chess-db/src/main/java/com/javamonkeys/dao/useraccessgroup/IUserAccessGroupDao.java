package com.javamonkeys.dao.useraccessgroup;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;

public interface IUserAccessGroupDao {
    UserAccessGroup createUserAccessGroup(UserAccessGroup group);

    UserAccessGroup getUserAccessGroupById(Integer id);

    UserAccessGroup getUserAccessGroupByName(String name);

    boolean updateUserAccessGroup(UserAccessGroup group);

    boolean deleteUserAccessGroup(UserAccessGroup group);
}
