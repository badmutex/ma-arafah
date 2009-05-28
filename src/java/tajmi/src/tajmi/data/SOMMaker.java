
package tajmi.data;

import java.util.List;
import java.util.Random;
import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.clusterable.instances.num.Vector_DistanceAlgorithm;
import tajmi.data.clusterable.instances.num.som.VectorInitFunc;
import tajmi.data.clusterable.instances.num.som.VectorProjectionFunc;
import tajmi.data.clusterable.instances.num.som.VectorStopFunc;
import tajmi.data.clusterable.instances.num.som.VectorUpdateFunc;
import tajmi.data.clusterable.instances.som.NaiveFindBestMatchFunc;
import tajmi.data.clusterable.instances.som.SimpleShowStatusFunc;
import tajmi.data.clusterable.interfaces.som.FindBestMatchFunc;
import tajmi.data.clusterable.interfaces.som.InitFunc;
import tajmi.data.clusterable.interfaces.som.NeighborhoodFunc;
import tajmi.data.clusterable.interfaces.som.ProjectionFunc;
import tajmi.data.clusterable.interfaces.som.ShowStatusFunc;
import tajmi.data.clusterable.interfaces.som.StopFunc;
import tajmi.data.clusterable.interfaces.som.UpdateFunc;
import tajmi.data.som.Field;
import tajmi.data.som.SOM;
import tajmi.data.som.SOMParams;

/**
 * This is where one should set the parameters for the SOM before creating it. <br>
 * Be aware: very stateful
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
    ShowStatusFunc show_status_func;

    public SOMMaker () {

        show_status_func = new SimpleShowStatusFunc();

        params = new SOMParams<T>();

        
        params.iterations = 0;
        params.learning_restraint = 0.1;
        params.random_gen = new Random(42);
        params.restraint_modifier = 0.01;

        field_len = 50;
        field_width = 50;

        params.show_status_func = this.show_status_func;

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

    public void setFind_bmu_func(FindBestMatchFunc<T> find_bmu_func) {
        this.find_bmu_func = find_bmu_func;
    }

    public void setInit_func(InitFunc<T> init_func) {
        this.init_func = init_func;
    }

    public void setNeighborhood_func(NeighborhoodFunc neighborhood_func) {
        this.neighborhood_func = neighborhood_func;
    }

    public void setProjection_func(ProjectionFunc<T> projection_func) {
        this.projection_func = projection_func;
    }

    public void setShow_status_func(ShowStatusFunc show_status_func) {
        this.show_status_func = show_status_func;
    }

    public void setStop_func(StopFunc<T> stop_func) {
        this.stop_func = stop_func;
    }

    public void setUpdate_func(UpdateFunc<T> update_func) {
        this.update_func = update_func;
    }



    private SOM<T> makeSOM (List data) {
        assert data != null : "Cannot create a SOM on empty data";

        Field<T> field = new Field<T>(field_len, field_width, data, params.random_gen, init_func);
        params.field = field;

        params.iterations = 0;

        return new SOM<T>(data, params);
    }

    public SOM<Vector> makeVectorialSOM (List<T> data) {

        VectorProjectionFunc projectf = new VectorProjectionFunc();

        projectf.setDistanceFunc(new Vector_DistanceAlgorithm());
        projectf.setFindBestMatchFunc(new NaiveFindBestMatchFunc<Vector>());
        projectf.setUpdateFunc(new VectorUpdateFunc());

        params.project_func = (ProjectionFunc<T>) projectf;

        params.stop_func = (StopFunc<T>) new VectorStopFunc();

        init_func = (InitFunc<T>) new VectorInitFunc();

        return (SOM<Vector>) makeSOM(data);
    }
}
