
package tajmi.functional.instances.math;

import tajmi.functional.interfaces.Fun;
import tajmi.functional.interfaces.Result;

/**
 *
 * @author badi
 */
public class Add implements Fun {

    Number fst, snd;

    public Number call()  {
        return fst.doubleValue() + snd.doubleValue();
    }

    public Fun curry(Object arg) {

        if (fst == null)
            fst = (Number) arg;
        else if (snd == null)
            snd = (Number) arg;

        return this;
    }

    public Result params (Number fst, Number snd) {
        return (Result) this.curry(fst).curry(snd);
    }

    public Fun copy() {
        return new Add().curry(fst).curry(snd);
    }

    


}
