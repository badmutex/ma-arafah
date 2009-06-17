
package tajmi.instances.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Universe;
import tajmi.Util;
import tajmi.abstracts.DistanceFunc;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.som.StatusUpdater;

/**
 *
 * @author badi
 */
public class AtomContainerMemoizingDistanceFunc extends DistanceFunc<IAtomContainer,IAtomContainer> {

    public Double call () {
        IAtomContainer g_1 = getFirst(),
                g_2 = getSecond();

        Universe state = Universe.getInstance();
        String n1 = g_1.getID(),
                n2 = g_2.getID();

        StatusUpdater statusupdater = StatusUpdater.getInstance();
        statusupdater.update_status(new ShowMatchingNames().set_names(n1, n2));


        double distance = Double.NEGATIVE_INFINITY;

        if (state.distance_exists(n1, n2)) {
            statusupdater.update_status(new ShowMemoizingStatus().set_message("skipping"));
            distance = state.get_distance(n1, n2);
        } else {
            statusupdater.update_status(new ShowMemoizingStatus().set_message("running mcss algorithm"));

            IAtomContainer mcss = Util.mcss(g_1, g_2);

            if (mcss == null) {
                distance = Double.NEGATIVE_INFINITY;
            } else {
                IAtomContainer
                        g1 = AtomContainerManipulator.removeHydrogens(g_1),
                        g2 = AtomContainerManipulator.removeHydrogens(g_2);
                distance = 1.0 - (mcss.getAtomCount() + 0.0) / Math.max(g1.getAtomCount(), g2.getAtomCount());

                state.add_distance(n1, n2, distance);
            }
        }

        return distance;
    }

    private class ShowMatchingNames extends ShowStatusFunc {

        String n1, n2;

        public ShowMatchingNames set_names (String n1, String n2) {
            this.n1 = n1;
            this.n2 = n2;
            return this;
        }

        @Override
        public String verbose() {
            return "";
        }

        @Override
        public String very_verbose() {
            return "\t" + n1 + " <-> " + n2;
        }

        @Override
        public String everything() {
            return "";
        }

    }

    private class ShowMemoizingStatus extends ShowStatusFunc {

        String msg;

        public ShowMemoizingStatus set_message (String msg) {
            this.msg = msg;
            return this;
        }

        @Override
        public String verbose() {
            return "";
        }

        @Override
        public String very_verbose() {
            return "";
        }

        @Override
        public String everything() {
            return "\t\t" + msg;
        }

    }
}
