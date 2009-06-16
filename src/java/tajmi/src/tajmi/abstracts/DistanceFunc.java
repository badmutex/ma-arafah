package tajmi.abstracts;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.functional.interfaces.Fun;

/**
 * The type parameters <code>A</code> and <code>B</code>
 * are the types of the instances to have a distance computed between them.<br><br>
 *
 * <code>a -> b -> Double</code>
 * @author badi
 */
public abstract class DistanceFunc<A,B> implements Fun {

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

    public Fun copy() {
        DistanceFunc d = null;
        try {
            d = (DistanceFunc) this.getClass().newInstance().curry(first).curry(second);
        } catch (InstantiationException ex) {
            Logger.getLogger(DistanceFunc.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.exit(-1);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DistanceFunc.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.exit(-1);
        }
        return d;
    }

    public Fun curry(Object arg) {
        if (first == null)
            first = (A) arg;
        else if (second == null)
            second = (B) arg;

        return this;
    }

    public abstract Double call();
}
