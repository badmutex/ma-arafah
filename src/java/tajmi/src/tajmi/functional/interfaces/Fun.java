
package tajmi.functional.interfaces;

/**
 *
 * @author badi
 */
public interface Fun<P,R> extends Result<R> {

    public Fun<P,R> copy();

    public Fun<R,?> curry (Object arg);

}
