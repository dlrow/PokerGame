package model.poker;

import model.card.Value;

import java.util.List;

public class Combination implements Comparable<Combination> {

    private final CombinationType combinationType;

    private final List<Value> combinationValues;

    public Combination(CombinationType combinationType, List<Value> combinationValues) {
        this.combinationType = combinationType;
        this.combinationValues = combinationValues;
    }

    @Override
    public int compareTo(Combination other) {
        if (this.combinationType.equals(other.combinationType)) {
            int i = 0;
            int loopTill = Math.min(this.combinationValues.size(), other.combinationValues.size());
            while (i < loopTill &&
                    this.combinationValues.get(i).equals(other.combinationValues.get(i))) {
                i++;
            }
            return i == loopTill ? 0 : this.combinationValues.get(i).compareTo(other.combinationValues.get(i));
        } else {
            return this.combinationType.compareTo(other.combinationType);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "Type=" + combinationType +
                ", Values=" + combinationValues +
                '}';
    }
}
