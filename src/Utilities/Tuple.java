package Utilities;

/**
 * Created by Austin Seber2 on 11/9/2016.
 */
public class Tuple<A, B> {

    public A item1;
    public B item2;

    public Tuple(A item1, B item2) {

        this.item1 = item1;
        this.item2 = item2;

    }

    public static <A, B> Tuple Create(A item1, B item2) {

        return new Tuple<>(item1, item2);

    }

}
