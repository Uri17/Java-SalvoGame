package com.codeoftheweb.salvo;

        import org.hibernate.annotations.GenericGenerator;

        import javax.persistence.*;
        import java.util.Date;
        import java.util.HashSet;
        import java.util.Set;


@Entity
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date date;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayer = new HashSet<>();


    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> score = new HashSet<>();

    public Game() {
        this.date = new Date();


    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(Set<GamePlayer> gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    public Set<Score> getScore() {
        return score;
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }
}
