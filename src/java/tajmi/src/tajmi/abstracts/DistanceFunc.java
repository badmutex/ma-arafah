package tajmi.abstracts;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public abstract class DistanceFunc<T> implements Callable<Double> {

    T first, second;

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public DistanceFunc params(T first, T second) {
        this.first = first;
        this.second = second;

        return this;
    }

    public abstract Double call();
}
