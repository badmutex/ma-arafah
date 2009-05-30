
package tajmi.interfaces.som;

import tajmi.som.*;
import java.util.concurrent.Callable;
import tajmi.interfaces.DistanceFunc;

/**
 * Projects a datum onto the field, updates the field, and returns it.
 * @author badi
 */
public abstract class ProjectionFunc<T> implements Callable<SOMParams<T>> {

    T datum;
    SOMParams<T> state;

    DistanceFunc<T> distancef;
    FindBestMatchFunc<T> find_best_match;
    UpdateFunc<T> updatef;

    public void setDistanceFunc(DistanceFunc<T> distancef){
        this.distancef = distancef;
    }

    public void setFindBestMatchFunc(FindBestMatchFunc<T> find_best_match) {
        this.find_best_match = find_best_match;
    }

    public void setUpdateFunc(UpdateFunc<T> updatef){
        this.updatef = updatef;
    }

    public T getDatum() {
        return datum;
    }

    public DistanceFunc<T> getDistanceFunc() {
        return distancef;
    }

    public FindBestMatchFunc<T> getFindBestMatchFunc() {
        return find_best_match;
    }

    public SOMParams<T> getState() {
        return state;
    }

    public UpdateFunc<T> getUpdateFunc() {
        return updatef;
    }

    public ProjectionFunc<T> params (T datum, SOMParams<T> state) {
        this.datum = datum;
        this.state = state;
        return this;
    }

    public abstract SOMParams<T> call();

}
