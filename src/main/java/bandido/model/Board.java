package bandido.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    // Map from (x, y) to placed card and its rotation
    private final Map<String, PlacedCard> placedCards = new HashMap<>();
    // Track open tunnels (exits) on the board
    private final List<OpenTunnel> openTunnels = new ArrayList<>();

    public static class PlacedCard {
        public final Card card;
        public final int rotation;
        public PlacedCard(Card card, int rotation) {
            this.card = card;
            this.rotation = rotation;
        }
    }

    public static class OpenTunnel {
        public final int x, y;
        public final String direction; // e.g., "top-left"
        public OpenTunnel(int x, int y, String direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    public boolean canPlace(Card card, int x, int y, int rotation) {
        // TODO: Check for overlap, connection mismatches, floating cards
        return true;
    }

    public void place(Card card, int x, int y, int rotation) {
        // Place the card at (x, y) and also mark the second occupied cell depending on rotation
        placedCards.put(x + "," + y, new PlacedCard(card, rotation));
        // TODO: Since cards are 2x1, also mark the adjacent cell as occupied based on rotation
        // For example, if rotation == 0, also occupy (x+1, y)
    }

    public void initializeWithStartCard(Card startCard, int x, int y, int rotation) {
        place(startCard, x, y, rotation);
        // Add all exits of the start card as open tunnels
        for (String exit : startCard.getExits()) {
            openTunnels.add(new OpenTunnel(x, y, exit));
        }
    }

    public List<OpenTunnel> getOpenTunnels() {
        return new ArrayList<>(openTunnels);
    }

    public boolean isOccupied(int x, int y) {
        return placedCards.containsKey(x + "," + y);
    }

    // TODO: Update openTunnels in place() when new cards are placed
    // TODO: Add methods for checking win/loss, tunnel state, etc.
}
