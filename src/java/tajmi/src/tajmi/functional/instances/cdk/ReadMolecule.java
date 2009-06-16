package tajmi.functional.instances.cdk;

import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.functional.interfaces.Fun;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.ReaderFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * <code> ReadMolecule :: String filename -> IO IMolecule </code>
 * @author badi
 */
public class ReadMolecule implements Fun {

    String filename;

    public Fun copy() {
        return new ReadMolecule().curry(filename);
    }

    public Fun curry(Object arg) {

        if (filename == null)
            filename = (String) arg;

        return this;
    }

    public IMolecule call()  {
        IMolecule m = null;
        try {
            Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            ISimpleChemObjectReader reader = new ReaderFactory().createReader(r);
            m = (IMolecule) reader.read(new Molecule());
            m.setID(filename);
        } catch (CDKException ex) {
            Logger.getLogger(ReadMolecule.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadMolecule.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return m;
    }
}
