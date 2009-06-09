
package tajmi.instances.som;

import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class Gaussian2DNeighborhoodFunc extends NeighborhoodFunc {

    double A = 1;
    double SIGMA = 9;

    public void setA(double A) {
        this.A = A;
    }

    public void setSIGMA(double SIGMA) {
        this.SIGMA = SIGMA;
    }

    @Override
    public Double call() {
        Position origin = getOrigin(),
                target = getTarget();
        return gaussian(target.x(), target.y(), origin);
    }

    private double gaussian(double x, double y, Position origin) {
        double xpart = gaussian_part(x, origin.x());
        double ypart = gaussian_part(y, origin.y());
        return A * Math.pow(Math.E, -( xpart + ypart));
    }

    private double gaussian_part (double val, double ori) {
        return Math.pow(val - ori, 2) / Math.pow(SIGMA, 2);
    }

}
