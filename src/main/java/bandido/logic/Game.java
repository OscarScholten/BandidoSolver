package bandido.logic;

import bandido.model.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Deck deck;
    private int currentPlayerIdx = 0;

    public Game(Board board, List<Player> players, Deck deck) {
        this.board = board;
        this.players = players;
        this.deck = deck;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    public void nextPlayer() {
        currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
    }

    public Board getBoard() {
        return board;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    // TODO: Add methods for game setup, play turn, check win/loss, etc.

    // Win: all tunnels are blocked
    public boolean isWin() {
        return board.getOpenTunnels().isEmpty();
    }

    // Loss: deck is empty and not all tunnels are blocked
    public boolean isLoss() {
        return deck.getCards().isEmpty() && !isWin();
    }
}
