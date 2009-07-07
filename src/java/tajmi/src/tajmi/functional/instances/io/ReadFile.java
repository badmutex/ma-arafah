package tajmi.functional.instances.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.functional.interfaces.Fun;

/**
 *<code> ReadFile :: String -> IO [String]</code>
 * @author badi
 */
public class ReadFile implements Fun {

    String path;

    public Fun copy() {
        return new ReadFile().curry(path);
    }

    public Fun curry(Object arg) {
        if (path == null) {
            path = (String) arg;
        }

        return this;
    }

    public List<String> call() {
        List<String> lines = null;
        try {
            BufferedReader r = new BufferedReader(new FileReader(path));
            lines = new LinkedList<String>();
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                lines.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
}