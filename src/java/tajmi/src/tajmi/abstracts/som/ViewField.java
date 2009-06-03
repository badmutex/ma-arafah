
package tajmi.abstracts.som;

import java.util.concurrent.Callable;
import tajmi.som.Field;

/**
 *
 * @author badi
 */
public abstract class ViewField<F> implements Callable {

    Field<F> field;

    public ViewField<F> params (Field<F> field) {
        this.field = field;
        return this;
    }

    public Field<F> getField() {
        return field;
    }


    public abstract Object call ();

}
