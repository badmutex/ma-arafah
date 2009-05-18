
package tajmi.data.som;

/**
 * Singleton to hold the extraneous configuration details for the SOM
 * @author badi
 */
public class Config {

    private static Config INSTANCE;

    private Config () {
        init_random_gen_seed = 101010;
    }

    public static synchronized Config getInstance () {
        if (INSTANCE == null)
            INSTANCE = new Config();
        return INSTANCE;
    }


    private long init_random_gen_seed;

    public long init_random_gen_seed () {
        return init_random_gen_seed;
    }
}
