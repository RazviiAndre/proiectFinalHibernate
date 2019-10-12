package DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Score {
    @Id
    private int id;
    private int value;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Score() {
    }

    public Score(int id , int value ){
        this.id = id;
        this.value = value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return id == score.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
