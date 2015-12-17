package com.javamonkeys.controller.user;

import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Base64;

@Controller
@RequestMapping("/api/users")
public class UserController implements IUserController {

    @Autowired
    IUserService userService;

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestHeader(value = "Authorization") String authorization) {
        String[] credentials = getCredentialsFromBase64String(authorization);
        if (credentials != null) {
            User user = userService.createUser(credentials[0], credentials[1], null, null, null);
            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return (user == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        return  (user == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        return (userService.updateUser(id, user))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        return (userService.deleteUser(id))
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // Decode string credentials from base64
    // return array of 2 elements (credentials) if decoding was successful, or null - otherwise
    private String[] getCredentialsFromBase64String(String authorization) {
        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] decodedData = Base64.getDecoder().decode(base64Credentials);
            if (decodedData != null) {
                String decodedCredentials = new String(decodedData, Charset.forName("UTF-8"));
                String[] credentials = decodedCredentials.split(":", 2);
                return (credentials.length == 2) ? credentials : null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
