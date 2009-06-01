
package tajmi.instances.som;

import org.junit.Test;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class Gaussian2DNeighborhoodFuncTest {

    @Test
    public void GaussianNeighborhoodFuncTest() {
        Gaussian2DNeighborhoodFunc f = new Gaussian2DNeighborhoodFunc();

        Position ori = new Position(0, 0),
                target = new Position(1, 1);
        f.params(ori, target);
        f.setSIGMA(1);
        System.out.println("(x0,y0) = " + ori + " <-> " + target + " => " + f.call());

        System.out.println("Gaussian2DNeighborhoodFuncTest");
    }

}