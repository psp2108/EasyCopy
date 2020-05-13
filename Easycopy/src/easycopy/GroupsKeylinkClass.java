/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easycopy;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Pratik
 */
public class GroupsKeylinkClass implements Serializable {

    private Vector<String> groups = null;
    private Vector<GroupKeysClass> keys = null;

    public GroupsKeylinkClass() {
    }

    public Vector<String> getGroups() {
        return groups;
    }

    public void setGroups(Vector<String> groups) {
        this.groups = groups;
    }

    public Vector<GroupKeysClass> getKeys() {
        return keys;
    }

    public void setKeys(Vector<GroupKeysClass> keys) {
        this.keys = keys;
    }
}
