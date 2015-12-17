package com.javamonkeys.controller.useraccessgroup;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import com.javamonkeys.service.useraccessgroup.IUserAccessGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/useraccessgroups")
public class UserAccessGroupController implements IUserAccessGroupController {

    @Autowired
    IUserAccessGroupService userAccessGroupService;

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<UserAccessGroup> createUserAccessGroup(@RequestHeader(value = "name") String name,
                                                                 @RequestHeader(value = "isAdmin") Boolean isAdmin) {
        UserAccessGroup group = userAccessGroupService.createUserAccessGroup(name, isAdmin);
        return (group == null)
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<UserAccessGroup> getUserAccessGroupById(@PathVariable("id") Integer id) {
        UserAccessGroup group = userAccessGroupService.getUserAccessGroupById(id);
        return (group == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(group, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<UserAccessGroup> getUserAccessGroupByName(@RequestParam("name") String name) {
        UserAccessGroup group = userAccessGroupService.getUserAccessGroupByName(name);
        return (group == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(group, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUserAccessGroup(@PathVariable("id") Integer id, @RequestBody UserAccessGroup group) {
        return (userAccessGroupService.updateUserAccessGroup(id, group))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserAccessGroup(@PathVariable("id") Integer id) {
        return (userAccessGroupService.deleteUserAccessGroup(id))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
