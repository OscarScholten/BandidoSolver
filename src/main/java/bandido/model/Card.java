package bandido.model;

import java.util.List;

public class Card {
    private final String id;
    private final List<String> exits;

    public Card(String id, List<String> exits) {
        this.id = id;
        this.exits = exits;
    }

    public String getId() {
        return id;
    }

    public List<String> getExits() {
        return exits;
    }

    // TODO: Add rotation logic and equals/hashCode
}
