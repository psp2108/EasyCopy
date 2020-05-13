
package easycopy;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

public class SortedComboBoxModel<E extends Comparable<? super E>> extends DefaultComboBoxModel<E> {

    public SortedComboBoxModel() {
        super();
    }

    public SortedComboBoxModel(E[] items) {
        Arrays.sort(items);
        int size = items.length;
        for (int i = 0; i < size; i++) {
            super.addElement(items[i]);
        }
        setSelectedItem(items[0]);
    }

    public SortedComboBoxModel(Vector<E> items) {
        Collections.sort(items);
        int size = items.size();
        for (int i = 0; i < size; i++) {
            super.addElement(items.elementAt(i));
        }
        setSelectedItem(items.elementAt(0));
    }

    @Override
    public void addElement(E element) {
        insertElementAt(element, 0);
    }

    @Override
    public void insertElementAt(E element, int index) {
        int size = getSize();
        for (index = 0; index < size; index++) {
            Comparable c = (Comparable) getElementAt(index);
            if (c.compareTo(element) > 0) {
                break;
            }
        }
        super.insertElementAt(element, index);
    }
}