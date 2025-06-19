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
        int dx = 0, dy = 0;
        switch (rotation) {
            case 0: dx = 1; break;
            case 90: dy = 1; break;
            case 180: dx = -1; break;
            case 270: dy = -1; break;
        }
        int x2 = x + dx;
        int y2 = y + dy;
        placedCards.put(x2 + "," + y2, new PlacedCard(card, rotation));

        // For each exit, check if it connects to an adjacent card's exit
        List<OpenTunnel> toRemove = new ArrayList<>();
        List<OpenTunnel> toAdd = new ArrayList<>();
        for (String exit : card.getExits()) {
            int[] exitPos = getExitPosition(x, y, rotation, exit);
            int ex = exitPos[0];
            int ey = exitPos[1];
            String dir = getExitDirection(x, y, rotation, exit);
            int[] adj = getAdjacentCell(ex, ey, dir);
            PlacedCard adjCard = placedCards.get(adj[0] + "," + adj[1]);
            boolean closed = false;
            if (adjCard != null) {
                // Check if the adjacent card has an exit facing this card
                for (String adjExit : adjCard.card.getExits()) {
                    int[] adjExitPos = getExitPosition(adj[0], adj[1], adjCard.rotation, adjExit);
                    String adjDir = getExitDirection(adj[0], adj[1], adjCard.rotation, adjExit);
                    if (adjExitPos[0] == ex && adjExitPos[1] == ey && adjDir.equals(oppositeDirection(dir))) {
                        closed = true;
                        // Remove the matching tunnel from openTunnels if it exists
                        toRemove.add(new OpenTunnel(ex, ey, dir));
                        toRemove.add(new OpenTunnel(adj[0], adj[1], adjDir));
                        break;
                    }
                }
            }
            if (!closed) {
                toAdd.add(new OpenTunnel(ex, ey, dir));
            }
        }
        // Remove all tunnels that are now closed
        openTunnels.removeIf(t -> toRemove.stream().anyMatch(r -> r.x == t.x && r.y == t.y && r.direction.equals(t.direction)));
        // Add new open tunnels
        openTunnels.addAll(toAdd);
    }

    // Helper: get the global position and direction of an exit after rotation
    private int[] getExitPosition(int x, int y, int rotation, String exit) {
        // For 2x1 cards, exits are always on the edge of the card
        // Card at (x, y) with rotation 0 occupies (x, y) and (x+1, y)
        // Exits:
        //   left: (x, y), direction "left"
        //   right: (x+1, y), direction "right"
        //   top-left: (x, y), direction "top"
        //   top-right: (x+1, y), direction "top"
        //   bottom-left: (x, y), direction "bottom"
        //   bottom-right: (x+1, y), direction "bottom"
        int ex = x, ey = y;
        String dir = exit;
        switch (rotation) {
            case 0:
                switch (exit) {
                    case "left": ex = x; ey = y; dir = "left"; break;
                    case "right": ex = x+1; ey = y; dir = "right"; break;
                    case "top-left": ex = x; ey = y; dir = "top"; break;
                    case "top-right": ex = x+1; ey = y; dir = "top"; break;
                    case "bottom-left": ex = x; ey = y; dir = "bottom"; break;
                    case "bottom-right": ex = x+1; ey = y; dir = "bottom"; break;
                }
                break;
            case 90:
                switch (exit) {
                    case "left": ex = x; ey = y; dir = "top"; break;
                    case "right": ex = x; ey = y+1; dir = "bottom"; break;
                    case "top-left": ex = x; ey = y; dir = "left"; break;
                    case "top-right": ex = x; ey = y+1; dir = "left"; break;
                    case "bottom-left": ex = x; ey = y; dir = "right"; break;
                    case "bottom-right": ex = x; ey = y+1; dir = "right"; break;
                }
                break;
            case 180:
                switch (exit) {
                    case "left": ex = x+1; ey = y; dir = "right"; break;
                    case "right": ex = x; ey = y; dir = "left"; break;
                    case "top-left": ex = x+1; ey = y; dir = "bottom"; break;
                    case "top-right": ex = x; ey = y; dir = "bottom"; break;
                    case "bottom-left": ex = x+1; ey = y; dir = "top"; break;
                    case "bottom-right": ex = x; ey = y; dir = "top"; break;
                }
                break;
            case 270:
                switch (exit) {
                    case "left": ex = x; ey = y+1; dir = "bottom"; break;
                    case "right": ex = x; ey = y; dir = "top"; break;
                    case "top-left": ex = x; ey = y+1; dir = "right"; break;
                    case "top-right": ex = x; ey = y; dir = "right"; break;
                    case "bottom-left": ex = x; ey = y+1; dir = "left"; break;
                    case "bottom-right": ex = x; ey = y; dir = "left"; break;
                }
                break;
        }
        // Encode direction as int for legacy, but return as string in OpenTunnel
        // We'll use 0 for left, 1 for right, 2 for top, 3 for bottom, but here just return ex, ey, and dir
        // We'll ignore the int[2] direction encoding and just use the string
        // So, return ex, ey, and encode dir as hashCode (for compatibility with old code)
        return new int[] {ex, ey, dir.hashCode()};
    }

    // Helper: rotate an exit direction string by rotation degrees
    private String rotateDirection(String dir, int rotation) {
        // Now handled in getExitPosition, so just return dir
        return dir;
    }

    // Helper: get the opposite direction string
    private String oppositeDirection(String dir) {
        switch (dir) {
            case "left": return "right";
            case "right": return "left";
            case "top": return "bottom";
            case "bottom": return "top";
        }
        return dir;
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

    // Helper: get the direction string for an exit after rotation
    private String getExitDirection(int x, int y, int rotation, String exit) {
        // Use the same logic as getExitPosition, but return the direction string
        switch (rotation) {
            case 0:
                switch (exit) {
                    case "left": return "left";
                    case "right": return "right";
                    case "top-left": return "top";
                    case "top-right": return "top";
                    case "bottom-left": return "bottom";
                    case "bottom-right": return "bottom";
                }
                break;
            case 90:
                switch (exit) {
                    case "left": return "top";
                    case "right": return "bottom";
                    case "top-left": return "left";
                    case "top-right": return "left";
                    case "bottom-left": return "right";
                    case "bottom-right": return "right";
                }
                break;
            case 180:
                switch (exit) {
                    case "left": return "right";
                    case "right": return "left";
                    case "top-left": return "bottom";
                    case "top-right": return "bottom";
                    case "bottom-left": return "top";
                    case "bottom-right": return "top";
                }
                break;
            case 270:
                switch (exit) {
                    case "left": return "bottom";
                    case "right": return "top";
                    case "top-left": return "right";
                    case "top-right": return "right";
                    case "bottom-left": return "left";
                    case "bottom-right": return "left";
                }
                break;
        }
        return exit;
    }

    // Helper: get the adjacent cell for a given exit direction
    private int[] getAdjacentCell(int x, int y, String dir) {
        switch (dir) {
            case "left": return new int[] {x-1, y};
            case "right": return new int[] {x+1, y};
            case "top": return new int[] {x, y-1};
            case "bottom": return new int[] {x, y+1};
        }
        return new int[] {x, y};
    }
}
