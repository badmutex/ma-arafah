/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tajmi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class Util {

    public static List<Tuple2> zip(Iterable<?> a, Iterable<?> b){

        List<Tuple2> result = new LinkedList<Tuple2>();

        Iterator
                itr_a = a.iterator(),
                itr_b = b.iterator();
        while(itr_a.hasNext() && itr_b.hasNext()){
            result.add(new Tuple2(itr_a.next(), itr_b.next()));
        }

        return result;
    }

    public static boolean identical(Iterable<?> a, Iterable<?> b){
        
        Iterator<Tuple2> itr = zip(a,b).iterator();
        while(itr.hasNext()){
            Tuple2 cmp = itr.next();
            if(cmp._1() instanceof Iterable && cmp._2() instanceof Iterable){
                return identical( (Iterable) cmp._1(), (Iterable) cmp._2());
            }
            else{
                if(! cmp._1().equals(cmp._2()))
                    return false;
            }
        }
        return true;
    }






}
