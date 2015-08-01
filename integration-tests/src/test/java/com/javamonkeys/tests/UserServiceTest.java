package com.javamonkeys.tests;

import com.javamonkeys.dao.user.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";
    private CustomResponseErrorHandler customResponseErrorHandler = new CustomResponseErrorHandler();

    private static ResponseEntity<User> doLogin(CustomRestTemplate restTemplate, HttpEntity entity){
        return restTemplate.exchange(baseUrl + "/api/users/login", HttpMethod.GET, entity, User.class);
    }

    private static ResponseEntity<User> doLogout(CustomRestTemplate restTemplate, HttpEntity entity){
        return restTemplate.exchange(baseUrl + "/api/users/logout", HttpMethod.POST, entity, User.class);
    }

    private static ResponseEntity<String> doRegister(CustomRestTemplate restTemplate, HttpEntity entity){
        return restTemplate.exchange(baseUrl + "/api/users/register", HttpMethod.POST, entity, String.class);
    }

    private static ResponseEntity<User> doGetUser(CustomRestTemplate restTemplate, HttpEntity entity, Integer userId){
        return restTemplate.exchange(baseUrl + "/api/users/" + userId, HttpMethod.GET, entity, User.class);
    }

    private static ResponseEntity<String> doUpdateUser(CustomRestTemplate restTemplate, HttpEntity entityUser, Integer userId){
        return restTemplate.exchange(baseUrl + "/api/users/" + userId, HttpMethod.PUT, entityUser, String.class);
    }

    private static ResponseEntity<String> doDeleteUser(CustomRestTemplate restTemplate, HttpEntity entity, Integer userId){
        return restTemplate.exchange(baseUrl + "/api/users/" + userId, HttpMethod.DELETE, entity, String.class);
    }

    @BeforeClass
    public static void init(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // 1. ADD user "Filippov@javamonkeys.com"
        restTemplate.addBasicAuthHttpHeaders("Filippov@javamonkeys.com", "12345");
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 2. ADD user "DeleteUser@javamonkeys.com"
        restTemplate.addBasicAuthHttpHeaders("DeleteUser@javamonkeys.com", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());

        responseEntityUser = doLogin(restTemplate, entity);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 3. ADD user "UpdateUser@javamonkeys.com"
        restTemplate.addBasicAuthHttpHeaders("UpdateUser@javamonkeys.com", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());

        responseEntityUser = doLogin(restTemplate, entity);
        if (responseEntityUser.getStatusCode() == HttpStatus.BAD_REQUEST) { // user not found
            ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        // 4. DELETE user "NewUserTest@javamonkeys.com"
        restTemplate.addBasicAuthHttpHeaders("NewUserTest@javamonkeys.com", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());

        responseEntityUser = doLogin(restTemplate, entity);
        if (responseEntityUser.getStatusCode() == HttpStatus.OK){
            User currentUser = responseEntityUser.getBody();
            ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, entity, currentUser.getId());
            assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        }

        // 4. DELETE user "ComplexTestUser@javamonkeys.com" if exist
        restTemplate.addBasicAuthHttpHeaders("ComplexTestUser@javamonkeys.com", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());

        responseEntityUser = doLogin(restTemplate, entity);
        if (responseEntityUser.getStatusCode() == HttpStatus.OK){
            User currentUser = responseEntityUser.getBody();
            ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, entity, currentUser.getId());
            assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        }
    }

    ///////////////////////////////////////// REGISTER /////////////////////////////////////////

    @Test
    public void testRegisterUserAlreadyExistsException() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("Filippov@javamonkeys.com", "12345");
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testRegisterIncorrectAuthorization() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // NULL auth
        restTemplate.addHttpHeader("Authorization", null);
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Empty string auth
        restTemplate.addHttpHeader("Authorization", "");
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Incorrect BASE64 string auth
        restTemplate.addHttpHeader("Authorization", "Basic 1234567890abcdefghijk");
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

    }

    @Test
    public void testRegisterNewUser() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("NewUserTest@javamonkeys.com", "12345");
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    ///////////////////////////////////////// LOGIN //////////////////////////////////////////

    @Test
    public void testLoginUserAlreadyExists() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("Filippov@javamonkeys.com", "12345");
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntity = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Filippov@javamonkeys.com".toLowerCase(), responseEntity.getBody().getEmail().toLowerCase());
    }

    @Test
    public void testLoginUserNotFound() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("UserNotFound@javamonkeys.com", "12345");
        HttpEntity<User> entity = new HttpEntity<User>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntity = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testLoginIncorrectAuthorization() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // NULL auth
        restTemplate.addHttpHeader("Authorization", null);
        HttpEntity<User> entity = new HttpEntity<User>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntity = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Empty string auth
        restTemplate.addHttpHeader("Authorization", "");
        entity = new HttpEntity<User>(restTemplate.getHttpHeaders());

        responseEntity = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // Incorrect BASE64 string auth
        restTemplate.addHttpHeader("Authorization", "Basic 1234567890abcdefghijk");
        entity = new HttpEntity<User>(restTemplate.getHttpHeaders());

        responseEntity = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

    }

    //////////////////////////////////////// LOGOUT /////////////////////////////////////////

    @Test
    public void testLogout() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("Filippov@javamonkeys.com", "12345");

        // LOGIN
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        User beforeUser = responseEntityUser.getBody();

        restTemplate.clearHttpHeaders();

        // LOGOUT
        restTemplate.addHttpHeader("id", Integer.toString(beforeUser.getId()));
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity responseEntity = doLogout(restTemplate, entity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // LOGOUT with incorrect id
        restTemplate.addHttpHeader("id", "-999");
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntity = doLogout(restTemplate, entity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /////////////////////////////////////// GET USER ///////////////////////////////////////

    @Test
    public void testGetUser() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("Filippov@javamonkeys.com", "12345");

        // LOGIN for check user id
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        User firstUser = responseEntityUser.getBody();
        assertNotNull(firstUser);
        assertNotNull(firstUser.getId());

        // Correct user id
        restTemplate.clearHttpHeaders();
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, firstUser.getId());
        User secondUser = responseEntityUser.getBody();
        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertEquals(firstUser.getId(), secondUser.getId());
        assertEquals(firstUser.getEmail(), secondUser.getEmail());

        // Incorrect user id
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, -999);
        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNull(responseEntityUser.getBody());
    }

    ///////////////////////////////////// DELETE USER //////////////////////////////////////

    @Test
    public void testDeleteUser() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("DeleteUser@javamonkeys.com", "12345");

        // LOGIN for check user id
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        User currentUser = responseEntityUser.getBody();
        assertNotNull(currentUser);
        assertNotNull(currentUser.getId());

        // DELETE user
        restTemplate.clearHttpHeaders();
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity responseEntity = doDeleteUser(restTemplate, entity, currentUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check user
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, currentUser.getId());
        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNull(responseEntityUser.getBody());

        // Incorrect user id
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntity = doDeleteUser(restTemplate, entity, -999);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////// UPDATE USER //////////////////////////////////////

    @Test
    public void testUpdateUser() {

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("UpdateUser@javamonkeys.com", "12345");

        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        User beforeUser = responseEntityUser.getBody();
        assertNotNull(beforeUser);
        assertNotNull(beforeUser.getId());

        // UPDATE user
        String beforeName = beforeUser.getName();
        String afterName  = (beforeName == null || beforeName.isEmpty()) ? "update user" : beforeName + "_postfix)";

        Date beforeBirthDate = beforeUser.getBirthDate();
        Date afterBirthDate;

        if (beforeBirthDate == null) {
            LocalDate newLocalDate = LocalDate.now();
            Instant instant = newLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            afterBirthDate = Date.from(instant);
        } else {
            afterBirthDate = null;
        }

        assertNotEquals(beforeName, afterName);
        assertNotEquals(beforeBirthDate, afterBirthDate);

        beforeUser.setName(afterName);
        beforeUser.setBirthDate(afterBirthDate);

        restTemplate.clearHttpHeaders();
        HttpEntity<User> entityUser = new HttpEntity<User>(beforeUser, restTemplate.getHttpHeaders());

        ResponseEntity responseEntity = doUpdateUser(restTemplate, entityUser, beforeUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check user
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, beforeUser.getId());
        User afterUser = responseEntityUser.getBody();

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNotNull(afterUser);

        assertEquals(afterName, afterUser.getName());
        assertEquals(afterBirthDate, afterUser.getBirthDate());

        // Incorrect user id
        entityUser = new HttpEntity<User>(beforeUser, restTemplate.getHttpHeaders());

        responseEntity = doUpdateUser(restTemplate, entityUser, -999);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /////////////////////////////////// COMPLEX TESTING ////////////////////////////////////

    @Test
    public void complexUserTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addBasicAuthHttpHeaders("ComplexTestUser@javamonkeys.com", "12345");

        User newUser;

        // 1. LOGIN (CHECK THAT USER NOT FOUND)
        HttpEntity<String> entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<User> responseEntityUser = doLogin(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityUser.getStatusCode());
        assertEquals(null, responseEntityUser.getBody());

        // 2. REGISTER
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doRegister(restTemplate, entity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        // 3. LOGIN (USER MAY EXISTS)
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doLogin(restTemplate, entity);
        newUser = responseEntityUser.getBody();
        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNotNull(responseEntityUser.getBody());
        assertEquals("ComplexTestUser@javamonkeys.com".toLowerCase(), responseEntityUser.getBody().getEmail().toLowerCase());
        assertEquals("12345", responseEntityUser.getBody().getPassword());


        // 4. UPDATE
        String beforeName = newUser.getName();
        String afterName  = "ComplexTestUser";

        String beforePass = newUser.getPassword();
        String afterPass = "54321";

        Date beforeBirthDate = newUser.getBirthDate();
        Date afterBirthDate;

        LocalDate newLocalDate = LocalDate.now();
        Instant instant = newLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        afterBirthDate = Date.from(instant);

        assertNotEquals(beforeName, afterName);
        assertNotEquals(beforePass, afterPass);
        assertNotEquals(beforeBirthDate, afterBirthDate);

        newUser.setName(afterName);
        newUser.setBirthDate(afterBirthDate);
        newUser.setPassword(afterPass);

        restTemplate.clearHttpHeaders();
        HttpEntity<User> entityUser = new HttpEntity<User>(newUser, restTemplate.getHttpHeaders());

        responseEntity = doUpdateUser(restTemplate, entityUser, newUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // 5. GET USER (TO CHECK UPDATES)
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, newUser.getId());
        User updateUser = responseEntityUser.getBody();

        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNotNull(updateUser);
        assertNotNull(updateUser.getId());

        assertEquals(afterName, updateUser.getName());
        assertEquals(afterPass, updateUser.getPassword());
        assertEquals(afterBirthDate, updateUser.getBirthDate());

        // 6. DELETE USER
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntity = doDeleteUser(restTemplate, entity, updateUser.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check user
        entity = new HttpEntity<String>(restTemplate.getHttpHeaders());

        responseEntityUser = doGetUser(restTemplate, entity, updateUser.getId());
        assertEquals(HttpStatus.OK, responseEntityUser.getStatusCode());
        assertNull(responseEntityUser.getBody());
    }


}