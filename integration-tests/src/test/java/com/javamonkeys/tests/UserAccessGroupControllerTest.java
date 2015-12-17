package com.javamonkeys.tests;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;

import static org.junit.Assert.*;

public class UserAccessGroupControllerTest {

    private static final String baseUrl = "http://localhost:8555";
    private static UserAccessGroup existingGroup, updateGroup, deleteGroup;

    private static ResponseEntity<UserAccessGroup> doCreateUserAccessGroup(CustomRestTemplate restTemplate, HttpEntity entity) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups/",
                HttpMethod.POST,
                entity,
                UserAccessGroup.class);
    }

    private static ResponseEntity<UserAccessGroup> doGetUserAccessGroupById(CustomRestTemplate restTemplate, Integer groupId) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups/" + groupId,
                HttpMethod.GET,
                new HttpEntity(restTemplate.getHttpHeaders()),
                UserAccessGroup.class);
    }

    private static ResponseEntity<UserAccessGroup> doGetUserAccessGroupByName(CustomRestTemplate restTemplate, String name) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups?name=" + name,
                HttpMethod.GET,
                new HttpEntity(restTemplate.getHttpHeaders()),
                UserAccessGroup.class);
    }

    private static ResponseEntity<String> doUpdateUserAccessGroup(CustomRestTemplate restTemplate, HttpEntity entityUser, Integer groupId) {
        return restTemplate.exchange(baseUrl + "/api/useraccessgroups/" + groupId,
                HttpMethod.PUT,
                entityUser,
                String.class);
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

        // Delete "new test group" if exists
        ResponseEntity<UserAccessGroup> responseEntity = doGetUserAccessGroupByName(restTemplate, "new test group");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            if (responseEntity.getBody() == null)
                fail("User access group \"new test group\" deletion error");
            else
                doDeleteUserAccessGroup(restTemplate, responseEntity.getBody().getId());
        }

        // Create "existing test group"
        restTemplate.addHttpHeader("name", "existing test group");
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(true));
        responseEntity = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User access group \"existing test group\" creation error");
        } else {
            existingGroup = responseEntity.getBody();
            assertNotNull(existingGroup);
        }

        // Create "delete test group"
        restTemplate.addHttpHeader("name", "delete test group");
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(true));
        responseEntity = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User access group \"delete test group\" creation error");
        } else {
            deleteGroup = responseEntity.getBody();
            assertNotNull(deleteGroup);
        }

        // Create "update test group"
        restTemplate.addHttpHeader("name", "update test group");
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(true));
        responseEntity = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            fail("User access group \"update test group\" creation error");
        } else {
            updateGroup = responseEntity.getBody();
            assertNotNull(updateGroup);
        }
    }

    @AfterClass
    public static void clearTestData() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        doDeleteUserAccessGroup(restTemplate, existingGroup.getId());
        doDeleteUserAccessGroup(restTemplate, updateGroup.getId());
        doDeleteUserAccessGroup(restTemplate, deleteGroup.getId());

        UserAccessGroup group = doGetUserAccessGroupByName(restTemplate, "new test group").getBody();
        if (group != null)
            assertEquals(HttpStatus.NO_CONTENT, doDeleteUserAccessGroup(restTemplate, group.getId()).getStatusCode());
    }

    /* Create new user access group
    * User access group doesn't exist in DB
    * Should return HttpStatus.CREATED */
    @Test
    public void createUserAccessGroupTest() {
        final String name = "new test group";

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addHttpHeader("name", name);
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(false));
        ResponseEntity<UserAccessGroup> responseEntity = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        restTemplate.clearHttpHeaders();
        responseEntity = doGetUserAccessGroupByName(restTemplate, name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserAccessGroup group = responseEntity.getBody();
        assertNotNull(group);
        assertEquals(name, group.getName());
        assertEquals(false, group.getIsAdmin());
    }

    /* Create new user access group
    * User access group already exists in DB
    * expected HttpStatus.BAD_REQUEST */
    @Test
    public void createUserAccessGroupAlreadyExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addHttpHeader("name", existingGroup.getName());
        restTemplate.addHttpHeader("isAdmin", existingGroup.getIsAdmin().toString());
        ResponseEntity<UserAccessGroup> responseEntity = doCreateUserAccessGroup(restTemplate, new HttpEntity(restTemplate.getHttpHeaders()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Get user access group by id
    * User access group already exists in DB
    * Should return user */
    @Test
    public void getUserAccessGroupByIdTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<UserAccessGroup> responseEntity = doGetUserAccessGroupById(restTemplate, existingGroup.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserAccessGroup group = responseEntity.getBody();
        assertNotNull(group);
        assertEquals(existingGroup, group);
    }

    /* Get user access group by id
    * Incorrect user access group id
    * expected HttpStatus.NOT_FOUND */
    @Test
    public void getUserAccessGroupIncorrectIdTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<UserAccessGroup> responseEntity = doGetUserAccessGroupById(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Get user access group by name
     * User access group already exists in DB
     * Should return user access group */
    @Test
    public void getUserAccessGroupByNameTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<UserAccessGroup> response = doGetUserAccessGroupByName(restTemplate, existingGroup.getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserAccessGroup findGroup = response.getBody();
        assertNotNull(findGroup);
        assertEquals(existingGroup.getName(), findGroup.getName());
    }

    /* Get user access group by name
     * Incorrect name
     * Should return null */
    @Test
    public void getUserAccessGroupIncorrectNameTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<UserAccessGroup> responseEntity = doGetUserAccessGroupByName(restTemplate, "incorrectName");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Update user access group
     * User access group already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void updateUserAccessGroupTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Update current user access group
        final String newName = "new test name for group";
        assertNotEquals(newName, updateGroup.getName());
        assertTrue(updateGroup.getIsAdmin());
        updateGroup.setName(newName);
        updateGroup.setIsAdmin(false);

        HttpEntity<UserAccessGroup> entity = new HttpEntity<>(updateGroup, restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doUpdateUserAccessGroup(restTemplate, entity, updateGroup.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user access group
        ResponseEntity<UserAccessGroup> responseUser = doGetUserAccessGroupByName(restTemplate, newName);
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());
        UserAccessGroup changedGroup = responseUser.getBody();
        assertNotNull(changedGroup);
        assertEquals(updateGroup, changedGroup);
    }

    /* Update user access group
     * User access group doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void updateUserAccessGroupDoesNotExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        UserAccessGroup newGroup = new UserAccessGroup("new group", false);
        HttpEntity<UserAccessGroup> entity = new HttpEntity<>(newGroup, restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doUpdateUserAccessGroup(restTemplate, entity, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Delete user access group from DB
     * User access group already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void deleteUserAccessGroupTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Check before
        ResponseEntity<UserAccessGroup> responseUser = doGetUserAccessGroupByName(restTemplate, deleteGroup.getName());
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());

        // Delete
        ResponseEntity<String> responseEntity = doDeleteUserAccessGroup(restTemplate, deleteGroup.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check after
        responseUser = doGetUserAccessGroupByName(restTemplate, deleteGroup.getName());
        assertEquals(HttpStatus.NOT_FOUND, responseUser.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Delete user access group from DB
     * User access group doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void deleteUserAccessGroupDoesNotExistTest() {
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<String> responseEntity = doDeleteUserAccessGroup(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////////// COMPLEX TEST /////////////////////////////////////////

    @Test
    public void complexTest() {
        final String name = "complex test group";

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // Delete user access group if exist
        UserAccessGroup group = doGetUserAccessGroupByName(restTemplate, name).getBody();
        if (group != null)
            doDeleteUserAccessGroup(restTemplate, group.getId());

        assertEquals(HttpStatus.NOT_FOUND, doGetUserAccessGroupByName(restTemplate, name).getStatusCode());

        // Create new user access group
        restTemplate.addHttpHeader("name", name);
        restTemplate.addHttpHeader("isAdmin", Boolean.toString(false));
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());
        ResponseEntity<UserAccessGroup> responseUser = doCreateUserAccessGroup(restTemplate, entity);
        assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());
        assertNotNull(responseUser.getBody());
        assertEquals(name, responseUser.getBody().getName());
        assertFalse(responseUser.getBody().getIsAdmin());

        restTemplate.clearHttpHeaders();

        // Get new user access group
        group = doGetUserAccessGroupByName(restTemplate, name).getBody();
        assertNotNull(group);
        assertEquals(name, group.getName());
        assertFalse(group.getIsAdmin());

        // Update current user access group
        final String newName = "complex test new group name";
        group.setName(newName);
        group.setIsAdmin(true);

        HttpEntity<UserAccessGroup> userEntity = new HttpEntity<>(group, restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doUpdateUserAccessGroup(restTemplate, userEntity, group.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user access group
        group = doGetUserAccessGroupByName(restTemplate, newName).getBody();
        assertNotNull(group);
        assertEquals(newName, group.getName());
        assertTrue(group.getIsAdmin());

        // Delete current user access group
        responseEntity = doDeleteUserAccessGroup(restTemplate, group.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Check update user access group
        assertNull(doGetUserAccessGroupByName(restTemplate, newName).getBody());
    }
}
