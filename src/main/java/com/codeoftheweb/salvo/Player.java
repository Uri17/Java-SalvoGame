package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayer = new HashSet<>();


    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> score = new HashSet<>();

    private String userName;
    private String email;
    private String password;

    public Player() { }

    public Player(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;

    }

    /*public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return userName + " " + email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Score> getScore() {
        return score;
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }

    public Set<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(Set<GamePlayer> gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Score getScores (Game game){
        //Give score filter from the game is looking for and prints to jason in order findsFirst gp1,gp2 from game1,
        // then gp3 and gp4 from game 2...
        return score.stream().filter(s -> s.getGame().equals(game)).findFirst().orElse(null);
    }

}

