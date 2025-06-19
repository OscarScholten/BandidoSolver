package bandido.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void shuffle(long seed) {
        Collections.shuffle(cards, new Random(seed));
    }

    public Card draw() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
