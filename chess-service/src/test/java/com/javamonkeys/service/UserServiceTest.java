package com.javamonkeys.service;

import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import com.javamonkeys.service.user.IUserService;
import com.javamonkeys.service.user.UserService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;
import java.util.Date;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(EasyMockRunner.class)
@Transactional
public class UserServiceTest {

    @TestSubject
    IUserService userService = new UserService();

    @Mock(fieldName = "userDao")
    IUserDao mockDao;

    private User testUser;
    private UserAccessGroup testGroup;

    @Before
    public void setup() {
        testUser = new User("testUser1@javamonkeys.com", "12345");
        testUser.setName("test user name 1");
        testUser.setId(1);

        testGroup = new UserAccessGroup("test group 1", true);
        testGroup.setId(1);
    }

    /* Test method: "createUser()" */
    @Test
    public void createUserTest() {
        User testUser = new User("newUser@javamonkeys.com", "12345", new Date(), "new user name", testGroup);
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(null).once();
        expect(mockDao.createUser(eq(testUser))).andReturn(testUser).once();
        replay(mockDao);

        User user = userService.createUser(testUser.getEmail(),
                testUser.getPassword(),
                testUser.getBirthDate(),
                testUser.getName(),
                testUser.getUserAccessGroup());
        verify(mockDao);
        assertNotNull(user);
        assertEquals(testUser, user);
    }

    /* Test method: "createUser()"
    * User with this email is already exists */
    @Test
    public void createUserUserAlreadyExistTest() {
        User testUser = new User("newUser@javamonkeys.com", "12345", new Date(), "new user name", testGroup);
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(testUser).once();
        replay(mockDao);

        assertNull(userService.createUser(testUser.getEmail(),
                testUser.getPassword(),
                testUser.getBirthDate(),
                testUser.getName(),
                testUser.getUserAccessGroup()));
        verify(mockDao);
    }

    /* Test method: "createUser()"
    * Illegal email or password */
    @Test
    public void createUserIllegalArgumentsTest() {
        replay(mockDao);

        assertNull(userService.createUser(null, testUser.getPassword(), new Date(), "new user name", testGroup));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertNull(userService.createUser("", testUser.getPassword(), new Date(), "new user name", testGroup));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertNull(userService.createUser(testUser.getEmail(), null, new Date(), "new user name", testGroup));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertNull(userService.createUser(testUser.getEmail(), "", new Date(), "new user name", testGroup));
        verify(mockDao);
    }

    /* Test method: "getUserById()" */
    @Test
    public void getUserByIdTest() {
        expect(mockDao.getUserById(testUser.getId())).andReturn(testUser).once();
        replay(mockDao);

        assertEquals(testUser, userService.getUserById(testUser.getId()));
        verify(mockDao);
    }

    /* Test method: "getUserById()"
    * User was not found */
    @Test
    public void getUserByIdNotFoundTest() {
        expect(mockDao.getUserById(1)).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.getUserById(1));
        verify(mockDao);
    }

    /* Test method: "getUserById()"
    * Illegal arguments */
    @Test
    public void getUserByIdIllegalArgumentsTest() {
        replay(mockDao);
        assertNull(userService.getUserById(null));
        verify(mockDao);
    }

    /* Test method: "getUserByEmail()" */
    @Test
    public void getUserByEmailTest() {
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(testUser).once();
        replay(mockDao);

        assertEquals(testUser, userService.getUserByEmail(testUser.getEmail()));
        verify(mockDao);
    }

    /* Test method: "getUserByEmail()"
    * user not found */
    @Test
    public void getUsersByEmailNotFoundTest() {
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.getUserByEmail(testUser.getEmail()));
        verify(mockDao);
    }

    /* Test method: "getUserByEmail()"
    * Illegal arguments */
    @Test
    public void getUserByEmailIllegalArgumentsTest() {
        replay(mockDao);
        assertNull(userService.getUserByEmail(null));
        verify(mockDao);
    }

    /* Test method: "updateUser()" */
    @Test
    public void updateUserTest() {
        User existUser = new User("existUser@javamonkeys.com", "12345");
        existUser.setId(1);

        User newUser = new User("newUser@javamonkeys.com", "54321", new Date(), "new name", testGroup);
        newUser.setId(1);

        expect(mockDao.getUserById(existUser.getId())).andReturn(existUser).once();
        expect(mockDao.updateUser(eq(newUser))).andReturn(true).once();
        replay(mockDao);

        assertTrue(userService.updateUser(existUser.getId(), newUser));
        verify(mockDao);
    }

    /* Test method: "updateUser()"
    * User not found*/
    @Test
    public void updateUserNotFoundTest() {
        expect(mockDao.getUserById(testUser.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userService.updateUser(testUser.getId(), testUser));
        verify(mockDao);
    }

    /* Test method: "updateUser()"
    * Illegal arguments */
    @Test
    public void updateUserIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userService.updateUser(null, testUser));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertFalse(userService.updateUser(testUser.getId(), null));
        verify(mockDao);
    }

    /* Test method: "deleteUser()" */
    @Test
    public void deleteUserTest() {
        expect(mockDao.getUserById(testUser.getId())).andReturn(testUser).once();
        expect(mockDao.deleteUser(testUser)).andReturn(true).once();
        replay(mockDao);

        assertTrue(userService.deleteUser(testUser.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUser()"
    * User not found */
    @Test
    public void deleteUserNotFoundTest() {
        expect(mockDao.getUserById(testUser.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userService.deleteUser(testUser.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUser()"
    * Illegal arguments */
    @Test
    public void deleteUserIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userService.deleteUser(null));
        verify(mockDao);
    }
}