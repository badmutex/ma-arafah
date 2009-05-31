package tajmi.instances.cdk.som;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.graph.AtomContainerAtomPermutor;
import org.openscience.cdk.graph.AtomContainerBondPermutor;
import org.openscience.cdk.graph.AtomContainerPermutor;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.abstracts.som.InitFunc;

/**
 *
 * @author badi
 */
public class AtomContainerInitFunc extends InitFunc<List<IAtomContainer>, IAtomContainer> {

    List<IAtomContainer> chosen;
    AtomContainerPermutor bondpermutor, atompermutor;

    @Override
    public InitFunc params(List<IAtomContainer> seed, Random randgen) {
        chosen = new LinkedList<IAtomContainer>();
        List tmp = new ArrayList(seed);
        Collections.shuffle(tmp, randgen);
        return super.params(tmp, randgen);
    }

    private IAtomContainer permute(IAtomContainer c) {
        AtomContainer choice = (AtomContainer) c;

        atompermutor = new AtomContainerAtomPermutor(choice);
        choice = (AtomContainer) atompermutor.next();
        bondpermutor = new AtomContainerBondPermutor(choice);
        choice = (AtomContainer) bondpermutor.next();

        return choice;
    }

    @Override
    public List<IAtomContainer> call() {

        List<IAtomContainer> seed = getSeed();
        List<IAtomContainer> result = new LinkedList<IAtomContainer>();

        IAtomContainer choice;

        if (seed.size() > 0) {
            choice = (AtomContainer) seed.remove(seed.size() - 1);
        } else {
            choice = (AtomContainer) chosen.remove(0);
        }

        choice = permute(choice);
        chosen.add(choice);
        result.add(choice);

        return result;
    }
}
