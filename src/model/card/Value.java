package model.card;

public enum Value {
    DEFAULT("DEF", 0),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("T", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    final String representedBy;
    final Integer weight;

    Value(String representedBy, Integer weight) {
        this.representedBy = representedBy;
        this.weight = weight;
    }

    public static Value getValueByRepresentation(String rep){
        for(Value v : Value.values()){
            if(v.representedBy.equalsIgnoreCase(rep))
                return v;
        }
        return DEFAULT;
    }

    public Integer getWeight() {
        return weight;
    }
}
