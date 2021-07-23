package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="Locations")
    private List<String> salvoLocations = new ArrayList<>();

    private int turn;

    public Salvo() {
    }

    public Salvo(GamePlayer gamePlayer, List<String> salvoLocations, int turn) {
        this.gamePlayer = gamePlayer;
        this.salvoLocations = salvoLocations;
        this.turn = turn;
    }

    public long getId() { return id; }

    public GamePlayer getGamePlayer() { return gamePlayer; }

    public void setGamePlayer(GamePlayer gamePlayer) { this.gamePlayer = gamePlayer; }

    public List<String> getSalvoLocations() { return salvoLocations; }

    public void setSalvoLocations(List<String> salvoLocations) { this.salvoLocations = salvoLocations; }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }
}
