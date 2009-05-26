/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Util;
import tajmi.data.KMeans;
import tajmi.data.clusterable.interfaces.CenterOfMassFunc;
import tajmi.data.clusterable.interfaces.DistanceFunc;
import tajmi.data.clusterable.instances.cdk.AtomContainer_CenterOfMassAlgorithm;
import tajmi.data.clusterable.instances.cdk.AtomContainer_DistanceAlgorithm;
import tajmi.data.clusterable.instances.num.Vector;

/**
 *
 * @author badi
 */
public class tajmi_data_KMeans {

    public static void main(String[] args) throws FileNotFoundException, IOException, CDKException {
        graphs();
    }

    public static void graphs() throws FileNotFoundException, IOException, CDKException {

        final int POINTS = 8;
        final int K = 2;
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

        DistanceFunc da = new AtomContainer_DistanceAlgorithm();
        CenterOfMassFunc coma = new AtomContainer_CenterOfMassAlgorithm();

        KMeans<IMolecule> km = new KMeans<IMolecule>(ms, K, da, coma);

        List<List<IMolecule>> result = km.call();

        int count = 0;
        for(List<IMolecule> C : result){
            String outdir = "c_i";
            IAtomContainer c = (IAtomContainer) coma.params(C);
            System.out.println("Writing " + outdir + File.separator + "c_" + (++count) + ".txt");
            Util.writeMoleculeFile(outdir + File.separator + "c_" + count + ".txt", c);
        }

        System.out.println("+++++++++ Serializing results +++++++++");
        new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(KMEANS_RESULTS_FILE)))
                .writeObject(result);

        
        System.out.println();
    }

    public static void o4_results() throws FileNotFoundException, IOException {
        BufferedReader r = new BufferedReader(new FileReader("o4-results.data"));

        List<Vector> vs = new LinkedList<Vector>();

        for (String line = r.readLine(); line != null; line = r.readLine()) {
            String[] bits = line.split("\\s");
            Vector v = new Vector(2);
            v.add(Double.parseDouble(bits[2]));
            v.add(Double.parseDouble(bits[3]));

            vs.add(v);
        }
        System.out.println(vs);



    }

    /**
     * need to change visibility of tajmi.data.KMeans.closest_points_to
     * default is private
     */
    public static void closest_points_to() {
    }
}
