/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import scala.Tuple2;
import tajmi.Util;

/**
 *
 * @author badi
 */
public class tajmi_Util {

    public static void main(String[] args) {
        testFiles();
    }

    public static void testFiles() {
        File dir = new File("moleculefiles");
        File[] files = dir.listFiles(new FileFilter() {

            public boolean accept(File pathname) {
                return pathname.getName().matches("\\w*\\.mol2");
            }
        });
        System.out.print("Reading from: ");
        for(File f : files)
            System.out.print(f.getName() + " ");

        List<IMolecule> ms = new LinkedList<IMolecule>();
        for(File f : files){
            try {
                ms.add(Util.readMoleculeFile(f.getAbsolutePath()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CDKException ex) {
                Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Read in " + files.length + " =>? " + ms.size());
    }

    public static void readMoleculeFile() {
        try {
            IMolecule m = Util.readMoleculeFile("moleculefiles/B02.mol2");
            System.out.println(m);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CDKException ex) {
            Logger.getLogger(tajmi_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void identical() {
        List l1 = new LinkedList(),
                l2 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            l1.add(i);
            l2.add(i);
        }
        System.out.println("Identical: True =>? " + Util.identical(l1, l2));

        l2.clear();
        for (int i = 10; i < 20; i++) {
            l2.add(i);
        }
        System.out.println("Identical: False =>? " + Util.identical(l1, l2));

        l1.clear();
        l2.clear();

        for (int i = 0; i < 10; i++) {
            List l = new LinkedList();
            for (int j = 0; j < 10; j++) {
                l.add(j);
            }
            l1.add(l);
            l2.add(l);
        }
        System.out.println("Identical: True =>? " + Util.identical(l1, l2));

        l2.clear();
        for (int i = 0; i < 10; i++) {
            List l = new LinkedList();
            for (int j = 0; j < 10; j += 2) {
                l.add(j);
            }
            l2.add(l);
        }
        System.out.println("Identical: False =>? " + Util.identical(l1, l2));

    }

    public static void zip() {
        List l1 = new LinkedList();
        for (int i = 0; i < 20; i++) {
            l1.add(i);
        }

        String hello = "hello world";
        List l2 = new LinkedList();
        for (int i = 0; i < hello.length(); i++) {
            l2.add(hello.charAt(i));
        }

        List<Tuple2> l3 = Util.zip(l1, l2);
        System.out.println(l3);
    }
}
