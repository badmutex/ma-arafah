package tajmi.abstracts;

import java.util.concurrent.Callable;

/**
 * The type parameters <code>A</code> and <code>B</code>
 * are the types of the instances to have a distance computed between them.
 * @author badi
 */
public abstract class DistanceFunc<A,B> implements Callable<Double> {

    A first;
    B second;

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public DistanceFunc params(A first, B second) {
        this.first = first;
        this.second = second;

        return this;
    }

    public abstract Double call();
}
