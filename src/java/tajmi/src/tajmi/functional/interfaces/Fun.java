
package tajmi.functional.interfaces;

/**
 *
 * @author badi
 */
public interface Fun extends Result {

    public Fun copy();

    public Fun curry (Object arg);

}
