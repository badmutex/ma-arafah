
package tajmi.instances.cdk.som;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.Molecule;
import scala.Tuple2;
import tajmi.som.Field;

import java.util.*;
import tajmi.abstracts.som.InitFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class AtomContainerUpdateFuncTest {

    @Test
    public void AtomContainerUpdateFunc() {
        IAtomContainer atom = new Molecule();

        List<IAtomContainer> input_data = new LinkedList();
        for (int i = 0; i < 9; i++)
            input_data.add(atom);

        Random rand = new Random(42);

        InitFunc initf = new InitFunc() {

            @Override
            public Tuple2 call() {
                return new Tuple2(getSeed(), getRandgen());
            }
        };

        Field field = new Field(10, 1, input_data, rand, initf);

        new AtomContainerUpdateFunc().params(field, atom, new Position(0, 0), 1.0).call();

        System.out.println("AtomContainerUpdateFuncTest");
    }

}