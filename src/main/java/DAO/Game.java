package DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "game")
public class Game {
    @Id
    private int id;
    private String name;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                Objects.equals(name, game.name) &&
                Objects.equals(description, game.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
