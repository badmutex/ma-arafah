
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;
import tajmi.abstracts.DistanceFunc;

/**
 * Projects a datum onto the field, updates the field, and returns it.
 * @author badi
 */
public abstract class ProjectionFunc<F,D> implements Callable<SOMParams<F,D>> {

    D datum;
    SOMParams<F,D> state;

    DistanceFunc<D> distancef;
    FindBestMatchFunc<F,D> find_best_match;
    UpdateFunc<F,D> updatef;

    public void setDistanceFunc(DistanceFunc distancef){
        this.distancef = distancef;
    }

    public void setFindBestMatchFunc(FindBestMatchFunc find_best_match) {
        this.find_best_match = find_best_match;
    }

    public void setUpdateFunc(UpdateFunc updatef){
        this.updatef = updatef;
    }

    public D getDatum() {
        return datum;
    }

    public DistanceFunc<D> getDistanceFunc() {
        return distancef;
    }

    public FindBestMatchFunc<F,D> getFindBestMatchFunc() {
        return find_best_match;
    }

    public SOMParams<F,D> getState() {
        return state;
    }

    public UpdateFunc<F,D> getUpdateFunc() {
        return updatef;
    }

    public ProjectionFunc<F,D> params (D datum, SOMParams<F,D> state) {
        this.datum = datum;
        this.state = state;
        return this;
    }

    public abstract SOMParams<F,D> call();

}
