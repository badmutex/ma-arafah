
package tajmi.data.som;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class SOM<T> implements Callable<Field<T>> {

    List<T> data;
    SOMParams<T> params;

    public SOM (List<T> data, SOMParams<T> params) {
        this.data = data;
        this.params = params;
    }


    private Tuple2<List<T>,Random> shuffle (List<T> data, Random random_gen){
        synchronized(data){
            if(random_gen == null)
                Collections.shuffle(data);
            else
                Collections.shuffle(data, random_gen);
        }
        return new Tuple2<List<T>, Random> (data, random_gen);
    }

    public Field<T> call () {

        Tuple2<List<T>, Random> res = shuffle(data, params.random_gen);
        data = res._1();
        params.random_gen = res._2();

        Iterator<T> itr = data.iterator();
        while ( !params.stop_func.params(params).call() ){

            // we need to cycle over inputs
            if( !itr.hasNext() )
                itr = data.iterator();

            params = params.project_func.params(itr.next(), params).call();

        }
        return params.field;
    }



}
