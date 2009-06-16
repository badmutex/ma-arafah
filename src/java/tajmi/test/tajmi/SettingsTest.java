
package tajmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tajmi.Settings.InvalidConfigurationType;
import tajmi.Settings.Variables;

/**
 *
 * @author badi
 */
public class SettingsTest {

    @Test
    public void SettingsTest() throws FileNotFoundException, IOException, InvalidConfigurationType, Exception {
        Settings settings = new Settings("test-data/test.config");
        Map<Variables, ?> config = settings.get_configuration();
        for (Settings.Variables v : config.keySet())
            System.out.println(v + " = " + config.get(v));
    }

}