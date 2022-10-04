package util;

import model.card.Card;
import model.card.Value;
import model.poker.Combination;
import model.poker.CombinationType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombinationIdentifier {
    /**
     * returns the {@link Combination} formed by list of card
     * Combination.combinationValues -> list of cards sorted by their priority
     *
     * @param cards -> [{SEVEN DIAMONDS}, {SEVEN CLUBS}, {NINE CLUBS}, {NINE DIAMONDS}, {KING CLUBS}]
     * @return -> { CombinationType.TWO_PAIR, [NINE, SEVEN, KING]}
     * CombinationType is TWO_PAIR for above input list of cards with NINE being the highest value in TWO_PAIR then SEVEN
     * and KING is not a part of pair so kept at the end of the list.
     */
    public static Combination getCombination(List<Card> cards) {
        //sort cards by value
        cards.sort(Comparator.comparingInt(c -> c.getValue().getWeight()));
        CombinationType combinationType = identifyCombinationType(cards);
        List<Value> combinationValues = null;
        switch (combinationType) {
            case ROYAL_FLUSH:
            case STRAIGHT_FLUSH:
            case FLUSH:
            case STRAIGHT:
            case HIGH_CARD:
                combinationValues = cards.stream()
                        .map(Card::getValue)
                        .collect(Collectors.toList());
                Collections.reverse(combinationValues);
                break;
            case FOUR_OF_A_KIND:
            case FULL_HOUSE:
            case THREE_OF_A_KIND:
            case TWO_PAIRS:
            case PAIR:
                combinationValues = getValueFrequency(cards).entrySet().stream()
                        .sorted(Comparator.comparing(Map.Entry<Value, Long>::getValue)
                                .thenComparing(Map.Entry::getKey).reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                break;
        }
        return new Combination(combinationType, combinationValues);
    }

    /**
     * identifies the {@link CombinationType} formed by the cards
     * @param cards -> [{NINE CLUBS}, {NINE DIAMONDS}, {EIGHT DIAMONDS}, {SEVEN CLUBS}, {THREE CLUBS}]
     * @return CombinationType.PAIR -> since the above list contains a pair of NINE
     */
    private static CombinationType identifyCombinationType(List<Card> cards) {
        if (isRoyalFlush(cards))
            return CombinationType.ROYAL_FLUSH;
        if (isStraightFlush(cards))
            return CombinationType.STRAIGHT_FLUSH;
        if (isFourOfAKind(cards))
            return CombinationType.FOUR_OF_A_KIND;
        if (isFullHouse(cards))
            return CombinationType.FULL_HOUSE;
        if (isFlush(cards))
            return CombinationType.FLUSH;
        if (isStraight(cards))
            return CombinationType.STRAIGHT;
        if (isThreeOfAKind(cards))
            return CombinationType.THREE_OF_A_KIND;
        if (isTwoPair(cards))
            return CombinationType.TWO_PAIRS;
        if (isPair(cards))
            return CombinationType.PAIR;

        return CombinationType.HIGH_CARD;
    }

    private static boolean isPair(List<Card> cards) {
        Map<Value, Long> frequencyMap = getValueFrequency(cards);
        long max = getMostFrequentValueInHand(frequencyMap);
        return max == 2;
    }

    private static boolean isTwoPair(List<Card> cards) {
        Map<Value, Long> frequencyMap = getValueFrequency(cards);
        long max = getMostFrequentValueInHand(frequencyMap);
        return max == 2 && frequencyMap.size() == 3;
    }

    private static boolean isThreeOfAKind(List<Card> cards) {
        Map<Value, Long> frequencyMap = getValueFrequency(cards);
        long max = getMostFrequentValueInHand(frequencyMap);
        return max == 3;
    }

    private static boolean isStraight(List<Card> cards) {
        return isConsecutive(cards);
    }


    private static boolean isFlush(List<Card> cards) {
        return isSameSuit(cards);
    }

    private static boolean isFullHouse(List<Card> cards) {
        Map<Value, Long> frequencyMap = getValueFrequency(cards);
        long max = getMostFrequentValueInHand(frequencyMap);
        return max == 3 && frequencyMap.size() == 2;
    }

    private static boolean isFourOfAKind(List<Card> cards) {
        Map<Value, Long> frequencyMap = getValueFrequency(cards);
        long max = getMostFrequentValueInHand(frequencyMap);
        return max == 4;
    }


    private static long getMostFrequentValueInHand(Map<Value, Long> frequencyMap) {
        long max = 0;
        for (Map.Entry<Value, Long> entry : frequencyMap.entrySet()) {
            max = Math.max(entry.getValue(), max);
        }
        return max;
    }

    private static boolean isStraightFlush(List<Card> cards) {
        return isSameSuit(cards) && isConsecutive(cards);
    }

    private static boolean isRoyalFlush(List<Card> cards) {
        return isSameSuit(cards) && isConsecutive(cards) && getHighCard(cards).equals(Value.ACE);
    }

    private static boolean isSameSuit(List<Card> cards) {
        long suitCountInHand = cards.stream()
                .map(Card::getSuit)
                .distinct().count();
        return suitCountInHand == 1;
    }

    private static boolean isConsecutive(List<Card> cards) {
        Value prevCardValue = cards.get(0).getValue();
        Value currCardValue;
        for (int i = 1; i < 5; i++) {
            currCardValue = cards.get(i).getValue();
            if (currCardValue.getWeight() != prevCardValue.getWeight() + 1)
                return false;
            prevCardValue = currCardValue;
        }
        return true;
    }

    private static Map<Value, Long> getValueFrequency(List<Card> cards) {
        return cards.stream().collect(Collectors.groupingBy(Card::getValue, Collectors.counting()));
    }

    private static Value getHighCard(List<Card> cards) {
        return cards.get(cards.size() - 1).getValue();
    }
}
