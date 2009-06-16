package tajmi.som;

import tajmi.abstracts.som.ShowStatusFunc;

/**
 *
 * @author badi
 */
public class StatusUpdater {

    private static StatusUpdater INSTANCE = null;

    public static synchronized StatusUpdater getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatusUpdater();
        }
        return INSTANCE;
    }

    private StatusUpdater() {
        level = Verbosity.Verbose;
    }

    public enum Verbosity {

        Verbose,
        VeryVerbose,
        Everything
    }
    Verbosity level;

    public void set_verbosity_level(Verbosity level) {
        this.level = level;
    }

    public void update_status(ShowStatusFunc fun) {
        if (level.equals(Verbosity.Verbose)) {
            fun.update_status_verbosly();
        }

        if (level.equals(Verbosity.VeryVerbose)) {
            fun.update_status_verbosly();
            fun.update_status_very_verbosly();
        }

        if (level.equals(Verbosity.Everything)) {
            fun.update_status_verbosly();
            fun.update_status_very_verbosly();
            fun.update_status_everything();
        }
    }
}
