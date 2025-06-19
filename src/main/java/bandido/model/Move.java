package bandido.model;

public class Move {
    private final Card card;
    private final int x;
    private final int y;
    private final int rotation; // 0, 90, 180, 270 degrees

    public Move(Card card, int x, int y, int rotation) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public Card getCard() {
        return card;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
        return rotation;
    }
}
