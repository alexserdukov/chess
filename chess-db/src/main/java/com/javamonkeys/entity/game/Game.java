package com.javamonkeys.entity.game;

import com.javamonkeys.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Games")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "matchDate")
    private Date matchDate;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "userWhiteId")
    private User white;

    @ManyToOne
    @JoinColumn(name = "userBlackId")
    private User black;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "gameLength")
    private Long gameLength;

    @Column(name = "whiteTime")
    private Long whiteTime;

    @Column(name = "blackTime")
    private Long blackTime;

    @Column(name = "result")
    private String result;

    @Column(name = "moveText")
    private String moveText;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    public Game() {}

    public Game(User user, Boolean isWhite, Long gameLength) {
        setAuthor(user);
        setMatchDate(new Date());
        if (isWhite) {
            setWhite(user);
        } else {
            setBlack(user);
        }
    }

    public Game(Date matchDate, User author, User white, User black, Date startTime, Long gameLength, Long whiteTime,
                Long blackTime, String result, String moveText, GameStatus status) {
        this.matchDate = matchDate;
        this.author = author;
        this.white = white;
        this.black = black;
        this.startTime = startTime;
        this.gameLength = gameLength;
        this.whiteTime = whiteTime;
        this.blackTime = blackTime;
        this.result = result;
        this.moveText = moveText;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public User getWhite() {
        return white;
    }

    public void setWhite(User white) {
        this.white = white;
    }

    public User getBlack() {
        return black;
    }

    public void setBlack(User black) {
        this.black = black;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMoveText() {
        return moveText;
    }

    public void setMoveText(String moveText) {
        this.moveText = moveText;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getGameLength() {
        return gameLength;
    }

    public void setGameLength(Long gameLength) {
        this.gameLength = gameLength;
    }

    public Long getWhiteTime() {
        return whiteTime;
    }

    public void setWhiteTime(Long whiteTime) {
        this.whiteTime = whiteTime;
    }

    public Long getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(Long blackTime) {
        this.blackTime = blackTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (gameLength != game.gameLength) return false;
        if (whiteTime != game.whiteTime) return false;
        if (blackTime != game.blackTime) return false;
        if (id != null ? !id.equals(game.id) : game.id != null) return false;
        if (matchDate != null ? !matchDate.equals(game.matchDate) : game.matchDate != null) return false;
        if (author != null ? !author.equals(game.author) : game.author != null) return false;
        if (white != null ? !white.equals(game.white) : game.white != null) return false;
        if (black != null ? !black.equals(game.black) : game.black != null) return false;
        if (startTime != null ? !startTime.equals(game.startTime) : game.startTime != null) return false;
        if (result != null ? !result.equals(game.result) : game.result != null) return false;
        if (moveText != null ? !moveText.equals(game.moveText) : game.moveText != null) return false;
        return status == game.status;

    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (matchDate != null ? matchDate.hashCode() : 0);
        result1 = 31 * result1 + (author != null ? author.hashCode() : 0);
        result1 = 31 * result1 + (white != null ? white.hashCode() : 0);
        result1 = 31 * result1 + (black != null ? black.hashCode() : 0);
        result1 = 31 * result1 + (startTime != null ? startTime.hashCode() : 0);
        result1 = 31 * result1 + (int) (gameLength ^ (gameLength >>> 32));
        result1 = 31 * result1 + (int) (whiteTime ^ (whiteTime >>> 32));
        result1 = 31 * result1 + (int) (blackTime ^ (blackTime >>> 32));
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (moveText != null ? moveText.hashCode() : 0);
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        return result1;
    }
}
