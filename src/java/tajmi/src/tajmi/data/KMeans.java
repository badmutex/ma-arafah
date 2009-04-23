package tajmi.data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Implements the KMeans algorithm for structured data. This structured data
 * should implement the Distanceable interface.
 * @author badi
 */
public class KMeans implements Callable<List<List<Distanceable>>> {

    List<Distanceable> vectors;
    int k;
    List<List<Distanceable>> cluster_centers;   // C_i for all i in {1,...,k}
    List<Distanceable> centers_of_mass;         // c_i for all i in {1,...,k}

    public KMeans(List<Distanceable> vectors, int k) {
        this.vectors = vectors;
        this.k = k;
    }

    private List<Distanceable> partition(List<Distanceable> vectors) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<List<Distanceable>> call() throws Exception {
        centers_of_mass = partition(vectors);

        for (int z = 0; z < 100; z++) {
            step1();
            step2();
        }

        return cluster_centers;
    }

    private List<Distanceable> closest_points_to(Distanceable c_i, int i) {

        // build a matrix of distances between each cluster center to the vectors
        double[][] distances = new double[k][vectors.size()];

        for(int j = 0; j < k; j++){
            for(int l = 0; l < vectors.size(); l++){
                distances[j][l] = centers_of_mass.get(j).distance(vectors.get(l));
            }
        }
        

        // find the vectors closest to c_i over c_j where i != j
        List<Distanceable> selected_points = new LinkedList<Distanceable>();

        //TODO


        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * for each i in {1..k}, set the cluster center C_i to be the set of points in
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

        cluster_centers = C;
    }

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
            c = centers_of_mass(cluster_centers.get(i));

            centers_of_mass.set(i, c);
        }
    }
}
