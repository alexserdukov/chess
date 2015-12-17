package com.javamonkeys.dao.user;

import com.javamonkeys.dao.useraccessgroup.IUserAccessGroupDao;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class UserDaoTest {

    @Autowired
    IUserDao userDao;

    @Autowired
    IUserAccessGroupDao userAccessGroupDao;

    private User testUser;
    private UserAccessGroup testGroup1, testGroup2;

    @Before
    public void setup() {
        testGroup1 = userAccessGroupDao.createUserAccessGroup(new UserAccessGroup("test group 1", false));
        testGroup2 = userAccessGroupDao.createUserAccessGroup(new UserAccessGroup("test group 2", false));

        testUser = userDao.createUser(new User("testUser@javamonkeys.com", "12345", null, "test user name", testGroup1));
    }

    /* Test method: "createUser()" */
    @Test
    public void createUserTest() {
        User user = new User("new user", "12345");
        user.setName("new user name");
        assertNull(user.getId());
        user = userDao.createUser(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        User newUser = userDao.getUserById(user.getId());
        assertNotNull(newUser);
        assertEquals(user, newUser);
    }

    /* Test method: "getUserById()" */
    @Test
    public void getUserByIdTest() {
        User user = userDao.getUserById(testUser.getId());
        assertNotNull(user);
        assertEquals(testUser, user);
    }

    /* Test method: "getUserByEmail()" */
    @Test
    public void getUserByEmailTest() {
        User user = userDao.getUserByEmail(testUser.getEmail());
        assertNotNull(user);
        assertEquals(testUser, user);
    }

    /* Test method: "updateUser()" */
    @Test
    public void updateUserTest() {
        final String newEmail = "new email";
        final String newPass = "87654";
        final String newName = "new name";
        final Date newDate = new Date();

        assertNotEquals(newEmail, testUser.getEmail());
        assertNotEquals(newPass, testUser.getPassword());
        assertNotEquals(newName, testUser.getName());
        assertNotEquals(newDate, testUser.getBirthDate());
        assertNotEquals(testGroup2, testUser.getUserAccessGroup());
        testUser.setEmail(newEmail);
        testUser.setPassword(newPass);
        testUser.setName(newName);
        testUser.setBirthDate(newDate);
        testUser.setUserAccessGroup(testGroup2);

        assertTrue(userDao.updateUser(testUser));
        User updatedUser = userDao.getUserById(testUser.getId());
        assertNotNull(updatedUser);
        assertEquals(testUser, updatedUser);
    }

    /* Test method: "deleteUser()" */
    @Test
    public void deleteUserTest() {
        assertTrue(userDao.deleteUser(testUser));
        assertNull(userDao.getUserById(testUser.getId()));
    }
}

