
package tajmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import tajmi.frontends.SOMMaker;
import tajmi.instances.vectorial.Vector;
import tajmi.interfaces.som.StopFunc;
import tajmi.som.SOM;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class TestSOM {

    public static void main (String[] args) throws FileNotFoundException, IOException {

            List<Vector> data = readVectorData("test-data" + File.separator + "yeast.data.short");
            System.out.println(data);

            SOMMaker sommaker = new SOMMaker();
            sommaker.field_size(10, 10);
            
            SOM<Vector> result = sommaker.makeVectorialSOM(data);
            
            System.out.println("Running SOM");
            result.call();
    }

    public static List<Vector> readVectorData (String path) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<Vector> vs = new LinkedList<Vector>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()){
            Pattern p = Pattern.compile("\\s+"); // split on whitespace
            String[] data = p.split(line);
            Vector v = new Vector(data.length);
            for (String d : data)
                v.add( Double.parseDouble(d) );
            vs.add(v);
        }
        return vs;
    }
}
