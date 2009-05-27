
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
    SOMParams<T> somparams;

    public SOM (List<T> data, SOMParams<T> params) {
        this.data = data;
        this.somparams = params;
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

        Tuple2<List<T>, Random> res = shuffle(data, somparams.random_gen);
        data = res._1();
        somparams.random_gen = res._2();

        Iterator<T> itr = data.iterator();
        while ( !somparams.stop_func.params(somparams).call() ){

            // we need to cycle over inputs
            if( !itr.hasNext() ) {
                itr = data.iterator();
                somparams.iteration++;
                System.out.println("!");
            }

            // the magic happens here
            somparams = somparams.project_func.params(itr.next(), somparams).call();

            System.out.print("+");
        }
        
        return somparams.field;
    }



}
