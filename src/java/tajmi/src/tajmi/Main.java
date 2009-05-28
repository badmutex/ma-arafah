package tajmi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.frontends.KMeans;
import tajmi.interfaces.CenterOfMassFunc;
import tajmi.interfaces.DistanceFunc;
import tajmi.instances.cdk.AtomContainerCenterOfMassFunc;
import tajmi.instances.cdk.AtomContainerDistanceFunc;

/**
 *
 * @author badi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, CDKException {

        if(args.length < 2){
            System.out.println("Usage: java -jar path/to/tajmi.jar <n data points> <k>");
            System.exit(0);
        }
        final int POINTS = Integer.parseInt(args[0]);
        final int K = Integer.parseInt(args[1]);
        final String KMEANS_RESULTS_FILE = "kmeans-results.data";

        File dir = new File("moleculefiles");
        File[] files = dir.listFiles(new FileFilter() {

            int chosen = 0;
            public boolean accept(File pathname) {
                if(chosen < POINTS){
                    chosen++;
                    return pathname.getName().matches("\\w*\\.mol2");
                } else return false;
            }
        });
        System.out.print("Reading from: ");
        for (File f : files) {
            System.out.print(f.getName() + " ");
        }
        System.out.println();

        List<IMolecule> ms = new LinkedList<IMolecule>();
        for (File f : files) {
            IMolecule m = Util.readMoleculeFile(f.getAbsolutePath());
            AtomContainerManipulator.removeHydrogens(m);
            String n = f.getName().replace(".mol2", "");
            m.setID(n);
            ms.add(m);
        }
        Collections.shuffle(ms, new Random(4224));
        System.out.println("Read in " + files.length + " =>? " + ms.size());

        DistanceFunc da = new AtomContainerDistanceFunc();
        CenterOfMassFunc coma = new AtomContainerCenterOfMassFunc();

        KMeans<IMolecule> km = new KMeans<IMolecule>(ms, K, da, coma);

        List<List<IMolecule>> result = km.call();

        int count = 0;
        String outdir = "c_i";
        File f = new File(outdir);
        if(!f.exists())
            f.mkdir();
        for(List<IMolecule> C : result){
            IAtomContainer c = (IAtomContainer) coma.params(C).call();
            System.out.println("Writing " + outdir + File.separator + "c_" + (++count) + ".smiles");
            Util.writeMoleculeFile(outdir + File.separator + "c_" + count + ".smiles", c);
        }


        System.out.println("+++++++++ Results +++++++++");
        int clust_id = 0;
        for (List<IMolecule> cluster : result) {
            String names = "";
            for (IMolecule molec : cluster) {
                names += " " + molec.getID();
            }
            System.out.println("[" + clust_id + "]\t" + names);
            clust_id++;
        }

        System.out.println("+++++++++ Serializing results [" + KMEANS_RESULTS_FILE + "] +++++++++");
        new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(KMEANS_RESULTS_FILE)))
                .writeObject(result);


        System.out.println();
        
    }
}
