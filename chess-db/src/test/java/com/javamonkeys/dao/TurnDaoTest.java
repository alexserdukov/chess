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
import java.util.List;

import static org.junit.Assert.*;

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
    private User testUser1, testUser2;
    private Game testGame1, testGame2;

    @Before
    public void init() {
        testUser1 = userDao.createUser(new User("testUser1@javamonkeys.com", "12345"));
        testUser2 = userDao.createUser(new User("testUser2@javamonkeys.com", "54321"));

        testGame1 = gameDao.createGame(new Game(testUser1, new Date(), true, 3600L));
        testGame2 = gameDao.createGame(new Game(testUser2, new Date(), false, 1800L));

        testTurn1 = turnDao.createTurn(new Turn(testGame1, testUser1, new Date(), Pieces.KING, "test f1", "test e1", "test fen 1"));
        testTurn2 = turnDao.createTurn(new Turn(testGame1, testUser1, new Date(), Pieces.BISHOP, "test f2", "test e2", "test fen 2"));
        testTurn3 = turnDao.createTurn(new Turn(testGame1, testUser1, new Date(), Pieces.PAWN, "test f3", "test e3", "test fen 3"));
    }

    /* Test method: "createTurn()" */
    @Test
    public void createTurnTest() {
        Turn turn = new Turn(testGame1, testUser1, new Date(), Pieces.KING, "f2", "e4", "new fen");
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
        List<Turn> list = turnDao.getTurns(testGame1);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertTrue(list.contains(testTurn1));
        assertTrue(list.contains(testTurn2));
        assertTrue(list.contains(testTurn3));
    }

    /* Test method: "getLastTurn()" */
    @Test
    public void getLastTurnTest() {
        Turn turn = turnDao.getLastTurn(testGame1);
        assertNotNull(turn);
        assertEquals(testTurn3, turn);
    }

    /* Test method: "updateTurn()" */
    @Test
    public void updateTurnTest() {
        final Game newGame = testGame2;
        final User newUser = testUser2;
        final Date newDate = new Date();
        newDate.setTime(testTurn1.getTurnDate().getTime() - 10);
        final Pieces newPiece = Pieces.BISHOP;
        final String newStartPosition = "new f4";
        final String newEndPosition = "new e4";
        final String newFen = "new FEN";

        // check differences
        assertNotEquals(newGame, testTurn1.getGame());
        assertNotEquals(newUser, testTurn1.getUser());
        assertNotEquals(newDate, testTurn1.getTurnDate());
        assertNotEquals(newPiece, testTurn1.getPiece());
        assertNotEquals(newStartPosition, testTurn1.getStartPosition());
        assertNotEquals(newEndPosition, testTurn1.getEndPosition());
        assertNotEquals(newFen, testTurn1.getFen());

        // set new values
        testTurn1.setGame(newGame);
        testTurn1.setUser(newUser);
        testTurn1.setTurnDate(newDate);
        testTurn1.setPiece(newPiece);
        testTurn1.setStartPosition(newStartPosition);
        testTurn1.setEndPosition(newEndPosition);
        testTurn1.setFen(newFen);

        // update turn
        assertTrue(turnDao.updateTurn(testTurn1));

        // check saved data
        Turn updatedTurn = turnDao.getTurnById(testTurn1.getId());
        assertNotNull(updatedTurn);
        assertEquals(testTurn1, updatedTurn);
    }

    /* Test method: "deleteTurn()" */
    @Test
    public void deleteTurnTest() {
        assertTrue(turnDao.deleteTurn(testTurn1));
        assertNull(turnDao.getTurnById(testTurn1.getId()));
    }
}
