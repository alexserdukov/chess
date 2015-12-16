package com.javamonkeys.controller.useraccessgroup;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.springframework.http.ResponseEntity;

public interface IUserAccessGroupController {
    ResponseEntity<UserAccessGroup> createUserAccessGroup(String name, Boolean isAdmin);

    ResponseEntity<UserAccessGroup> getUserAccessGroupById(Integer id);

    ResponseEntity<UserAccessGroup> getUserAccessGroupByName(String email);

    ResponseEntity updateUserAccessGroup(Integer id, UserAccessGroup group);

    ResponseEntity deleteUserAccessGroup(Integer id);
}
