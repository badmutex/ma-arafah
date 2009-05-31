
package tajmi.som;

import tajmi.abstracts.som.ProjectionFunc;
import tajmi.abstracts.som.StopFunc;
import java.util.Random;
import tajmi.abstracts.som.ShowStatusFunc;

/**
 *
 * @author badi
 */
public class SOMParams<F,D> {

    public Field<F> field;

    public ProjectionFunc<F,D> project_func;
    public StopFunc stop_func;

    public double learning_restraint;
    public double restraint_modifier;

    public Random random_gen;

    /** times the entire dataset has been projected */
    public int iterations;
    /** times a projection has been done in the current iteration */
    public int projections;

    /** function responsible for notifying the user of the progress */
    public ShowStatusFunc show_status_func;

    
    public SOMParams<F,D> copy () {
        SOMParams novel = new SOMParams();

        novel.field = this.field;
        novel.project_func = this.project_func;
        novel.random_gen = this.random_gen;
        novel.learning_restraint = this.learning_restraint;
        novel.stop_func = this.stop_func;
        novel.iterations = this.iterations;
        novel.projections = this.projections;
        novel.show_status_func = this.show_status_func;

        return novel;
    }
}
