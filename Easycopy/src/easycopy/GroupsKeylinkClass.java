/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easycopy;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Pratik
 */
public class GroupsKeylinkClass implements Serializable {

    private Vector<String> groups = null;
    private Vector<GroupKeysClass> keys = null;
    private Rectangle bounds = null;

    public GroupsKeylinkClass() {
        groups = new Vector<String> ();
        keys = new Vector<GroupKeysClass>();
        bounds = new Rectangle();
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
    
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
