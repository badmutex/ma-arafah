
package tajmi.instances.som;

import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class GaussianNeighborhoodFunc extends NeighborhoodFunc {

    @Override
    public Double call() {
        Position origin = getOrigin(),
                target = getTarget();
        return gaussian(target.x(), target.y(), origin);
    }

    private double gaussian(double x, double y, Position origin) {
        double xpart = gaussian_part(x, origin.x());
        double ypart = gaussian_part(y, origin.y());
        return xpart * ypart;
    }

    private double gaussian_part (double val, double ori) {
        double part = Math.pow(val - ori, 2) / (2 * Math.pow(0.5, 2));
        return 1 * Math.pow(Math.E, part);
    }

}
