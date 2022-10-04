package model.player;

public class Player {

    private final String name;

    private Hand hand;

    private Integer point = 0;

    public Player(String name) {
        this.name = name;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
