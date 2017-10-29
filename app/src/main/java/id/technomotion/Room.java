package id.technomotion;

/**
 * Created by omayib on 30/10/17.
 */

public class Room {
    private final int id;
    private final String name;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
