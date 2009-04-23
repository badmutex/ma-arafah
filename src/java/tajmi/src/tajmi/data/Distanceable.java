package tajmi.data;

/**
 *
 * @author badi
 */
public interface Distanceable {

    /**
     * Compute the distance between this object and the other.
     * @param other the other object
     * @return a double representing the distance between the two objects
     */
    public double distance(Distanceable other);
}
