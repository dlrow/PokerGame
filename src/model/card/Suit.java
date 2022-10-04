package model.card;

public enum Suit {
    DIAMONDS('D'),
    HEARTS('H'),
    SPADES('S'),
    CLUBS('C'),
    DEFAULT('X');

    Character representedBy;

    Suit(Character representedBy) {
        this.representedBy = representedBy;
    }

    public static Suit getSuitByChar(Character ch){
        for(Suit s : Suit.values()){
            if(s.representedBy.equals(ch))
                return s;
        }
        return DEFAULT;
    }

}
