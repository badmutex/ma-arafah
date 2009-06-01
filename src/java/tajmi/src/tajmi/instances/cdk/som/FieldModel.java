
package tajmi.instances.cdk.som;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author badi
 */
public class FieldModel<T> extends LinkedList<T> {

    T generalizeMedian;

    public T getGeneralizeMedian() {
        assert generalizeMedian != null : "Generalized median should not be null!";
        return generalizeMedian;
    }

    public void setGeneralizeMedian(T generalizeMedian) {
        this.generalizeMedian = generalizeMedian;
    }

    public FieldModel () {
        super();
        generalizeMedian = null;
    }

    public FieldModel (Collection<? extends T> collection) {
        super(collection);
        generalizeMedian = null;
    }

}
