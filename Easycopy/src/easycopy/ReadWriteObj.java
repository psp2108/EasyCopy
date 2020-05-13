/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easycopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;

/**
 *
 * @author Pratik
 */
public class ReadWriteObj {

    public static String permPath = "F:\\Programming\\jAVA\\NetBins Programs\\EasyCopy\\V2.0\\Easycopy\\TestData\\";

    public static void serializeDataOut(GroupsKeylinkClass ish, String path) throws IOException {
        String fileName = path;
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static GroupsKeylinkClass serializeDataIn(String path) throws Exception {
        File file = new File(path);
        
        if (file.exists()) {
            String fileName = path;
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            GroupsKeylinkClass iHandler = (GroupsKeylinkClass) ois.readObject();
            ois.close();
            return iHandler;
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
        GroupsKeylinkClassBL g = new GroupsKeylinkClassBL();
        g.addGroup("Grp1");
        g.addGroup("Grp2");

        g.updateGroup("Grp1", new JButton("1"), new JButton("2"), new JButton("3"), new JButton("4"));
        g.updateGroup("Grp2", new JButton("a"), new JButton("b"), new JButton("c"));
         */
        GroupsKeylinkClassBL g = GroupsKeylinkClassBL.getStoredObject();

        JButton[] temp;

        temp = g.getGroup("Grp1");
        System.out.println("Grp1 ---");
        printButtonLabels(temp);
        temp = g.getGroup("Grp2");
        System.out.println("Grp2 ---");
        printButtonLabels(temp);

        serializeDataOut(g.dataObj, permPath + "test");
    }

    public static void printButtonLabels(JButton[] buttons) {
        for (JButton b : buttons) {
            System.out.println(b.getText());
        }
    }

}
