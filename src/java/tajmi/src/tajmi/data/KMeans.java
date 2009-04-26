package tajmi.data;

import tajmi.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Implements the KMeans algorithm for structured data. This structured data
 * should implement the Distanceable interface and override the Object.equals() method
 * @author badi
 */
public class KMeans implements Callable<List<List<Distanceable>>> {

    List<Distanceable> vectors;
    int k;
    List<List<Distanceable>> clusters;   // C_i for all i in {1,...,k}
    List<Distanceable> centers_of_mass;         // c_i for all i in {1,...,k}

    public KMeans(List<Distanceable> vectors, int k) {
        this.vectors = vectors;
        this.k = k;
    }

    /**
     * Arbitrarily seeds the kmeans algorithms with chosen centers of mass
     * @param vectors the vectors to choose from
     * @return the centers of mass
     */
    private List<Distanceable> init_centers_of_mass_from(List<Distanceable> vectors) {

        List<Distanceable> centers = new LinkedList<Distanceable>();
        List<Distanceable> copied_vectors = new ArrayList<Distanceable>(vectors.size());
        Collections.copy(copied_vectors, vectors);
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
    public List<List<Distanceable>> call() {
        centers_of_mass = init_centers_of_mass_from(vectors);

        do{
            step1();
            step2();
        } while( !done() );

        return clusters;
    }

    /**
     * Checks the state to see if the clusters and centers of mass have changed.
     * If there is no chance, the we're done.
     * @return true if `C` and `c` have stabilized
     */
    private boolean done() {

        // save current state
        List<List<Distanceable>> saved_cluster_centers = clusters;
        List<Distanceable> saved_centers_of_mass = centers_of_mass;

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
     * Finds the data points closest to the current center of mass
     * @param c_i the center of mass
     * @param i the current position
     * @return a list of points closest to `c_i` than `c_j` forall i != j
     */
    private List<Distanceable> closest_points_to(Distanceable c_i, int i) {

        /* find the vectors closest to c_i over c_j where i != j
         * :: For each vector, if there exists a distance  to another clusters center
         * that is less than the distance to c_i, disregard that vector, else add it
         */
        List<Distanceable> selected_points = new LinkedList<Distanceable>();
        for (Distanceable point : vectors){
            double mydist = c_i.distance(point);
            boolean this_point_ok = true;

            for (Distanceable c : centers_of_mass){
                if(c.equals(c_i))
                    continue;
                if(c.distance(point) < mydist) {
                    this_point_ok = false;
                    break;
                }
            }

            if(this_point_ok)
                selected_points.add(point);
        }


        return selected_points;
    }

    /**
     * for each i in {1..k}, set the clusters center C_i to be the set of points in
     * X that are closer to c_i than they are to c_j for all i != j
     */
    private void step1() {
        List<List<Distanceable>> C = new LinkedList<List<Distanceable>>();
        List<Distanceable> C_i;
        Distanceable c;
        for (int i = 0; i < k; i++) {
            c = centers_of_mass.get(i);

            C_i = closest_points_to(c, i);

            C.add(C_i);
        }

        clusters = C;
    }

    //TODO centers_of_mass implementation
    private Distanceable centers_of_mass(List<Distanceable> get) {

        //finding the center of mass of structured data could be problematic :(
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * for each i in {1..k}, set c_i to be the center of mass of all points in C_i:
     * c_i = 1/|C_i| SUM_{x_j in C_i} x_j
     */
    private void step2() {
        Distanceable c;
        for (int i = 0; i < k; i++) {
            c = centers_of_mass(clusters.get(i));

            centers_of_mass.set(i, c);
        }
    }
}
