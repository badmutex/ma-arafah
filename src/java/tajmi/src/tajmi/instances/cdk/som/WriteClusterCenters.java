package tajmi.instances.cdk.som;

import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.abstracts.som.ViewField;
import org.openscience.cdk.interfaces.IAtomContainer;
import java.io.*;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.functional.instances.cdk.WriteMolecule;
import tajmi.som.StatusUpdater;

/**
 *
 * @author badi
 */
public class WriteClusterCenters extends ViewField<FieldModel<IAtomContainer>> {

    String output_directory;
    WriteMolecule writer;

    public WriteClusterCenters setOutput_directory(String output_directory) {
        this.output_directory = output_directory;
        return this;
    }

    public String getOutput_directory() {
        return output_directory;
    }
    String output_prefix;

    public String getOutput_prefix() {
        return output_prefix;
    }

    public WriteClusterCenters setOutput_prefix(String output_prefix) {
        this.output_prefix = output_prefix;
        return this;
    }

    public WriteMolecule getMoleculeWriter() {
        return writer;
    }

    public WriteClusterCenters setMoleculeWriter(WriteMolecule writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public Void call() {

       Iterable<IAtomContainer> centers = (Iterable<IAtomContainer>) new CollectClusterCenters().curry(getField()).call();

        // 3) write out the cluster centers
        File outdir = new File(getOutput_directory());
        if (!outdir.exists()) {
            outdir.mkdir();
        }

        String prefix = getOutput_prefix();
        WriteMolecule writer = getMoleculeWriter();
        int counter = 1;
        for (IAtomContainer center : centers) {

            String fname = getOutput_directory() + File.separator + prefix + counter;

            try {
                writer.copy().curry(fname).curry(center).call();
                StatusUpdater.getInstance().update_status(new Status().setVerbose("Wrote " + fname + "\n"));
            } catch (Exception ex) {
                Logger.getLogger(WriteClusterCenters.class.getName()).log(Level.SEVERE, "Could not write " + fname, ex);
            }
            counter++;

        }


        // ....and we're done!
        return null;
    }

    private class Status extends ShowStatusFunc {

        String verbose,
                very_verbose,
                everything;

        public Status setEverything(String everything) {
            this.everything = everything;
            return this;
        }

        public Status setVerbose(String verbose) {
            this.verbose = verbose;
            return this;
        }

        public Status setVery_verbose(String very_verbose) {
            this.very_verbose = very_verbose;
            return this;
        }

        
        @Override
        public String verbose() {
            return verbose;
        }

        @Override
        public String very_verbose() {
            return very_verbose;
        }

        @Override
        public String everything() {
            return everything;
        }
        
    }
}
