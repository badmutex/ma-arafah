/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tajmi.data.clusterable.instances.num;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;

/**
 * Vector of numerical data to be clustered
 * @author badi
 */
public class Vector extends ArrayList implements List, Iterable {

    public Vector(){ super(); }
    public Vector(int size){ super(size); }
    public Vector(Collection c){ super(c); }

    public Vector sum(Vector other){
        Vector result = new Vector(Math.min(this.size(), other.size()));
        for(Tuple2<Double,Double> tup : Util.zip(this, other)){
            result.add(tup._1() + tup._2());
        }
        return result;
    }
}
