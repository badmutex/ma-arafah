
package tajmi.som;

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
public class SOM implements Callable<Field> {

    List data;
    SOMParams somparams;

    public SOM (List data, SOMParams params) {
        this.data = data;
        this.somparams = params;
    }


    private Tuple2<List,Random> shuffle (List data, Random random_gen){
        synchronized(data){
            if(random_gen == null)
                Collections.shuffle(data);
            else
                Collections.shuffle(data, random_gen);
        }
        return new Tuple2<List, Random> (data, random_gen);
    }

    public Field call () {

        Tuple2<List, Random> res = shuffle(data, somparams.random_gen);
        data = res._1();
        somparams.random_gen = res._2();

        Iterator itr = data.iterator();
        while ( !somparams.stop_func.params(somparams).call() ){

            // we need to cycle over inputs
            if( !itr.hasNext() ) {
                itr = data.iterator();
                somparams.iterations++;
                somparams.projections = 0;
            }

            // the magic happens here
            somparams = somparams.project_func.params(itr.next(), somparams).call();

            // update the user and some state
            somparams.show_status_func.params(somparams).call();
            somparams.projections++;
        }
        
        return somparams.field;
    }



}
