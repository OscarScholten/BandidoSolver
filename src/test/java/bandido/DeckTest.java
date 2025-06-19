package bandido;

import bandido.model.Card;
import bandido.model.Deck;
import bandido.util.CardLoader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    private static final String CARDS_JSON = "Cards.json";

    @Test
    public void testLoadCards() throws Exception {
        List<Card> cards = CardLoader.loadCards(CARDS_JSON);
        assertNotNull(cards);
        assertTrue(cards.size() > 0, "Deck should not be empty");
        Set<String> ids = new HashSet<>();
        for (Card c : cards) ids.add(c.getId());
        assertTrue(ids.contains("card1"), "Should contain card1");
    }

    @Test
    public void testShuffleSameSeedSameOrder() throws Exception {
        List<Card> cards1 = CardLoader.loadCards(CARDS_JSON);
        List<Card> cards2 = CardLoader.loadCards(CARDS_JSON);
        Deck deck1 = new Deck(cards1);
        Deck deck2 = new Deck(cards2);
        long seed = 12345L;
        deck1.shuffle(seed);
        deck2.shuffle(seed);
        List<Card> d1 = deck1.getCards();
        List<Card> d2 = deck2.getCards();
        assertEquals(d1.size(), d2.size());
        for (int i = 0; i < d1.size(); i++) {
            assertEquals(d1.get(i).getId(), d2.get(i).getId(), "Decks differ at position " + i);
        }
    }

    @Test
    public void testShuffleDifferentSeedDifferentOrder() throws Exception {
        List<Card> cards1 = CardLoader.loadCards(CARDS_JSON);
        List<Card> cards2 = CardLoader.loadCards(CARDS_JSON);
        Deck deck1 = new Deck(cards1);
        Deck deck2 = new Deck(cards2);
        deck1.shuffle(11111L);
        deck2.shuffle(22222L);
        List<Card> d1 = deck1.getCards();
        List<Card> d2 = deck2.getCards();
        boolean anyDifferent = false;
        for (int i = 0; i < d1.size(); i++) {
            if (!d1.get(i).getId().equals(d2.get(i).getId())) {
                anyDifferent = true;
                break;
            }
        }
        assertTrue(anyDifferent, "Decks with different seeds should differ");
    }
}
