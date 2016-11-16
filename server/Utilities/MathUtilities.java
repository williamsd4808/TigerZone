package Utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
public final class MathUtilities {

    public static int putInRange(int min, int current, int max) {

        return Math.min(Math.max(min, current), max); /// This function returns  a number between [min, max] given a current number

    }

    public static double putInRange(double min, double current, double max) {

        return Math.min(Math.max(min, current), max); /// This function returns  a number between [min, max] given a current number

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}