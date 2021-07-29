package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ServiceLoader;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double score;
    private LocalDateTime finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    public Score() { }

    public Score(double score, LocalDateTime finishDate, Player player, Game game) {
        this.score = score;
        this.finishDate = finishDate;
        this.player = player;
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame(Game game) {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
