package com.javamonkeys.dao;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.turn.ITurnDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.turn.Pieces;
import com.javamonkeys.entity.turn.Turn;
import com.javamonkeys.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class TurnDaoTest {

    @Autowired
    ITurnDao turnDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    IGameDao gameDao;

    private Turn testTurn1, testTurn2, testTurn3;
    private User testUser;
    private Game testGame;

    @Before
    public void init() {
        testUser = userDao.createUser(new User("testUser@javamonkeys.com", "12345"));
        testGame = gameDao.createGame(new Game(testUser, true, 3600L));
        testTurn1 = turnDao.createTurn(new Turn(testGame, testUser, new Date(), Pieces.KING, "test f1", "test e1", "test fen 1"));
        testTurn2 = turnDao.createTurn(new Turn(testGame, testUser, new Date(), Pieces.BISHOP, "test f2", "test e2", "test fen 2"));
        testTurn3 = turnDao.createTurn(new Turn(testGame, testUser, new Date(), Pieces.PAWN, "test f3", "test e3", "test fen 3"));
    }

    /* Test method: "createTurn()" */
    @Test
    public void createTurnTest() {
        Turn turn = new Turn(testGame, testUser, new Date(), Pieces.KING, "f2", "e4", "new fen");
        assertNull(turn.getId());
        turn = turnDao.createTurn(turn);
        assertNotNull(turn);
        assertNotNull(turn.getId());

        Turn newTurn = turnDao.getTurnById(turn.getId());
        assertNotNull(newTurn);
        assertEquals(turn, newTurn);
    }

    /* Test method: "getTurnById()" */
    @Test
    public void getTurnTest() {
        Turn turn = turnDao.getTurnById(testTurn1.getId());
        assertNotNull(turn);
        assertEquals(testTurn1, turn);
    }

    /* Test method: "getTurns()" */
    @Test
    public void getTurnsTest() {

    }

    /* Test method: "getLastTurn()" */
    @Test
    public void getLastTurnTest() {

    }

    /* Test method: "updateTurn()" */
    @Test
    public void updateTurnTest() {

    }

    /* Test method: "deleteTurn()" */
    @Test
    public void deleteTurnTest() {

    }
}
