/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import tajmi.Util;
import tajmi.data.KMeans;
import tajmi.data.clusterable.CenterOfMassAlgorithm;
import tajmi.data.clusterable.DistanceAlgorithm;
import tajmi.data.clusterable.cdk.AtomContainer_CenterOfMassAlgorithm;
import tajmi.data.clusterable.cdk.AtomContainer_DistanceAlgorithm;
import tajmi.data.clusterable.num.Vector;

/**
 *
 * @author badi
 */
public class tajmi_data_KMeans {

    public static void main(String[] args) throws FileNotFoundException, IOException, CDKException {
        graphs();
    }

    public static void graphs() throws FileNotFoundException, IOException, CDKException {
        File dir = new File("moleculefiles");
        File[] files = dir.listFiles(new FileFilter() {

            int chosen = 0;
            public boolean accept(File pathname) {
                if(chosen < 5){
                    chosen++;
                    return pathname.getName().matches("\\w*\\.mol2");
                } else return false;
            }
        });
        System.out.print("Reading from: ");
        for (File f : files) {
            System.out.print(f.getName() + " ");
        }

        List<IMolecule> ms = new LinkedList<IMolecule>();
        for (File f : files) {
            ms.add(Util.readMoleculeFile(f.getAbsolutePath()));
        }
        System.out.println("Read in " + files.length + " =>? " + ms.size());

        DistanceAlgorithm da = new AtomContainer_DistanceAlgorithm();
        CenterOfMassAlgorithm coma = new AtomContainer_CenterOfMassAlgorithm();

        KMeans<IMolecule> km = new KMeans<IMolecule>(ms, 5, da, coma);
        
        System.out.println(km.call().get(0));
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
