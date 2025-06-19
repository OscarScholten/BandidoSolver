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
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OpenTunnel that = (OpenTunnel) o;
            return x == that.x && y == that.y && direction.equals(that.direction);
        }
        @Override
        public int hashCode() {
            int result = Integer.hashCode(x);
            result = 31 * result + Integer.hashCode(y);
            result = 31 * result + direction.hashCode();
            return result;
        }
    }

    public boolean canPlace(Card card, int x, int y, int rotation) {
        // TODO: Check for overlap, connection mismatches, floating cards
        return true;
    }

    public void place(Card card, int x, int y, int rotation) {
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

        for (String exit : card.getExits()) {
            int[] exitPos = getExitPosition(x, y, rotation, exit);
            int ex = exitPos[0];
            int ey = exitPos[1];
            String dir = getExitDirection(x, y, rotation, exit);
            int[] adj = getAdjacentCell(ex, ey, dir);
            String oppDir = oppositeDirection(dir);
            OpenTunnel matching = new OpenTunnel(adj[0], adj[1], oppDir);
            if (openTunnels.contains(matching)) {
                openTunnels.remove(matching);
            } else {
                OpenTunnel newTunnel = new OpenTunnel(ex, ey, dir);
                if (!openTunnels.contains(newTunnel)) {
                    openTunnels.add(newTunnel);
                }
            }
        }
    }

    // Helper: get the global position of an exit after rotation
    private int[] getExitPosition(int x, int y, int rotation, String exit) {
        int ex = x, ey = y;
        switch (rotation) {
            case 0:
                switch (exit) {
                    case "left": ex = x; ey = y; break;
                    case "right": ex = x+1; ey = y; break;
                    case "top-left": ex = x; ey = y; break;
                    case "top-right": ex = x+1; ey = y; break;
                    case "bottom-left": ex = x; ey = y; break;
                    case "bottom-right": ex = x+1; ey = y; break;
                }
                break;
            case 90:
                switch (exit) {
                    case "left": ex = x; ey = y; break;
                    case "right": ex = x; ey = y+1; break;
                    case "top-left": ex = x; ey = y; break;
                    case "top-right": ex = x; ey = y+1; break;
                    case "bottom-left": ex = x; ey = y; break;
                    case "bottom-right": ex = x; ey = y+1; break;
                }
                break;
            case 180:
                switch (exit) {
                    case "left": ex = x+1; ey = y; break;
                    case "right": ex = x; ey = y; break;
                    case "top-left": ex = x+1; ey = y; break;
                    case "top-right": ex = x; ey = y; break;
                    case "bottom-left": ex = x+1; ey = y; break;
                    case "bottom-right": ex = x; ey = y; break;
                }
                break;
            case 270:
                switch (exit) {
                    case "left": ex = x; ey = y+1; break;
                    case "right": ex = x; ey = y; break;
                    case "top-left": ex = x; ey = y+1; break;
                    case "top-right": ex = x; ey = y+1; break;
                    case "bottom-left": ex = x; ey = y+1; break;
                    case "bottom-right": ex = x; ey = y+1; break;
                }
                break;
        }
        return new int[] {ex, ey};
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
        // After placing, openTunnels already contains the correct tunnels
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
