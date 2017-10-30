package id.technomotion;

/**
 * Created by omayib on 30/10/17.
 */

public class Room {
    private final int id;
    private final String name;
    private String latestConversation="";

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getLatestConversation() {
        return latestConversation;
    }

    public void setLatestConversation(String latestConversation) {
        this.latestConversation = latestConversation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id == room.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
