package com.javamonkeys.dao;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.user.User;
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
public class GameDaoTest {

    @Autowired
    IGameDao gameDao;

    @Autowired
    IUserDao userDao;

    private User testUser1, testUser2;
    private Game testGame;

    @Before
    public void init() {
        testUser1 = userDao.createUser(new User("user1@javamonkeys.com", "12345"));
        testUser2 = userDao.createUser(new User("user2@javamonkeys.com", "54321"));
        testGame = gameDao.createGame(new Game(new Date(),
                testUser2,
                testUser2,
                testUser1,
                new Date(),
                3600L,
                150L,
                210L,
                "test result",
                "test move text",
                GameStatus.NEW,
                testUser2));
    }

    /* Test method: "createGame()" */
    @Test
    public void createGameTest() {
        Game game = new Game(new Date(),
                testUser1,
                testUser1,
                testUser2,
                new Date(),
                3600L,
                50L,
                120L,
                "result",
                "moveText",
                GameStatus.NEW,
                testUser1);
        assertNull(game.getId());
        game = gameDao.createGame(game);
        assertNotNull(game);
        assertNotNull(game.getId());

        Game newGame = gameDao.getGameById(game.getId());
        assertNotNull(newGame);
        assertEquals(game, newGame);
    }

    /* Test method: "getGameById()" */
    @Test
    public void getGameByIdTest() {
        Game game = gameDao.getGameById(testGame.getId());
        assertNotNull(game);
        assertEquals(testGame, game);
    }

    /* Test method: "updateGame()" */
    @Test
    public void updateGameTest() {

        final Date newMatchDate = new Date();
        newMatchDate.setTime(testGame.getMatchDate().getTime() - 10);

        final Date newStartTime = new Date();
        newStartTime.setTime(testGame.getStartTime().getTime() - 10);

        final User newAuthor = testUser1;
        final User newWhite = testUser1;
        final User newBlack = testUser2;
        final Long newGameLength = 100L;
        final Long newWhiteTime = 200L;
        final Long newBlackTime = 300L;
        final String newResult = "new result";
        final String newMoveText = "new move text";
        final User newWinner = testUser1;
        final GameStatus newGameStatus = GameStatus.FINISHED;

        // check differences
        assertNotEquals(newMatchDate, testGame.getMatchDate());
        assertNotEquals(newStartTime, testGame.getStartTime());
        assertNotEquals(newAuthor, testGame.getAuthor());
        assertNotEquals(newWhite, testGame.getWhite());
        assertNotEquals(newBlack, testGame.getBlack());
        assertNotEquals(newGameLength, testGame.getGameLength());
        assertNotEquals(newWhiteTime, testGame.getWhiteTime());
        assertNotEquals(newBlackTime, testGame.getBlackTime());
        assertNotEquals(newResult, testGame.getResult());
        assertNotEquals(newMoveText, testGame.getMoveText());
        assertNotEquals(newGameStatus, testGame.getStatus());
        assertNotEquals(newWinner, testGame.getWinner());

        // set new values
        testGame.setMatchDate(newMatchDate);
        testGame.setStartTime(newStartTime);
        testGame.setAuthor(newAuthor);
        testGame.setWhite(newWhite);
        testGame.setBlack(newBlack);
        testGame.setGameLength(newGameLength);
        testGame.setWhiteTime(newWhiteTime);
        testGame.setBlackTime(newBlackTime);
        testGame.setResult(newResult);
        testGame.setMoveText(newMoveText);
        testGame.setStatus(newGameStatus);
        testGame.setWinner(newWinner);

        // check set data
        assertEquals(newMatchDate, testGame.getMatchDate());
        assertEquals(newStartTime, testGame.getStartTime());
        assertEquals(newAuthor, testGame.getAuthor());
        assertEquals(newWhite, testGame.getWhite());
        assertEquals(newBlack, testGame.getBlack());
        assertEquals(newGameLength, testGame.getGameLength());
        assertEquals(newWhiteTime, testGame.getWhiteTime());
        assertEquals(newBlackTime, testGame.getBlackTime());
        assertEquals(newResult, testGame.getResult());
        assertEquals(newMoveText, testGame.getMoveText());
        assertEquals(newGameStatus, testGame.getStatus());
        assertEquals(newWinner, testGame.getWinner());

        // update game
        assertTrue(gameDao.updateGame(testGame));

        // check saved data
        Game updatedGame = gameDao.getGameById(testGame.getId());
        assertNotNull(updatedGame);
        assertEquals(testGame, updatedGame);
    }

    /* Test method: "deleteGame()" */
    @Test
    public void deleteGameTest() {
        assertTrue(gameDao.deleteGame(testGame));
        assertNull(gameDao.getGameById(testGame.getId()));
    }
}
