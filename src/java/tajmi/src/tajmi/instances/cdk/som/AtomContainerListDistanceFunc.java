package tajmi.instances.cdk.som;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.DistanceFunc;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.functional.instances.seq.Seq;
import tajmi.functional.interfaces.Fun;
import tajmi.instances.cdk.AtomContainerMemoizingDistanceFunc;
import tajmi.som.StatusUpdater;

/**
 * Given an IAtomContainer <code>M</code> and a list of IAtomContainers <code>L</code>,
 * return the sum of the distances between <code>M</code> and each <code>m</code> in <code>L</code>.
 * @author badi
 */
public class AtomContainerListDistanceFunc extends DistanceFunc<IAtomContainer, List<IAtomContainer>> {

    @Override
    public Double call() {
        IAtomContainer molecule = getFirst();
        List<IAtomContainer> others = getSecond();
        StatusUpdater.getInstance().update_status(new StatusUpdate().set_target(molecule).set_rest(others));

        DistanceFunc<IAtomContainer, IAtomContainer> distancef = new AtomContainerMemoizingDistanceFunc();
        DistanceFunc f = (DistanceFunc) distancef.curry(molecule);

        return calculate_overall_distance(f, others);
    }

    double calculate_overall_distance(DistanceFunc df, List<IAtomContainer> others) {
        double distance = 0.0;
        for (IAtomContainer other : others) {
            distance += (Double) df.curry(other).call();
        }
        return distance;
    }

    class StatusUpdate extends ShowStatusFunc {

        IAtomContainer target;
        Iterable<IAtomContainer> rest;

        public StatusUpdate set_target(IAtomContainer t) {
            target = t;
            return this;
        }

        public StatusUpdate set_rest(Iterable<IAtomContainer> r) {
            rest = r;
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
            class GetNames implements Fun {

                IAtomContainer molecule;

                public Fun copy() {
                    return new GetNames().curry(molecule);
                }

                public Fun curry(Object arg) {
                    if (molecule == null) {
                        molecule = (IAtomContainer) arg;
                    }

                    return this;
                }

                public Object call() {
                    return molecule.getID();
                }
            }

            List<String> names = Seq.parallel_map(new GetNames(), rest);

            return getClass().getName() +
                    " finding distances between " + target.getID() + " and " + names;

        }
    }
}
