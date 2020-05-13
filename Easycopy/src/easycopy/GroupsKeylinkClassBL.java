/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easycopy;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.JButton;

/**
 *
 *
 * @author Pratik
 */
public class GroupsKeylinkClassBL implements Serializable {

    private Vector<String> groupName = null;
    private Vector<GroupKeysClass> keyGroups = null;
    public GroupsKeylinkClass dataObj = null;
    private String dataFilePath = "";
    public static String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "\\database\\dataFile";

    public Rectangle bounds = null;
    
    public static final int DUPLICATE_GROUP = 1;
    public static final int SUCCESSFULLY_ADDED = 0;

    public GroupsKeylinkClassBL(String filePath) {
        groupName = new Vector();
        keyGroups = new Vector();
        dataObj = new GroupsKeylinkClass();
        dataFilePath = filePath;
        bounds = new Rectangle(0,0, 200,200);
    }

    public int addGroup(String GroupName){

        if (groupName.contains(groupName)) {
            return DUPLICATE_GROUP;
        }

        groupName.add(GroupName);
        keyGroups.add(new GroupKeysClass());
        keyGroups.get(keyGroups.size() - 1).keyValue = new Vector();
        dataObj.setGroups(groupName);
        dataObj.setKeys(keyGroups);
//        ReadWriteObj.serializeDataOut(dataObj, DEFAULT_FILE_PATH);
        return SUCCESSFULLY_ADDED;
    }

    public void deleteGroup(String GroupName){
        int groupIndex = groupName.indexOf(GroupName);
        groupName.remove(groupIndex);
        keyGroups.get(groupIndex).keyValue.clear();
        keyGroups.remove(groupIndex);
        dataObj.setGroups(groupName);
        dataObj.setKeys(keyGroups);
//        ReadWriteObj.serializeDataOut(dataObj, DEFAULT_FILE_PATH);
    }

    public void updateGroup(String GroupName, JButton... Items){
        int groupIndex = groupName.indexOf(GroupName);

        keyGroups.get(groupIndex).keyValue.clear();
        for (JButton btn : Items) {
            keyGroups.get(groupIndex).keyValue.add(btn);
        }
        dataObj.setGroups(groupName);
        dataObj.setKeys(keyGroups);
//        ReadWriteObj.serializeDataOut(dataObj, DEFAULT_FILE_PATH);

    }

    public JButton[] getGroup(String GroupName) {
        int groupIndex = groupName.indexOf(GroupName);
        JButton[] ButtonList = new JButton[keyGroups.elementAt(groupIndex).keyValue.size()];
        ButtonList = keyGroups.elementAt(groupIndex).keyValue.toArray(ButtonList);

        return ButtonList;
    }

    public String[] getGroupNames() {
        if (groupName != null) {
            if (groupName.size() > 0) {
                String[] groupNames = new String[groupName.size()];
                groupNames = groupName.toArray(groupNames);
                return groupNames;
            }
        }
        return null;
    }
    

    public Rectangle getBounds(){
        return bounds;
    }
    public void setBounds(Rectangle bounds){
        this.bounds = bounds;
        this.dataObj.setBounds(bounds);
    }
    
    public void saveData() throws Exception{
        ReadWriteObj.serializeDataOut(dataObj, DEFAULT_FILE_PATH);        
    }
    
    public static GroupsKeylinkClassBL getStoredObject() throws Exception {
        GroupsKeylinkClassBL tempObj = new GroupsKeylinkClassBL(DEFAULT_FILE_PATH);
        tempObj.dataObj = ReadWriteObj.serializeDataIn(tempObj.dataFilePath);

        if (tempObj.dataObj != null) {
            tempObj.groupName = tempObj.dataObj.getGroups();
            tempObj.keyGroups = tempObj.dataObj.getKeys();
            tempObj.bounds = tempObj.dataObj.getBounds();
        } else {
            tempObj.dataObj = new GroupsKeylinkClass();
            tempObj.dataObj.setBounds(tempObj.bounds);
        }
        return tempObj;

    }
}
