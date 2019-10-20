package DAO;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String rating;

    public Game(int id , String name , String rating){
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public Game(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                Objects.equals(name, game.name) &&
                Objects.equals(rating, game.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating);
    }
}
