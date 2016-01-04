package com.javamonkeys.entity.turn;

import com.javamonkeys.entity.game.Game;
import com.javamonkeys.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Turns")
public class Turn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "turnDate")
    private Date turnDate;

    @Column(name = "piece")
    @Enumerated(EnumType.STRING)
    private Pieces piece;

    @Column(name = "startPosition")
    private String startPosition;

    @Column(name = "endPosition")
    private String endPosition;

    @Column(name = "fen")
    private String fen;

    public Turn() {}

    public Turn(Game game, User user, Date turnDate, Pieces piece, String startPosition, String endPosition, String fen) {
        this.game = game;
        this.user = user;
        this.turnDate = turnDate;
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.fen = fen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTurnDate() {
        return turnDate;
    }

    public void setTurnDate(Date turnDate) {
        this.turnDate = turnDate;
    }

    public Pieces getPiece() {
        return piece;
    }

    public void setPiece(Pieces piece) {
        this.piece = piece;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Turn turn = (Turn) o;

        if (id != null ? !id.equals(turn.id) : turn.id != null) return false;
        if (game != null ? !game.equals(turn.game) : turn.game != null) return false;
        if (user != null ? !user.equals(turn.user) : turn.user != null) return false;
        if (turnDate != null ? !turnDate.equals(turn.turnDate) : turn.turnDate != null) return false;
        if (piece != turn.piece) return false;
        if (startPosition != null ? !startPosition.equals(turn.startPosition) : turn.startPosition != null)
            return false;
        if (endPosition != null ? !endPosition.equals(turn.endPosition) : turn.endPosition != null) return false;
        return !(fen != null ? !fen.equals(turn.fen) : turn.fen != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (turnDate != null ? turnDate.hashCode() : 0);
        result = 31 * result + (piece != null ? piece.hashCode() : 0);
        result = 31 * result + (startPosition != null ? startPosition.hashCode() : 0);
        result = 31 * result + (endPosition != null ? endPosition.hashCode() : 0);
        result = 31 * result + (fen != null ? fen.hashCode() : 0);
        return result;
    }
}
