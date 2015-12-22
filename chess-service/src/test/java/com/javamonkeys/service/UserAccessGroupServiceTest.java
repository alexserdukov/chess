package com.javamonkeys.service;

import com.javamonkeys.dao.useraccessgroup.IUserAccessGroupDao;
import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;
import com.javamonkeys.service.useraccessgroup.IUserAccessGroupService;
import com.javamonkeys.service.useraccessgroup.UserAccessGroupService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
@Transactional
public class UserAccessGroupServiceTest {

    @TestSubject
    IUserAccessGroupService userAccessGroupService = new UserAccessGroupService();

    @Mock(fieldName = "userAccessGroupDao")
    IUserAccessGroupDao mockDao;

    private UserAccessGroup testGroup;

    @Before
    public void setup() {
        testGroup = new UserAccessGroup("test group 1", true);
        testGroup.setId(1);
    }

    /* Test method: "createUserAccessGroup()" */
    @Test
    public void createUserAccessGroupTest() {
        UserAccessGroup group = new UserAccessGroup("new group", false);
        expect(mockDao.getUserAccessGroupByName(group.getName())).andReturn(null).once();
        expect(mockDao.createUserAccessGroup(eq(group))).andReturn(group).once();
        replay(mockDao);

        UserAccessGroup newGroup = userAccessGroupService.createUserAccessGroup(group.getName(), false);
        verify(mockDao);
        assertNotNull(group);
        assertEquals(newGroup, group);
    }

    /* Test method: "createUserAccessGroup()"
    * Group with this name is already exists */
    @Test
    public void createUserAccessGroupAlreadyExistTest() {
        UserAccessGroup group = new UserAccessGroup("new group", false);
        expect(mockDao.getUserAccessGroupByName(group.getName())).andReturn(group).once();
        replay(mockDao);

        assertNull(userAccessGroupService.createUserAccessGroup(group.getName(), false));
        verify(mockDao);
    }

    /* Test method: "createUserAccessGroup()"
    * Illegal arguments */
    @Test
    public void createUserAccessGroupIllegalArgumentsTest() {
        replay(mockDao);

        assertNull(userAccessGroupService.createUserAccessGroup(null, testGroup.getIsAdmin()));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertNull(userAccessGroupService.createUserAccessGroup(testGroup.getName(), null));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupById()" */
    @Test
    public void getUserAccessGroupByIdTest() {
        expect(mockDao.getUserAccessGroupById(testGroup.getId())).andReturn(testGroup).once();
        replay(mockDao);

        assertEquals(testGroup, userAccessGroupService.getUserAccessGroupById(testGroup.getId()));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupById()"
    * User access group was not found */
    @Test
    public void getUserAccessGroupByIdNotFoundTest() {
        expect(mockDao.getUserAccessGroupById(testGroup.getId())).andReturn(null).once();
        replay(mockDao);

        assertNull(userAccessGroupService.getUserAccessGroupById(testGroup.getId()));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupById()"
    * Illegal arguments */
    @Test
    public void getUserAccessGroupByIdIllegalArgumentsTest() {
        replay(mockDao);
        assertNull(userAccessGroupService.getUserAccessGroupById(null));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupByName()" */
    @Test
    public void getUserAccessGroupByNameTest() {
        expect(mockDao.getUserAccessGroupByName(testGroup.getName())).andReturn(testGroup).once();
        replay(mockDao);

        assertEquals(testGroup, userAccessGroupService.getUserAccessGroupByName(testGroup.getName()));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupByName()"
    * User access group was not found */
    @Test
    public void getUsersAccessGroupByNameNotFoundTest() {
        expect(mockDao.getUserAccessGroupByName(testGroup.getName())).andReturn(null).once();
        replay(mockDao);

        assertNull(userAccessGroupService.getUserAccessGroupByName(testGroup.getName()));
        verify(mockDao);
    }

    /* Test method: "getUserAccessGroupByName()"
    * Illegal arguments */
    @Test
    public void getUserAccessGroupByNameIllegalArgumentsTest() {
        replay(mockDao);
        assertNull(userAccessGroupService.getUserAccessGroupByName(null));
        verify(mockDao);
    }

    /* Test method: "updateUserAccessGroup()" */
    @Test
    public void updateUserAccessGroupTest() {
        UserAccessGroup existGroup = new UserAccessGroup("exist group", false);
        existGroup.setId(1);

        UserAccessGroup newGroup = new UserAccessGroup("new group", true);
        newGroup.setId(1);

        expect(mockDao.getUserAccessGroupById(existGroup.getId())).andReturn(existGroup).once();
        expect(mockDao.updateUserAccessGroup(newGroup)).andReturn(true).once();
        replay(mockDao);

        assertTrue(userAccessGroupService.updateUserAccessGroup(existGroup.getId(), newGroup));
        verify(mockDao);
    }

    /* Test method: "updateUserAccessGroup()"
    * User access group was not found*/
    @Test
    public void updateUserAccessGroupNotFoundTest() {
        expect(mockDao.getUserAccessGroupById(testGroup.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userAccessGroupService.updateUserAccessGroup(testGroup.getId(), testGroup));
        verify(mockDao);
    }

    /* Test method: "updateUserAccessGroup()"
    * Illegal arguments */
    @Test
    public void updateUserAccessGroupIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userAccessGroupService.updateUserAccessGroup(null, testGroup));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertFalse(userAccessGroupService.updateUserAccessGroup(testGroup.getId(), null));
        verify(mockDao);
    }

    /* Test method: "deleteUserAccessGroup()" */
    @Test
    public void deleteUserAccessGroupTest() {
        expect(mockDao.getUserAccessGroupById(testGroup.getId())).andReturn(testGroup).once();
        expect(mockDao.deleteUserAccessGroup(testGroup)).andReturn(true).once();
        replay(mockDao);

        assertTrue(userAccessGroupService.deleteUserAccessGroup(testGroup.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUserAccessGroup()"
    * User access group was not found */
    @Test
    public void deleteUserAccessGroupNotFoundTest() {
        expect(mockDao.getUserAccessGroupById(testGroup.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userAccessGroupService.deleteUserAccessGroup(testGroup.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUserAccessGroup()"
    * Illegal arguments */
    @Test
    public void deleteUserAccessGroupIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userAccessGroupService.deleteUserAccessGroup(null));
        verify(mockDao);
    }
}
