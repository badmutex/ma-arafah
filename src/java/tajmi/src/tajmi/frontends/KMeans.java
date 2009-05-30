package tajmi.frontends;

import tajmi.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import scala.Tuple3;
import tajmi.abstracts.CenterOfMassFunc;
import tajmi.abstracts.DistanceFunc;

/**
 * Implements the KMeans algorithm for structured data. This structured data
 * should implement the Clusterable interface and override the Object.equals() method
 * @author badi
 */
public class KMeans<T> implements Callable<List<List<T>>> {

    List<T> vectors;
    int k;
    List<List<T>> clusters;   // C_i for all i in {1,...,k}
    List<T> centers_of_mass;  // c_i for all i in {1,...,k}

    DistanceFunc<T> distance_func;
    CenterOfMassFunc<T> center_of_mass_func;


    public KMeans(List<T> vectors, int k, DistanceFunc<T> dist, CenterOfMassFunc<T> cent) {
        this.vectors = vectors;
        this.k = k;

        distance_func = dist;
        center_of_mass_func = cent;

    }

    /**
     * Arbitrarily seeds the kmeans algorithms with chosen centers of mass
     * @param vectors the vectors to choose from
     * @return the centers of mass
     */
    private List<T> init_centers_of_mass_from(List<T> vectors) {

        List<T> centers = new LinkedList<T>();
        List<T> copied_vectors = new ArrayList<T>(vectors);
        Collections.shuffle(copied_vectors, new Random(42));

        for (int i = 0; i < k; i++) {
            centers.add(copied_vectors.get(i));
        }

        return centers;
    }

    /**
     * Runs the KMeans algorithm
     * @return the original input data clustered into `k` clusters
     */
    public List<List<T>> call() {
        System.out.println("<========= Starting KMeans for [" + k + "] clusters =========>");

        centers_of_mass = init_centers_of_mass_from(vectors);

        int count = 0;
        do{
            step1();
            step2();

            System.out.println(" * Iteration[" + (++count) + "]");
        } while( !done() );

        System.out.println("<========= Finished KMeans for [" + k + "] clusters =========>");

        return clusters;
    }

    /**
     * Checks the state to see if the clusters and centers of mass have changed.
     * If there is no chance, the we're done.
     * @return true if `C` and `c` have stabilized
     */
    private boolean done() {

        // save current state
        List<List<T>> saved_cluster_centers = clusters;
        List<T> saved_centers_of_mass = centers_of_mass;

        // next steps
        step1();
        step2();

        // compare new current centers of mass with previous state
        boolean same = true;
        if(saved_centers_of_mass.size() != centers_of_mass.size())
            same = false;
        if(! Util.identical(saved_centers_of_mass, centers_of_mass))
            same = false;

        // compare current clusters with previous state
        if(saved_cluster_centers.size() != clusters.size())
            same = false;
        if(! Util.identical(saved_cluster_centers, clusters))
            same = false;

        // reset to previous state if needed
        if(!same){
            clusters = saved_cluster_centers;
            centers_of_mass = saved_centers_of_mass;
            return false;
        } else
            return true;

    }

    /**
     * Finds the data points closest to the current params of mass
     * @param c_i the params of mass
     * @param i the current position
     * @param assigned a <code>boolean[s]</code> where <code>s</code> is the is the input vector size
     * @param distances a <code>double[k][s]</code>
     * @return a list of points closest to `c_i` than `c_j` forall i != j
     */
    private Tuple3<List<T>, boolean[], double[][]> closest_points_to(T c_i, int i, boolean[] assigned, double[][] distances) {

        /* find the vectors closest to c_i over c_j where i != j
         * :: For each vector, if there exists a params  to another clusters params
         * that is less than the params to c_i, disregard that vector, else add it
         */
        List<T> selected_points = new ArrayList<T>();
        int id = 0; // incremented at end of loop
        for (T point : vectors){
            System.out.print(id);

            // can we skip this point?
            if ( assigned[id] ){
                System.out.print("$");
                id++;
                continue;
            }

            // can't skip this point
            double mydist = distance_func.params(c_i, point).call();
            boolean this_point_ok = true;

            // is this point closer to another params?
            for (int j = 0; j < centers_of_mass.size(); j++) {
                if( j == i )    // j, i subscript the centers of mass
                    continue;

                T c = centers_of_mass.get(j);
                double other_dist = distances[j][id] < 0.0
                    ? distance_func.params(c, point).call()
                    : distances[j][id];

                distances[i][id] = distances[j][id] < 0.0
                        ? other_dist
                        : distances[i][id];

                if(other_dist < mydist) {
                    System.out.print("-");

                    this_point_ok = false;
                    break;
                }
            }

            if(this_point_ok){
                System.out.print("!");
                selected_points.add(point);
                assigned[id] = true;
            }


            id++;
        }

        return new Tuple3<List<T>, boolean[], double[][]> (selected_points, assigned, distances);
    }


    private List<T> add_remaining(int c_id, boolean[] assigned, List<T> vectors){
        List<T> chosen = new ArrayList(vectors.size());
        int id = 0;
        for(T v : vectors){
            System.out.print(id);
            if( !assigned[id] ){
                chosen.add(v);
                assigned[id] = true;
                System.out.print("!");
            } else {
                System.out.print("$");
            }
            id++;
        }
        return chosen;

    }

    /**
     * for each i in {1..k}, set the clusters params C_i to be the set of points in
     * X that are closer to c_i than they are to c_j for all i != j
     */
    private void step1() {
        System.out.println(" ** Step 1");

        /**
         * This will be used as a lookup table during step1. As data points are assigned
         * into a cluster this is noted in <code>assigned</code>. Thus, before a potentially
         * expensive params is computed between a point and a params, this checked.
         */
        boolean[] assigned = new boolean[vectors.size()];
        for (int i = 0; i < assigned.length; i++)
            assigned[i] = false;

        /**
         * Holds the distances between each points to the centers as calculated during
         * each step1. Serves as a lookup table so calculations aren't repeated.
         */
        double[][] distances = new double[k][vectors.size()];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < vectors.size(); j++)
                distances[i][j] = Double.NEGATIVE_INFINITY;

        List<List<T>> C = new LinkedList<List<T>>();
        List<T> C_i;
        T c;
        for (int i = 0; i < k; i++) {

            System.out.print(" **** closest points to c_" + i + "\t");

            if( i == k - 1 ){
                C_i = add_remaining(i, assigned, vectors);
            } else {

                c = centers_of_mass.get(i);

                Tuple3<List<T>, boolean[], double[][]> result = closest_points_to(c, i, assigned, distances);
                C_i = result._1();
                assigned = result._2();
                distances = result._3();

            }

            System.out.print(" [" + C_i.size() + "]\t");

            C.add(C_i);

            System.out.println(" *** Found " + i + "/" + (k-1) + " clusters");
        }

        clusters = C;

    }


    /**
     * for each i in {1..k}, set c_i to be the params of mass of all points in C_i:
     * c_i = 1/|C_i| SUM_{x_j in C_i} x_j
     */
    private void step2() {
        System.out.print(" ** Step 2\n");

        T c;
        for (int i = 0; i < k; i++) {
            if (clusters.get(i).size() < 1) {
                System.out.println(" *** c_" + i + " empty, skipping centering");
                continue;
            }
            c = (T) center_of_mass_func.params(clusters.get(i)).call();

            centers_of_mass.set(i, c);

            System.out.println(" *** Found " + i + "/" + (k-1) + " centers of mass");
        }
    }
}
