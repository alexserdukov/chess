package com.javamonkeys.tests;

import com.javamonkeys.entity.user.User;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;

import java.util.Date;

import static org.junit.Assert.*;

public class UserControllerTest {

    private static final String baseUrl = "http://localhost:8555";
    private static User existingUser, updateUser, deleteUser;
    private static UserAccessGroup testGroup;

    private static ResponseEntity<User> doCreateUser(CustomRestTemplate restTemplate, HttpEntity entity) {
        return restTemplate.exchange(baseUrl + "/api/users/",
                HttpMethod.POST,
                entity,
                User.class);
    }

    private static ResponseEntity<User> doGetUserById(CustomRestTemplate restTemplate, Integer userId) {
        return restTemplate.exchange(baseUrl + "/api/users/" + userId,
                HttpMethod.GET,
                new HttpEntity(restTemplate.getHttpHeaders()),
                User.class);
    }

    private static ResponseEntity<User> doGetUserByEmail(CustomRestTemplate restTemplate, String email) {
        return restTemplate.exchange(baseUrl + "/api/users?email=" + email,
                HttpMethod.GET,
                new HttpEntity(restTemplate.getHttpHeaders()),
                User.class);
    }

    private static ResponseEntity<String> doUpdateUser(CustomRestTemplate restTemplate, HttpEntity entityUser, Integer userId) {
        return restTemplate.exchange(baseUrl + "/api/users/" + userId,
                HttpMethod.PUT,
                entityUser,
                String.class);
    }

    private static ResponseEntity<String> doDeleteUser(CustomRestTemplate restTemplate, Integer userId) {
        return restTemplate.exchange(baseUrl + "/api/users/" + userId,
                HttpMethod.DELETE,
                new HttpEntity(restTemplate.getHttpHeaders()),
                String.class);
    }

    private static ResponseEntity<UserAccessGroup> doCreateUserAccessGroup(CustomRestTemplate restTemplate, HttpEntity entity) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups/",
                HttpMethod.POST,
                entity,
                UserAccessGroup.class);
    }

    private static ResponseEntity<String> doDeleteUserAccessGroup(CustomRestTemplate restTemplate, Integer groupId) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups/" + groupId,
                HttpMethod.DELETE,
                new HttpEntity(restTemplate.getHttpHeaders()),
                String.class);
    }

    @BeforeClass
    public static void init() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Delete "newUser@javamonkeys.com" if exists
        ResponseEntity<User> responseEntity = doGetUserByEmail(restTemplate, "newUser@javamonkeys.com");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            if (responseEntity.getBody() == null)
                fail("User \"newUser@javamonkeys.com\" deletion error");
            else
                doDeleteUser(restTemplate, responseEntity.getBody().getId());
        }

        // Create user "existingUser@javamonkeys.com"
        restTemplate.addBasicAuthHttpHeaders("existingUser@javamonkeys.com", "12345");
        responseEntity = doCreateUser(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User \"existingUser@javamonkeys.com\" creation error");
        } else {
            existingUser = responseEntity.getBody();
            assertNotNull(existingUser);
        }

        // Create user "deleteUser@javamonkeys.com"
        restTemplate.clearHttpHeaders();
        restTemplate.addBasicAuthHttpHeaders("deleteUser@javamonkeys.com", "12345");
        responseEntity = doCreateUser(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User \"deleteUser@javamonkeys.com\" creation error");
        } else {
            deleteUser = responseEntity.getBody();
            assertNotNull(deleteUser);
        }

        // Create user "updateUser@javamonkeys.com"
        restTemplate.clearHttpHeaders();
        restTemplate.addBasicAuthHttpHeaders("updateUser@javamonkeys.com", "12345");
        responseEntity = doCreateUser(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User \"updateUser@javamonkeys.com\" creation error");
        } else {
            updateUser = responseEntity.getBody();
            assertNotNull(updateUser);
        }

        // Create "test user group"
        restTemplate.clearHttpHeaders();
        restTemplate.addHttpHeader("name", "test user group");
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(true));
        ResponseEntity<UserAccessGroup> responseEntityGroup = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntityGroup.getStatusCode() != HttpStatus.CREATED) {
            fail("User access group \"test user group\" creation error");
        } else {
            testGroup = responseEntityGroup.getBody();
            assertNotNull(testGroup);
        }
    }

    @AfterClass
    public static void clearTestData() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        doDeleteUser(restTemplate, existingUser.getId());
        doDeleteUser(restTemplate, updateUser.getId());
        doDeleteUser(restTemplate, deleteUser.getId());
        doDeleteUserAccessGroup(restTemplate, testGroup.getId());

        User user = doGetUserByEmail(restTemplate, "newUser@mail.com").getBody();
        if (user != null)
            doDeleteUser(restTemplate, user.getId());
    }

    /* Create new user
    * User doesn't exist in DB
    * Should return HttpStatus.CREATED */
    @Test
    public void createUserTest() {
        final String email = "newUser@mail.com";
        final String password = "12345";

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders(email, password);
        ResponseEntity<User> responseEntity = doCreateUser(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        restTemplate.clearHttpHeaders();
        responseEntity = doGetUserByEmail(restTemplate, email);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User user = responseEntity.getBody();
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    /* Create new user
    * User already exist in DB
    * expected HttpStatus.BAD_REQUEST */
    @Test
    public void createUserAlreadyExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders(existingUser.getEmail(), existingUser.getPassword());
        ResponseEntity<User> responseEntity = doCreateUser(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Get user by id
    * User already exists in DB
    * Should return user */
    @Test
    public void getUserByIdTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUserById(restTemplate, existingUser.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User user = responseEntity.getBody();
        assertNotNull(user);
        assertEquals(existingUser, user);
    }

    /* Get user by id
    * Incorrect user id
    * expected HttpStatus.NOT_FOUND */
    @Test
    public void getUserIncorrectIdTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUserById(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Get user by email
     * User already exists in DB
     * Should return user */
    @Test
    public void getUserByEmailTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> response = doGetUserByEmail(restTemplate, existingUser.getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User findUser = response.getBody();
        assertNotNull(findUser);
        assertEquals(existingUser.getEmail(), findUser.getEmail());
    }

    /* Get user by email
     * Incorrect email
     * Should return null */
    @Test
    public void getUserIncorrectEmailTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUserByEmail(restTemplate, "incorrectEmail");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Update user
     * User already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void updateUserTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Update current user
        final String newEmail = "JohnDoe@mail.com";
        final String newName = "John Doe";
        final String newPassword = "54321";
        final Date newDate = new Date();
        assertNotEquals(newEmail, updateUser.getEmail());
        assertNotEquals(newName, updateUser.getName());
        assertNotEquals(newPassword, updateUser.getPassword());
        assertNotEquals(newDate, updateUser.getBirthDate());
        assertNotEquals(testGroup, updateUser.getUserAccessGroup());
        updateUser.setEmail(newEmail);
        updateUser.setName(newName);
        updateUser.setPassword(newPassword);
        updateUser.setBirthDate(newDate);
        updateUser.setUserAccessGroup(testGroup);

        HttpEntity<User> entity = new HttpEntity<>(updateUser, restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doUpdateUser(restTemplate, entity, updateUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user
        ResponseEntity<User> responseUser = doGetUserByEmail(restTemplate, newEmail);
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());
        User changedUser = responseUser.getBody();
        assertNotNull(changedUser);
        assertEquals(updateUser, changedUser);
    }

    /* Update user
     * User doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void updateUserDoesNotExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        User newUser = new User("newUser@mail.com", "123");
        HttpEntity<User> entity = new HttpEntity<>(newUser, restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doUpdateUser(restTemplate, entity, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Delete user from DB
     * User already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void deleteUserTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Check before
        ResponseEntity<User> responseUser = doGetUserByEmail(restTemplate, deleteUser.getEmail());
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());

        // Delete
        ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, deleteUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check after
        responseUser = doGetUserByEmail(restTemplate, deleteUser.getEmail());
        assertEquals(HttpStatus.NOT_FOUND, responseUser.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Delete user from DB
     * User doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void deleteUserDoesNotExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////////// COMPLEX TEST /////////////////////////////////////////

    @Test
    public void complexTest() {
        final String email = "complexTestUser@mail.com";
        final String password = "12345";

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Delete user if exist
        User user = doGetUserByEmail(restTemplate, email).getBody();
        if (user != null)
            doDeleteUser(restTemplate, user.getId());

        assertEquals(HttpStatus.NOT_FOUND, doGetUserByEmail(restTemplate, email).getStatusCode());

        // Create new user
        restTemplate.addBasicAuthHttpHeaders(email, password);
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());
        ResponseEntity<User> responseUser = doCreateUser(restTemplate, entity);
        assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());
        assertNotNull(responseUser.getBody());
        assertEquals(email, responseUser.getBody().getEmail());

        restTemplate.clearHttpHeaders();

        // Get new user
        user = doGetUserByEmail(restTemplate, email).getBody();
        assertNotNull(user);
        assertEquals(email, user.getEmail());

        // Update current user
        final String newEmail = "complexTestUserChanged@mail.com";
        final String newName = "complex user";
        final String newPassword = "54321";
        final Date newDate = new Date();
        user.setEmail(newEmail);
        user.setName(newName);
        user.setPassword(newPassword);
        user.setBirthDate(newDate);
        user.setUserAccessGroup(testGroup);

        HttpEntity<User> userEntity = new HttpEntity<>(user, restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doUpdateUser(restTemplate, userEntity, user.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user
        user = doGetUserByEmail(restTemplate, newEmail).getBody();
        assertNotNull(user);
        assertEquals(newEmail, user.getEmail());
        assertEquals(newName, user.getName());
        assertEquals(newDate, user.getBirthDate());
        assertEquals(testGroup, user.getUserAccessGroup());

        // Delete current user
        responseEntity = doDeleteUser(restTemplate, user.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user
        assertNull(doGetUserByEmail(restTemplate, newEmail).getBody());
    }
}