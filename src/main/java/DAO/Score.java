package DAO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int value;

    @OneToOne(mappedBy = "score")
    private Player player;

    public Score() {
    }

    public Score(int id , int value ){
        this.id = id;
        this.value = value;
    }

    public Score(int value){
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
