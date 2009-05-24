
package tajmi.data.som;

/**
 * Singleton to hold the extraneous configuration details for the SOM
 * @author badi
 */
public class SOMConfig {

    private static SOMConfig INSTANCE;

    private SOMConfig () {
        init_random_gen_seed = 101010;
    }

    public static synchronized SOMConfig getInstance () {
        if (INSTANCE == null)
            INSTANCE = new SOMConfig();
        return INSTANCE;
    }


    /** used to initialize the Field */
    private long init_random_gen_seed;

    public long init_random_gen_seed () {
        return init_random_gen_seed;
    }

    /** used as change the restraint this much each time */
    private double restraint_modifier;

    public double restraint_modifier () {
        return restraint_modifier;
    }
}
