/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easycopy;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pratik
 */
public class Easycopy extends JFrame implements ActionListener, KeyListener, ItemListener, MouseListener, MouseMotionListener, ComponentListener {

    public static JButton addButton = null;
    public static JTextField btnLabel = null;
    public static Container cpane = null;
    public static Easycopy mainFrame = null;
    public static String htmlStart = "<html><pre>";
    public static String htmlEnd = "</html></pre>";

    public static GroupsKeylinkClassBL keyvalues = null;
    public static boolean MouseInsideAdd = false;
    public static boolean ThreadRunning = false;
    public static Rectangle Bounds = null;

    public static JPanel topPanel = null;
    public static JPanel centerPanel = null;
    public static SortedComboBoxModel<String> model = null;
    public static JComboBox groupSelection = null;

    public static void setTextToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }

    public static String getTextFromClipboard() {
        try {
            String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            return data;
        } catch (Exception ex) {
            return " ";
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        File dir = new File(GroupsKeylinkClassBL.DEFAULT_DIRECTORY_PATH);
        if (!dir.exists()) {
            System.out.println("Directory Created");
            dir.mkdir();
        }

        keyvalues = GroupsKeylinkClassBL.getStoredObject();

        addButton = new JButton("Add");
        btnLabel = new JTextField(5);
        topPanel = new JPanel();
        centerPanel = new JPanel();

        model = new SortedComboBoxModel<>();
        groupSelection = new JComboBox(model);

        mainFrame = new Easycopy();
        mainFrame.setVisible(true);
        mainFrame.setBounds(keyvalues.getBounds());
        mainFrame.setTitle("Easy Copy");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setMinimumSize(new Dimension(143, 110));

        cpane = mainFrame.getContentPane();

        cpane.setLayout(new BorderLayout());
        cpane.add(topPanel, BorderLayout.NORTH);
        cpane.add(centerPanel, BorderLayout.CENTER);

        topPanel.setLayout(new FlowLayout());
        topPanel.add(addButton);
        topPanel.add(btnLabel);
        topPanel.add(groupSelection);

        centerPanel.setLayout(new FlowLayout());

        btnLabel.requestFocus();

        addButton.addActionListener(mainFrame);
        btnLabel.addKeyListener(mainFrame);
        groupSelection.addItemListener(mainFrame);
        addButton.addMouseListener(mainFrame);
        mainFrame.initializeForm();

        mainFrame.addMouseMotionListener(mainFrame);
        mainFrame.addComponentListener(mainFrame);
        addButton.addMouseMotionListener(mainFrame);
        addButton.addKeyListener(mainFrame);
    }

    public void initializeForm() {
        String groupNameList[] = keyvalues.getGroupNames();
        addGroupJCombo(groupNameList);
        if (groupNameList != null) {
            groupSelection.setSelectedIndex(0);
        }
        updateButtonList();
    }

    public JButton[] getButtonArray() {

        Component[] comp = centerPanel.getComponents();
        JButton[] btns = new JButton[comp.length];
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof JButton) {
                JButton btn = (JButton) comp[i];
                btns[i] = btn;
            }
        }
        return btns;
    }

    public void updateButtonList() {
        removeAllComponents();
        if (groupSelection.getSelectedItem() != null) {
            if (!groupSelection.getSelectedItem().equals("")) {
                JButton[] btnList = keyvalues.getGroup(groupSelection.getSelectedItem().toString());
                for (JButton btn : btnList) {
                    centerPanel.add(btn);
                }
                mainFrame.repaint();
                mainFrame.revalidate();
            }
        }
    }

    public boolean ifPresentInJCombo(JComboBox jCombo, String item) {
        int size = jCombo.getItemCount();
        for (int i = 0; i < size; i++) {
            if (jCombo.getItemAt(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void removeAllComponents() {
        Component[] comp = centerPanel.getComponents();
        for (Component comp1 : comp) {
            if (comp1 instanceof JButton) {
                JButton btn = (JButton) comp1;
                centerPanel.remove(btn);
            }
        }
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b;
        int modifier = e.getModifiers();

        //Shift : 17
        //Alt : 24
        //Ctrl : 18
        //No key : 16
        if (e.getSource() == addButton) {

            if (modifier == 18 && e.getActionCommand().equals("Add Group")) {
                //Ctrl Pressed Add Group

                if (!btnLabel.getText().equals("")) {

                    if (!ifPresentInJCombo(groupSelection, btnLabel.getText())) {
                        keyvalues.addGroup(btnLabel.getText());
                        addGroupJCombo(btnLabel.getText());
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "You are adding a duplicate group");
                    }

                    groupSelection.setSelectedItem(btnLabel.getText());
                    btnLabel.setText("");
                    btnLabel.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Empty group name not accepted");
                }

            } else if (modifier == 24 && e.getActionCommand().equals("Delete Group")) {
                //Alt Pressed Delete Group
                if (groupSelection.getItemCount() > 0) {
                    keyvalues.deleteGroup(groupSelection.getSelectedItem().toString());
                    removeGroupJCombo(groupSelection.getSelectedItem().toString());
                }
            } else if (modifier == 17 && e.getActionCommand().equals("Rename Group")) {
                //Shift Pressed Rename Group
                String newGrpName = btnLabel.getText();

                if (!btnLabel.getText().equals("") && !ifPresentInJCombo(groupSelection, newGrpName)) {

                    String tempGrpName = groupSelection.getSelectedItem().toString();
                    JButton[] tempButtons = keyvalues.getGroup(tempGrpName);

                    keyvalues.deleteGroup(tempGrpName);
                    removeGroupJCombo(tempGrpName);

                    keyvalues.addGroup(newGrpName);
                    keyvalues.updateGroup(newGrpName, tempButtons);
                    addGroupJCombo(newGrpName);
                    groupSelection.setSelectedItem(newGrpName);
                    btnLabel.setText("");

                }

            } else if (modifier == 16 && e.getActionCommand().equals("Add")) {
                //Normal Add Button

                if (groupSelection.getItemCount() > 0) {

                    b = new JButton(btnLabel.getText());
                    b.setToolTipText(htmlStart + getTextFromClipboard() + htmlEnd);

                    b.addActionListener(mainFrame);
                    b.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            JComponent component = (JComponent) e.getSource();
                            if (component != addButton && component != groupSelection) {
                                int x = component.getX();
                                int y = component.getY();
                                MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x + 1, y + 1, 0, false);
                                ToolTipManager.sharedInstance().mouseMoved(phantom);
                            }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            mainFrame.repaint();
                            mainFrame.revalidate();
                        }
                    });

                    centerPanel.add(b);
                    btnLabel.setText("");
                    btnLabel.setToolTipText(btnLabel.getText());
                    btnLabel.requestFocus();
                    mainFrame.repaint();
                    mainFrame.revalidate();

                    keyvalues.updateGroup(groupSelection.getSelectedItem().toString(), getButtonArray());

                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Add and select group first");
                }

            }

            mainFrame.repaint();
            mainFrame.revalidate();
            try {
                keyvalues.saveData();
            } catch (Exception ex) {
                Logger.getLogger(Easycopy.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            b = (JButton) e.getSource();

            switch (modifier) {
                case 24:
                    centerPanel.remove(b);
                    setTextToClipboard("");
                    mainFrame.repaint();
                    mainFrame.revalidate();

                    keyvalues.updateGroup(groupSelection.getSelectedItem().toString(), getButtonArray());
                    try {
                        keyvalues.saveData();
                    } catch (Exception ex) {
                        Logger.getLogger(Easycopy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 17:
                    b.setToolTipText(htmlStart + getTextFromClipboard() + htmlEnd);
                    if (!btnLabel.getText().equals("")) {
                        b.setText(btnLabel.getText());
                        btnLabel.setText("");
                    }
                    mainFrame.repaint();
                    mainFrame.revalidate();

                    keyvalues.updateGroup(groupSelection.getSelectedItem().toString(), getButtonArray());
                    try {
                        keyvalues.saveData();
                    } catch (Exception ex) {
                        Logger.getLogger(Easycopy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    String tempOP = b.getToolTipText();
                    tempOP = tempOP.substring(htmlStart.length(), tempOP.length() - htmlEnd.length());
                    setTextToClipboard(tempOP);
                    break;
            }
        }

    }

    public void addGroupJCombo(String... grpName) {
        if (grpName != null) {
            for (String item : grpName) {
                groupSelection.addItem(item);
            }
        }
    }

    public void removeGroupJCombo(String grpName) {
        groupSelection.removeItem(grpName);
    }

    //Shift : 16
    //Alt : 18
    //Ctrl : 17
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == btnLabel) {
            btnLabel.setToolTipText(btnLabel.getText());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == btnLabel) {
            btnLabel.setToolTipText(btnLabel.getText());
        }

        if (MouseInsideAdd) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case 16:
                    addButton.setText("Rename Group");
                    break;
                case 18:
                    addButton.setText("Delete Group");
                    break;
                case 17:
                    addButton.setText("Add Group");
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == btnLabel) {
            btnLabel.setToolTipText(btnLabel.getText());
        }
        addButton.setText("Add");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        updateButtonList();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    //Normal : 0
    //Ctrl : 2
    //Alt : 8
    //Shift : 1
    @Override
    public void mouseEntered(MouseEvent e) {
        int modifier = e.getModifiers();

        switch (modifier) {
            case 8:
                if (e.getSource() == addButton) {
                    addButton.setText("Delete Group");
                }
                break;
            case 2:
                if (e.getSource() == addButton) {
                    addButton.setText("Add Group");
                }
                break;
            case 1:
                if (e.getSource() == addButton) {
                    addButton.setText("Rename Group");
                }
                break;
            default:
                if (e.getSource() == addButton) {
                    addButton.setText("Add");
                }
                break;
        }
        MouseInsideAdd = true;

    }

    @Override
    public void mouseExited(MouseEvent e) {
        addButton.setText("Add");
        MouseInsideAdd = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (e.getSource() == addButton) {
            int modifier = e.getModifiers();

            switch (modifier) {
                case 8:
                    if (e.getSource() == addButton) {
                        addButton.setText("Delete Group");
                    }
                    break;
                case 2:
                    addButton.setText("Add Group");
                    if (e.getSource() == addButton) {
                    }
                    break;
                case 1:
                    if (e.getSource() == addButton) {
                        addButton.setText("Rename Group");
                    }
                    break;
                default:
                    if (e.getSource() == addButton) {
                        addButton.setText("Add");
                    }
                    break;
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Bounds = mainFrame.getBounds();
        if (!ThreadRunning) {
            ThreadRunning = true;
            new saveDataInBG().start();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        Bounds = mainFrame.getBounds();
        if (!ThreadRunning) {
            ThreadRunning = true;
            new saveDataInBG().start();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    public class saveDataInBG extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(700);
                keyvalues.setBounds(Bounds);
                keyvalues.saveData();
            } catch (Exception ex) {
                System.out.println("Bounds--- " + ex);
                Logger.getLogger(Easycopy.class.getName()).log(Level.SEVERE, null, ex);
            }
            ThreadRunning = false;
        }
    }

}
