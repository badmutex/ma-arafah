
package tajmi.data.som;

import tajmi.data.clusterable.interfaces.som.ProjectionFunc;
import tajmi.data.clusterable.interfaces.som.StopFunc;
import java.util.Random;

/**
 *
 * @author badi
 */
public class SOMParams<T> {

    public Field<T> field;

    public ProjectionFunc<T> project_func;
    public StopFunc<T> stop_func;

    public double restraint;

    public Random random_gen;

    public SOMParams<T> copy () {
        SOMParams<T> novel = new SOMParams();

        novel.field = this.field;
        novel.project_func = this.project_func;
        novel.random_gen = this.random_gen;
        novel.restraint = this.restraint;
        novel.stop_func = this.stop_func;

        return novel;
    }
}
