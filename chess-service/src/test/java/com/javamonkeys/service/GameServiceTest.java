package com.javamonkeys.service;

import com.javamonkeys.dao.game.IGameDao;
import com.javamonkeys.dao.user.IUserDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.game.GameService;
import com.javamonkeys.service.game.IGameService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;

import java.util.Date;

import static junit.framework.TestCase.*;
import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
@Transactional
public class GameServiceTest {

    @TestSubject
    IGameService gameService = new GameService();

    @Mock(fieldName = "gameDao")
    IGameDao mockGameDao;

    @Mock(fieldName = "userDao")
    IUserDao mockUserDao;

    private Game testGame;
    private User testUser1, testUser2;

    @Before
    public void init() {
        testUser1 = new User("testUser1@javamonkeys.com", "12345");
        testUser1.setId(1);
        testUser2 = new User("testUser2@javamonkeys.com", "54321");
        testUser2.setId(2);
        testGame = new Game(testUser1, new Date(), true, 3600L);
    }

    /* Test method: "createGame()" */
    @Test
    public void createGameTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockGameDao.createGame(eq(testGame))).andReturn(testGame).once();
        replay(mockUserDao);
        replay(mockGameDao);

        Game newGame = gameService.createGame(testUser1.getId(), testGame.getMatchDate(), true, 3600L);
        verify(mockUserDao);
        verify(mockGameDao);
        assertNotNull(newGame);
        assertEquals(testGame, newGame);
    }

    /* Test method: "createGame()"
    * User not found */
    @Test
    public void createGameUserNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockGameDao);
        assertNull(gameService.createGame(testUser1.getId(), testGame.getMatchDate(), true, 3600L));
        verify(mockUserDao);
        verify(mockGameDao);
    }

    /* Test method: "createGame()"
    * Illegal userId */
    @Test
    public void createGameIllegalUserIdTest() {
        replay(mockUserDao);
        replay(mockGameDao);
        assertNull(gameService.createGame(null, testGame.getMatchDate(), true, 3600L));
        verify(mockUserDao);
        verify(mockGameDao);
    }

    /* Test method: "createGame()"
    * Illegal matchDate */
    @Test
    public void createGameIllegalMatchDateTest() {
        replay(mockUserDao);
        replay(mockGameDao);
        assertNull(gameService.createGame(testUser1.getId(), null, true, 3600L));
        verify(mockUserDao);
        verify(mockGameDao);
    }

    /* Test method: "createGame()"
    * Illegal isWhite */
    @Test
    public void createGameIllegalIsWhiteTest() {
        replay(mockUserDao);
        replay(mockGameDao);
        assertNull(gameService.createGame(testUser1.getId(), testGame.getMatchDate(), null, 3600L));
        verify(mockUserDao);
        verify(mockGameDao);
    }

    /* Test method: "createGame()"
    * Illegal gameLength */
    @Test
    public void createGameIllegalGameLengthTest() {
        replay(mockUserDao);
        replay(mockGameDao);
        assertNull(gameService.createGame(testUser1.getId(), testGame.getMatchDate(), true, null));
        verify(mockUserDao);
        verify(mockGameDao);
    }

    /* Test method: "getGameById()" */
    @Test
    public void getGameByIdTest() {
        testGame.setId(1);
        expect(mockGameDao.getGameById(testGame.getId())).andReturn(testGame).once();
        replay(mockGameDao);

        Game game = gameService.getGameById(testGame.getId());
        verify(mockGameDao);
        assertNotNull(game);
        assertEquals(testGame, game);
    }

    /* Test method: "getGameById()"
    * Game not found */
    @Test
    public void getGameByIdNotFoundTest() {
        testGame.setId(1);
        expect(mockGameDao.getGameById(testGame.getId())).andReturn(null).once();
        replay(mockGameDao);
        assertNull(gameService.getGameById(testGame.getId()));
        verify(mockGameDao);
    }

    /* Test method: "getGameById()"
    * Illegal gameId */
    @Test
    public void getGameByIdIllegalGameIdTest() {
        replay(mockGameDao);
        assertNull(gameService.getGameById(null));
        verify(mockGameDao);
    }

    /* Test method: "updateGame()" */
    @Test
    public void updateGameTest() {
        Game existGame = new Game(testUser1, new Date(), true, 3600L);
        existGame.setId(1);

        Game newGame = new Game(new Date(),
                testUser2,
                testUser2,
                testUser1,
                new Date(),
                7200L,
                250L,
                300L,
                "result",
                "move text",
                GameStatus.FINISHED);
        newGame.setId(1);

        expect(mockGameDao.getGameById(existGame.getId())).andReturn(existGame).once();
        expect(mockGameDao.updateGame(eq(newGame))).andReturn(true).once();
        replay(mockGameDao);

        assertTrue(gameService.updateGame(existGame.getId(), newGame));
        verify(mockGameDao);
    }

    /* Test method: "updateGame()"
    * Game not found */
    @Test
    public void updateGameNotFoundTest() {
        testGame.setId(1);
        expect(mockGameDao.getGameById(testGame.getId())).andReturn(null).once();
        replay(mockGameDao);
        assertFalse(gameService.updateGame(testGame.getId(), testGame));
        verify(mockGameDao);
    }

    /* Test method: "updateGame()"
    * Illegal gameId */
    @Test
    public void updateGameIllegalGameIdTest() {
        replay(mockGameDao);
        assertFalse(gameService.updateGame(null, testGame));
        verify(mockGameDao);
    }

    /* Test method: "updateGame()"
    * Illegal game */
    @Test
    public void updateGameIllegalGameTest() {
        replay(mockGameDao);
        assertFalse(gameService.updateGame(testGame.getId(), null));
        verify(mockGameDao);
    }

    /* Test method: "deleteGame()" */
    @Test
    public void deleteGameTest() {
        testGame.setId(1);
        expect(mockGameDao.getGameById(testGame.getId())).andReturn(testGame).once();
        expect(mockGameDao.deleteGame(testGame)).andReturn(true).once();
        replay(mockGameDao);
        assertTrue(gameService.deleteGame(testGame.getId()));
        verify(mockGameDao);
    }

    /* Test method: "deleteGame()"
    * Game not found */
    @Test
    public void deleteGameNotFoundTest() {
        testGame.setId(1);
        expect(mockGameDao.getGameById(testGame.getId())).andReturn(null).once();
        replay(mockGameDao);
        assertFalse(gameService.deleteGame(testGame.getId()));
        verify(mockGameDao);
    }

    /* Test method: "deleteGame()"
    * Illegal gameId */
    @Test
    public void deleteGameIllegalGameIdTest() {
        replay(mockGameDao);
        assertFalse(gameService.deleteGame(null));
        verify(mockGameDao);
    }
}
