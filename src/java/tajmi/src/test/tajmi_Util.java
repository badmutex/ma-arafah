/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.LinkedList;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;

/**
 *
 * @author badi
 */
public class tajmi_Util {

    public static void main(String[] args){
        identical();
    }

    public static void identical(){
        List l1 = new LinkedList(),
                l2 = new LinkedList();
        for(int i = 0; i < 10; i++){
            l1.add(i);
            l2.add(i);
        }
        System.out.println("Identical: True =>? " + Util.identical(l1, l2));

        l2.clear();
        for(int i = 10; i < 20; i++){
            l2.add(i);
        }
        System.out.println("Identical: False =>? " + Util.identical(l1, l2));

        l1.clear();
        l2.clear();

        for(int i = 0; i < 10; i++){
            List l = new LinkedList();
            for(int j = 0; j < 10; j++)
                l.add(j);
            l1.add(l);
            l2.add(l);
        }
        System.out.println("Identical: True =>? " + Util.identical(l1, l2));

        l2.clear();
        for(int i = 0; i < 10; i++){
            List l = new LinkedList();
            for(int j = 0; j < 10; j+= 2)
                l.add(j);
            l2.add(l);
        }
        System.out.println("Identical: False =>? " + Util.identical(l1, l2));

    }

    public static void zip(){
        List l1 = new LinkedList();
        for(int i = 0; i < 20; i++){
            l1.add(i);
        }

        String hello = "hello world";
        List l2 = new LinkedList();
        for(int i = 0; i < hello.length(); i++){
            l2.add(hello.charAt(i));
        }

        List<Tuple2> l3 = Util.zip(l1, l2);
        System.out.println(l3);
    }

}
