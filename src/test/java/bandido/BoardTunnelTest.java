package bandido;

import bandido.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTunnelTest {
    @Test
    public void testTunnelClosureSimple() {
        // Create a start card with two exits: right and left
        Card start = new Card("start", List.of("left", "right"));
        Board board = new Board();
        board.initializeWithStartCard(start, 0, 0, 0);
        // Should have two open tunnels
        assertEquals(2, board.getOpenTunnels().size());
        Set<String> exits = new HashSet<>();
        for (Board.OpenTunnel t : board.getOpenTunnels()) {
            exits.add(t.direction);
        }
        assertTrue(exits.contains("left"));
        assertTrue(exits.contains("right"));

        // Place a card to the right with a left exit, closing the right tunnel
        Card rightCard = new Card("card1", List.of("left"));
        board.place(rightCard, 2, 0, 0); // Use 2,0 for 2x1 card adjacency
        // Now only the left tunnel should remain open
        List<Board.OpenTunnel> tunnels = board.getOpenTunnels();
        assertEquals(1, tunnels.size());
        assertEquals("left", tunnels.get(0).direction);
    }

    @Test
    public void testTunnelClosureBothSides() {
        Card start = new Card("start", List.of("left", "right"));
        Board board = new Board();
        board.initializeWithStartCard(start, 0, 0, 0);
        Card rightCard = new Card("card1", List.of("left"));
        Card leftCard = new Card("card2", List.of("right"));
        board.place(rightCard, 2, 0, 0); // Use 2,0 for right
        board.place(leftCard, -2, 0, 0); // Use -2,0 for left
        // All tunnels should be closed
        assertEquals(0, board.getOpenTunnels().size());
    }
}
