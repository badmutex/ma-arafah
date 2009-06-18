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

    private void show_message (String msg) {
        synchronized (System.out) {
            System.out.print(msg);
        }
    }

    public void update_status(ShowStatusFunc fun) {
        if (level.equals(Verbosity.Verbose)) {
            String msg = fun.verbose();
            show_message (msg);
        }

        if (level.equals(Verbosity.VeryVerbose)) {
            String msg = fun.verbose() +
                    fun.very_verbose();
            show_message (msg);
        }

        if (level.equals(Verbosity.Everything)) {
            String msg = fun.verbose() +
                    fun.very_verbose() +
                    fun.everything();
            show_message (msg);
        }
    }
}
