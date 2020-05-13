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
import java.util.*;
import java.awt.event.*;

/**
 *
 * @author Pratik
 */
public class Easycopy extends JFrame implements ActionListener, KeyListener {

    public static JButton addButton = null;
    public static JTextField btnLabel = null;
    public static Container cpane = null;
    public static Easycopy mainFrame = null;
    public static String htmlStart = "<html><pre>";
    public static String htmlEnd = "</html></pre>";

    public static JPanel topPanel = null;
    public static JPanel centerPanel = null;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        addButton = new JButton("Add");
        btnLabel = new JTextField(5);
        topPanel = new JPanel();
        centerPanel = new JPanel();

        mainFrame = new Easycopy();
        mainFrame.setVisible(true);
        mainFrame.setSize(200, 200);
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

        centerPanel.setLayout(new FlowLayout());

        btnLabel.requestFocus();

        addButton.addActionListener(mainFrame);
        btnLabel.addKeyListener(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = null;

        if (e.getSource() == addButton) {
            b = new JButton(btnLabel.getText());
            b.setToolTipText(htmlStart + getTextFromClipboard() + htmlEnd);
            b.addActionListener(mainFrame);

            centerPanel.add(b);
            btnLabel.setText("");
            btnLabel.setToolTipText(btnLabel.getText());
            btnLabel.requestFocus();
            mainFrame.repaint();
            mainFrame.revalidate();
        } else {
            b = (JButton) e.getSource();

            System.out.println(e.getModifiers());

            if (e.getModifiers() == 24) {
                System.out.println("Alt Pressed");
                centerPanel.remove(b);
                setTextToClipboard("");
                mainFrame.repaint();
                mainFrame.revalidate();
            } else if (e.getModifiers() == 17) {
                System.out.println("Shift Pressed");
                b.setToolTipText(htmlStart + getTextFromClipboard() + htmlEnd);
                if (!btnLabel.getText().equals("")) {
                    b.setText(btnLabel.getText());
                    btnLabel.setText("");
                }
            } else {
                System.out.println("Text Copied");
                String tempOP = b.getToolTipText();
                tempOP = tempOP.substring(htmlStart.length(), tempOP.length() - htmlEnd.length());
                setTextToClipboard(tempOP);
            }
        }
    }

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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == btnLabel) {
            btnLabel.setToolTipText(btnLabel.getText());
        }
    }

}
