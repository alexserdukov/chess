package com.javamonkeys.service.useraccessgroup;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;

public interface IUserAccessGroupService {
    UserAccessGroup createUserAccessGroup(String name, Boolean isAdmin);

    UserAccessGroup getUserAccessGroupById(Integer id);

    UserAccessGroup getUserAccessGroupByName(String name);

    boolean updateUserAccessGroup(Integer id, UserAccessGroup group);

    boolean deleteUserAccessGroup(Integer id);
}
