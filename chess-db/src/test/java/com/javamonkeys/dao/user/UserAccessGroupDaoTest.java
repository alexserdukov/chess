package com.javamonkeys.dao.user;

import com.javamonkeys.dao.useraccessgroup.IUserAccessGroupDao;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class UserAccessGroupDaoTest {

    @Autowired
    IUserAccessGroupDao userAccessGroupDao;

    private UserAccessGroup testGroup;

    @Before
    public void setup() {
        testGroup = userAccessGroupDao.createUserAccessGroup(new UserAccessGroup("test group 1", false));
    }

    /* Test method: "createUserAccessGroup()" */
    @Test
    public void createUserAccessGroupTest() {
        UserAccessGroup group = new UserAccessGroup("new group", false);
        assertNull(group.getId());
        group = userAccessGroupDao.createUserAccessGroup(group);
        assertNotNull(group);
        assertNotNull(group.getId());

        UserAccessGroup newGroup = userAccessGroupDao.getUserAccessGroupById(group.getId());
        assertNotNull(newGroup);
        assertEquals(group, newGroup);
    }

    /* Test method: "getUserAccessGroupById()" */
    @Test
    public void getUserAccessGroupByIdTest() {
        UserAccessGroup group = userAccessGroupDao.getUserAccessGroupById(testGroup.getId());
        assertNotNull(group);
        assertEquals(testGroup, group);
    }

    /* Test method: "getUserAccessGroupByEmail()" */
    @Test
    public void getUserAccessGroupByEmailTest() {
        UserAccessGroup group = userAccessGroupDao.getUserAccessGroupByName(testGroup.getName());
        assertNotNull(group);
        assertEquals(testGroup, group);
    }

    /* Test method: "updateUserAccessGroup()" */
    @Test
    public void updateUserAccessGroupTest() {
        final String newName = "new test name 1";

        assertNotEquals(newName, testGroup.getName());
        assertFalse(testGroup.getIsAdmin());
        testGroup.setName(newName);
        testGroup.setIsAdmin(true);

        assertTrue(userAccessGroupDao.updateUserAccessGroup(testGroup));
        UserAccessGroup updatedGroup = userAccessGroupDao.getUserAccessGroupById(testGroup.getId());
        assertNotNull(updatedGroup);
        assertEquals(testGroup, updatedGroup);
    }

    /* Test method: "deleteUserAccessGroup()" */
    @Test
    public void deleteUserAccessGroupTest() {
        assertTrue(userAccessGroupDao.deleteUserAccessGroup(testGroup));
        assertNull(userAccessGroupDao.getUserAccessGroupById(testGroup.getId()));
    }

}
