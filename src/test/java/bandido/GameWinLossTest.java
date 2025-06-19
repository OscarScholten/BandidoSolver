package bandido;

import bandido.logic.Game;
import bandido.model.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameWinLossTest {
    @Test
    public void testLossWhenDeckEmptyAndNotWin() {
        Board board = new Board();
        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        Deck deck = new Deck(new ArrayList<>()); // empty deck
        Game game = new Game(board, players, deck);
        assertTrue(game.isLoss(), "Should be loss when deck is empty and not win");
    }

    @Test
    public void testNotLossWhenDeckNotEmpty() {
        Board board = new Board();
        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("card1", List.of("top-left")));
        Deck deck = new Deck(cards);
        Game game = new Game(board, players, deck);
        assertFalse(game.isLoss(), "Should not be loss when deck is not empty");
    }

    @Test
    public void testNotLossWhenWin() {
        Board board = new Board();
        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        Deck deck = new Deck(new ArrayList<>()); // empty deck
        Game game = new Game(board, players, deck) {
            @Override
            public boolean isWin() { return true; }
        };
        assertFalse(game.isLoss(), "Should not be loss when win");
    }
}
