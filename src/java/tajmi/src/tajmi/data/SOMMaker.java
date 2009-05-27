
package tajmi.data;

import java.util.List;
import java.util.Random;
import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.clusterable.instances.num.Vector_DistanceAlgorithm;
import tajmi.data.clusterable.instances.num.som.VectorProjectionFunc;
import tajmi.data.clusterable.instances.num.som.VectorStopFunc;
import tajmi.data.clusterable.instances.num.som.VectorUpdateFunc;
import tajmi.data.clusterable.instances.som.NaiveFindBestMatchFunc;
import tajmi.data.clusterable.interfaces.som.FindBestMatchFunc;
import tajmi.data.clusterable.interfaces.som.InitFunc;
import tajmi.data.clusterable.interfaces.som.NeighborhoodFunc;
import tajmi.data.clusterable.interfaces.som.ProjectionFunc;
import tajmi.data.clusterable.interfaces.som.StopFunc;
import tajmi.data.clusterable.interfaces.som.UpdateFunc;
import tajmi.data.som.Field;
import tajmi.data.som.SOM;
import tajmi.data.som.SOMParams;

/**
 *
 * @author badi
 */
public class SOMMaker<T> {

    SOMParams<T> params;
    List<T> data = null;

    int field_len, field_width;

    FindBestMatchFunc<T> find_bmu_func;
    InitFunc<T> init_func;
    NeighborhoodFunc neighborhood_func;
    ProjectionFunc<T> projection_func;
    StopFunc<T> stop_func;
    UpdateFunc<T> update_func;

    public SOMMaker () {
        params.iteration = 0;
        params.learning_restraint = 0.1;
        params.random_gen = new Random(42);
        params.restraint_modifier = 0.01;

        field_len = 100;
        field_width = 100;
    }

    public SOMMaker<T> randomSeed (long seed) {
        params.random_gen = new Random(seed);
        
        return this;
    }

    public SOMMaker<T> field_size (int len, int width) {
        field_len = len;
        field_width = width;

        return this;
    }

    private SOM<T> makeSOM () {
        assert data != null : "Cannot create a SOM on empty data";

        Field<T> field = new Field<T>(field_len, field_width, data, params.random_gen, init_func);
        params.field = field;

        params.iteration = 0;

        return null;
    }

    public SOM<Vector> makeVectorialSOM () {

        VectorProjectionFunc projectf = new VectorProjectionFunc();

        projectf.setDistanceFunc(new Vector_DistanceAlgorithm());
        projectf.setFindBestMatchFunc(new NaiveFindBestMatchFunc<Vector>());
        projectf.setUpdateFunc(new VectorUpdateFunc());

        params.project_func = (ProjectionFunc<T>) projectf;

        params.stop_func = (StopFunc<T>) new VectorStopFunc();


        return (SOM<Vector>) makeSOM();
    }
}
