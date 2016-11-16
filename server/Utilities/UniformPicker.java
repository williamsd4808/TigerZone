package Utilities;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by aseber on 3/12/16.
 */

public class UniformPicker<T> {

    private ArrayList<Tuple<T, Double>> originalChoices = new ArrayList<>();
    private ArrayList<Tuple<Range, T>> choiceRatios = new ArrayList<>();
    private final Random rng;
    private double totalWeight;

    public UniformPicker() {

        rng = new Random();

    }

    public UniformPicker(long seed) {

        rng = new Random(seed);

    }

    public void addChoice(T choice, double weight) {

        MathUtilities.putInRange(0.0, weight, Double.MAX_VALUE);

        if (weight > 0.0) {

            originalChoices.add(Tuple.Create(choice, weight));
            totalWeight += weight;

        }


    }

    public T pollChoice() {

        int i = pick();
        totalWeight -= originalChoices.get(i).item2;
        return originalChoices.remove(i).item1;

    }

    public T peekChoice() {

        int i = pick();
        return originalChoices.get(i).item1;

    }

    public boolean validDecisionsToPick() {

        return !originalChoices.isEmpty();

    }

    private int pick() {

        double rand = rng.nextDouble();
        normalizeRatios();

        for (int i = 0; i < choiceRatios.size(); i++) {

            Tuple<Range, T> tuple = choiceRatios.get(i);

            if (tuple.item1.inRange(rand)) {

                return i;

            }

        }

        // This should never occur! If it does, talk to Austin to fix it.
        throw new RuntimeException("UniformPicker: Bad call to pick() method");

    }

    private void normalizeRatios() {

        choiceRatios.clear();

        double beginRatio = 0.0;
        double endRatio;

        for (Tuple<T, Double> tuple : originalChoices) {

            endRatio = beginRatio + tuple.item2 / totalWeight;
            choiceRatios.add(Tuple.Create(new Range(beginRatio, endRatio), tuple.item1));
            beginRatio = endRatio;

        }

    }

    private class Range {

        private double begin;
        private double end;

        public Range(double begin, double end) {

            this.begin = begin;
            this.end = end;

        }

        public boolean inRange(double randomNumber) {

            return (randomNumber >= begin && randomNumber < end);

        }

    }

}