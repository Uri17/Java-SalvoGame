package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;



@Entity
public class Ship {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String type;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="shipLocations")
    private List<String> shipLocations = new ArrayList<>();

    public Ship() {

    }

    public Ship(GamePlayer gamePlayer, String type, List<String> shipLocations){

        this.gamePlayer = gamePlayer;
        this.type = type;
        this.shipLocations = shipLocations;


    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<String> getShipLocations() {
        return shipLocations;
    }

    public void setShipLocations(List<String> shipLocations) {
        this.shipLocations = shipLocations;
    }

}

