package tajmi.functional.instances.cdk;

import java.util.Hashtable;
import java.util.Map;
import org.openscience.cdk.io.formats.IChemFormat;
import org.openscience.cdk.io.formats.SMILESFormat;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class ChooseFileFormat implements Fun {

    String description;

    private Map<String, IChemFormat> valid_formats;

    public ChooseFileFormat() {
        super();
        valid_formats = new Hashtable<String, IChemFormat>();
        valid_formats.put("smiles", (IChemFormat) SMILESFormat.getInstance());
    }
    public Fun copy() {
        return new ChooseFileFormat().curry(description);
    }

    public Fun curry(Object arg) {
        if (description == null) {
            description = (String) arg;
        }

        return this;
    }

    public IChemFormat call() throws InvalidFileFormat {
        IChemFormat format = null;
        boolean found_it = false;
        
        for (String desc : valid_formats.keySet()) {
            if (description.equalsIgnoreCase(desc))
            format = valid_formats.get(desc);
            found_it = true;
        }

        if (!found_it)
            throw new InvalidFileFormat(description);

        return format;
    }

    public class InvalidFileFormat extends RuntimeException {

        String bad_format_name;

        public InvalidFileFormat(String format_name) {
            this.bad_format_name = format_name;
        }

        @Override
        public String getLocalizedMessage() {
            return "Unknown attempted format [" + bad_format_name + "]. Try " + valid_formats.keySet();
        }
    }
}
