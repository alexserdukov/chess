package com.javamonkeys.service;

import com.javamonkeys.dao.turn.ITurnDao;
import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.game.GameStatus;
import com.javamonkeys.entity.turn.Pieces;
import com.javamonkeys.entity.turn.Turn;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.game.IGameService;
import com.javamonkeys.service.turn.ITurnService;
import com.javamonkeys.service.turn.TurnService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(EasyMockRunner.class)
@Transactional
public class TurnServiceTest {

    @TestSubject
    ITurnService turnService = new TurnService();

    @Mock(fieldName = "turnDao")
    ITurnDao mockTurnDao;

    @Mock(fieldName = "gameService")
    IGameService mockGameService;

    private User testUser1, testUser2;
    private Game testGame1, testGame2;
    private Turn testTurn1, testTurn2, testTurn3;

    @Before
    public void init() {
        testUser1 = new User("testUser1@javamonkeys.com", "12345");
        testUser1.setId(1);
        testUser2 = new User("testUser2@javamonkeys.com", "54321");
        testUser2.setId(2);

        testGame1 = new Game(testUser1, new Date(), true, 3600L);
        testGame1.setId(1);
        testGame2 = new Game(testUser2, new Date(), false, 7200L);
        testGame1.setId(2);

        testTurn1 = new Turn(testGame1, testUser1, new Date(), Pieces.BISHOP, "test f3", "test e3", "test fen 1");
        testTurn1.setId(1);
        testTurn2 = new Turn(testGame1, testUser2, new Date(), Pieces.QUEEN, "test e5", "test f7", "test fen 2");
        testTurn2.setId(2);
        testTurn3 = new Turn(testGame1, testUser1, new Date(), Pieces.ROOK, "test c6", "test e6", "test fen 3");
        testTurn3.setId(3);
    }

    /* Test method: "createTurn()"
    * game isn't over */
    @Test
    public void createTurnGameNotOverTest() {
        Turn turn = new Turn(testGame1, testUser1, new Date(), Pieces.KING, "test f1", "test e1", "fen");
        expect(mockTurnDao.createTurn(eq(turn))).andReturn(turn).once();
        replay(mockTurnDao);
        replay(mockGameService);

        Turn newTurn = turnService.createTurn(turn.getGame(),
                turn.getUser(),
                turn.getTurnDate(),
                turn.getPiece(),
                turn.getStartPosition(),
                turn.getEndPosition(),
                turn.getFen(),
                false);
        verify(mockTurnDao);
        verify(mockGameService);
        assertNotNull(newTurn);
        assertEquals(turn, newTurn);
    }

    /* Test method: "createTurn()"
    * game is over */
    @Test
    public void createTurnGameOverTest() {
        Turn turn = new Turn(testGame1, testUser1, new Date(), Pieces.KING, "new f1", "new e1", "new fen");
        expect(mockTurnDao.createTurn(eq(turn))).andReturn(turn).once();
        testGame1.setStatus(GameStatus.FINISHED);
        expect(mockGameService.updateGame(testGame1.getId(), testGame1)).andReturn(true).once();
        replay(mockTurnDao);
        replay(mockGameService);

        Turn newTurn = turnService.createTurn(turn.getGame(),
                turn.getUser(),
                turn.getTurnDate(),
                turn.getPiece(),
                turn.getStartPosition(),
                turn.getEndPosition(),
                turn.getFen(),
                true);
        verify(mockTurnDao);
        verify(mockGameService);
        assertNotNull(newTurn);
        assertEquals(turn, newTurn);
    }

    /* Test method: "createTurn()"
    * illegal game */
    @Test
    public void createTurnIllegalGameTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(null,
                testTurn1.getUser(),
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                testTurn1.getEndPosition(),
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal user */
    @Test
    public void createTurnIllegalUserTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                null,
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                testTurn1.getEndPosition(),
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal turnDate */
    @Test
    public void createTurnIllegalTurnDateTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                null,
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                testTurn1.getEndPosition(),
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal Piece */
    @Test
    public void createTurnIllegalPieceTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                testTurn1.getTurnDate(),
                null,
                testTurn1.getStartPosition(),
                testTurn1.getEndPosition(),
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal startPosition */
    @Test
    public void createTurnIllegalStartPositionTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                null,
                testTurn1.getEndPosition(),
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal endPosition */
    @Test
    public void createTurnIllegalEndPositionTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                null,
                testTurn1.getFen(),
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal FEN */
    @Test
    public void createTurnIllegalFENTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                testTurn1.getEndPosition(),
                null,
                false));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "createTurn()"
    * illegal isGameOver */
    @Test
    public void createTurnIllegalIsGameOverTest() {
        replay(mockTurnDao);
        replay(mockGameService);

        assertNull(turnService.createTurn(testGame1,
                testUser1,
                testTurn1.getTurnDate(),
                testTurn1.getPiece(),
                testTurn1.getStartPosition(),
                testTurn1.getFen(),
                testTurn1.getFen(),
                null));
        verify(mockTurnDao);
        verify(mockGameService);
    }

    /* Test method: "getTurnById()" */
    @Test
    public void getTurnByIdTest() {
        expect(mockTurnDao.getTurnById(testTurn1.getId())).andReturn(testTurn1).once();
        replay(mockTurnDao);
        Turn turn = turnService.getTurnById(testTurn1.getId());
        verify(mockTurnDao);
        assertNotNull(turn);
        assertEquals(testTurn1, turn);
    }

    /* Test method: "getTurnById()"
    * Turn not found */
    @Test
    public void getTurnByIdNotFoundTest() {
        expect(mockTurnDao.getTurnById(testTurn1.getId())).andReturn(null).once();
        replay(mockTurnDao);
        assertNull(turnService.getTurnById(testTurn1.getId()));
        verify(mockTurnDao);
    }

    /* Test method: "getTurnById()"
    * Illegal turnId */
    @Test
    public void getTurnByIdIllegalTurnIdTest() {
        replay(mockTurnDao);
        assertNull(turnService.getTurnById(null));
        verify(mockTurnDao);
    }

    /* Test method: "getTurns()" */
    @Test
    public void getTurnsTest() {
        List<Turn> list = new ArrayList<>(3);
        list.add(testTurn1);
        list.add(testTurn2);
        list.add(testTurn3);
        expect(mockTurnDao.getTurns(testGame1)).andReturn(list).once();
        replay(mockTurnDao);

        List<Turn> newList = turnService.getTurns(testGame1);
        verify(mockTurnDao);
        assertNotNull(newList);
        assertEquals(list, newList);
    }

    /* Test method: "getTurns()"
    * Illegal game */
    @Test
    public void getTurnsIllegalGameTest() {
        replay(mockTurnDao);
        assertNull(turnService.getTurns(null));
        verify(mockTurnDao);
    }

    /* Test method: "getLastTurn()" */
    @Test
    public void getLastTurnTest() {
        expect(mockTurnDao.getLastTurn(testGame1)).andReturn(testTurn1).once();
        replay(mockTurnDao);
        Turn turn = turnService.getLastTurn(testGame1);
        verify(mockTurnDao);
        assertNotNull(turn);
        assertEquals(testTurn1, turn);
    }

    /* Test method: "getLastTurn()"
    * Turn not found */
    @Test
    public void getLastTurnNotFoundTest() {
        expect(mockTurnDao.getLastTurn(testGame1)).andReturn(null).once();
        replay(mockTurnDao);
        assertNull(turnService.getLastTurn(testGame1));
        verify(mockTurnDao);
    }

    /* Test method: "getLastTurn()"
    * Illegal game */
    @Test
    public void getLastTurnIllegalGameTest() {
        replay(mockTurnDao);
        assertNull(turnService.getLastTurn(null));
        verify(mockTurnDao);
    }

    /* Test method: "updateTurn()" */
    @Test
    public void updateTurnTest() {
        Turn existTurn = new Turn(testGame1, testUser1, new Date(), Pieces.BISHOP, "test f3", "test e3", "test fen 1");
        existTurn.setId(1);

        Date newDate = new Date();
        newDate.setTime(existTurn.getTurnDate().getTime() - 10);
        Turn newTurn = new Turn(testGame2, testUser2, newDate, Pieces.ROOK, "new f5", "new e7", "new fen");
        newTurn.setId(1);

        expect(mockTurnDao.getTurnById(existTurn.getId())).andReturn(existTurn).once();
        expect(mockTurnDao.updateTurn(eq(newTurn))).andReturn(true).once();
        replay(mockTurnDao);

        assertTrue(turnService.updateTurn(existTurn.getId(), newTurn));
        verify(mockTurnDao);
    }

    /* Test method: "updateTurn()"
    * Turn not found */
    @Test
    public void updateTurnNotFoundTest() {
        expect(mockTurnDao.getTurnById(testTurn1.getId())).andReturn(null).once();
        replay(mockTurnDao);
        assertFalse(turnService.updateTurn(testTurn1.getId(), testTurn1));
        verify(mockTurnDao);
    }

    /* Test method: "updateTurn()"
    * Illegal turnId */
    @Test
    public void updateTurnIllegalTurnIdTest() {
        replay(mockTurnDao);
        assertFalse(turnService.updateTurn(null, testTurn1));
        verify(mockTurnDao);
    }

    /* Test method: "updateTurn()"
    * Illegal turn */
    @Test
    public void updateTurnIllegalTurnTest() {
        replay(mockTurnDao);
        assertFalse(turnService.updateTurn(testTurn1.getId(), null));
        verify(mockTurnDao);
    }

    /* Test method: "deleteTurn()" */
    @Test
    public void deleteTurnTest() {
        expect(mockTurnDao.getTurnById(testTurn1.getId())).andReturn(testTurn1).once();
        expect(mockTurnDao.deleteTurn(testTurn1)).andReturn(true).once();
        replay(mockTurnDao);
        assertTrue(turnService.deleteTurn(testTurn1.getId()));
        verify(mockTurnDao);
    }

    /* Test method: "deleteTurn()"
    * turn not found */
    @Test
    public void deleteTurnNotFoundTest() {
        expect(mockTurnDao.getTurnById(testTurn1.getId())).andReturn(null).once();
        replay(mockTurnDao);
        assertFalse(turnService.deleteTurn(testTurn1.getId()));
        verify(mockTurnDao);
    }

    /* Test method: "deleteTurn()"
    * Illegal turnId */
    @Test
    public void deleteTurnIllegalTurnIdTest() {
        replay(mockTurnDao);
        assertFalse(turnService.deleteTurn(null));
        verify(mockTurnDao);
    }
}
