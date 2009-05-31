
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;
import tajmi.abstracts.DistanceFunc;

/**
 * Projects a datum onto the field, updates the field, and returns it.
 * @author badi
 */
public abstract class ProjectionFunc implements Callable<SOMParams> {

    Object datum;
    SOMParams state;

    DistanceFunc distancef;
    FindBestMatchFunc find_best_match;
    UpdateFunc updatef;

    public void setDistanceFunc(DistanceFunc distancef){
        this.distancef = distancef;
    }

    public void setFindBestMatchFunc(FindBestMatchFunc find_best_match) {
        this.find_best_match = find_best_match;
    }

    public void setUpdateFunc(UpdateFunc updatef){
        this.updatef = updatef;
    }

    public Object getDatum() {
        return datum;
    }

    public DistanceFunc getDistanceFunc() {
        return distancef;
    }

    public FindBestMatchFunc getFindBestMatchFunc() {
        return find_best_match;
    }

    public SOMParams getState() {
        return state;
    }

    public UpdateFunc getUpdateFunc() {
        return updatef;
    }

    public ProjectionFunc params (Object datum, SOMParams state) {
        this.datum = datum;
        this.state = state;
        return this;
    }

    public abstract SOMParams call();

}
