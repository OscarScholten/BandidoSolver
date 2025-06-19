package bandido;

import bandido.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTunnelTest {
    @Test
    public void testTunnelClosureSimple() {
        // Create a start card with two exits: right and left
        Card start = new Card("start", List.of("left", "right"));
        Board board = new Board();
        board.initializeWithStartCard(start, 0, 0, 0);
        // Should have two open tunnels
        List<Board.OpenTunnel> tunnels = board.getOpenTunnels();
        assertEquals(2, tunnels.size());
        // Assert locations and directions
        boolean foundLeft = false, foundRight = false;
        for (Board.OpenTunnel t : tunnels) {
            if (t.direction.equals("left")) {
                assertEquals(0, t.x);
                assertEquals(0, t.y);
                foundLeft = true;
            } else if (t.direction.equals("right")) {
                assertEquals(1, t.x);
                assertEquals(0, t.y);
                foundRight = true;
            }
        }
        assertTrue(foundLeft);
        assertTrue(foundRight);

        // Place a card to the right with a left exit, closing the right tunnel
        Card rightCard = new Card("card1", List.of("left"));
        board.place(rightCard, 2, 0, 0); // Use 2,0 for 2x1 card adjacency
        // Now only the left tunnel should remain open
        tunnels = board.getOpenTunnels();
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
