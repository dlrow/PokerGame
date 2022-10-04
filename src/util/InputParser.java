package util;

import model.card.Card;
import model.card.Suit;
import model.card.Value;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InputParser {

    /**
     * parses single line of input string into cards
     * e.g.
     * input ->  9C 9D 8H
     * output -> [{NINE CLUBS}, {NINE DIAMONDS}, {EIGHT HEARTS}]
     * */
    public static Queue<Card> parseCards(String st) {
        Queue<Card> parsedCard = new LinkedList<>();
        List<String> rawCards = Arrays.asList(st.split(" "));
        rawCards.forEach(rc -> {
            Character suitChar = rc.charAt(rc.length() - 1);
            String valueStr = rc.substring(0, rc.length() - 1);
            parsedCard.add(new Card(Value.getValueByRepresentation(valueStr), Suit.getSuitByChar(suitChar)));
        });
        return parsedCard;
    }
}
