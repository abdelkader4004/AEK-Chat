package dz.umab.chat.dskclient.GUI;

import dz.umab.chat.dskclient.client.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 *
 * @author non attribuable
 * @version 1.0
 */
public class ContactListTableModel
        extends AbstractTableModel {

    Icon[] states = new Icon[2];
    /**
     *
     */
    private Vector[] dataList = new Vector[2];

    /**
     *
     */
    public ContactListTableModel() {

        dataList[0] = new Vector();
        dataList[1] = new Vector();
        states[0] = new ImageIcon(ContactListTableModel.class.getResource("Grey.png"));
        states[1] = new ImageIcon(ContactListTableModel.class.getResource("Green.png"));

    }

    /**
     * getColumnCount
     *
     * @return int
     */
    public int getColumnCount() {
        return 2;
    }

    /**
     * getRowCount
     *
     * @return int
     */
    public int getRowCount() {
        return this.dataList[0].size();
    }

    /**
     * getValueAt
     *
     * @param rowIndex    int
     * @param columnIndex int
     * @return Object
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o = null;
        try {
            o = this.dataList[columnIndex].elementAt(rowIndex);
        } catch (Exception ex) {
            System.out.println("Erreur dans getValueAt");
        }
        return o;
    }

    /**
     * @param col int
     * @return String
     */
    public String getColumnName(int col) {

        return "";

    }

    /**
     * @param col int
     * @return Class
     */
    public Class getColumnClass(int col) {
        return this.dataList[col].elementAt(0).getClass();
    }

    public void addContact(int state, OurString name) {
        IconLabel lab = new IconLabel();
        lab.setIcon(states[state]);
        dataList[0].add(lab);
        dataList[1].add(name);
    }

    public OurString removeContact(int index) {
        OurString s = (OurString) dataList[1].elementAt(index);
        dataList[0].remove(index);
        dataList[1].remove(index);
        return s;
    }

    public void changeState(OurString pseudo, int stat) {
        IconLabel lab = (IconLabel) dataList[0].elementAt(dataList[1].indexOf(pseudo));
        lab.setIcon(states[stat]);
    }

    public int getState(int index) {
        if (states[0].equals(((JLabel) dataList[0].elementAt(index)).getIcon())) {
            return 0;
        } else {
            return 1;
        }
    }

    public String getPseudo(int index) {
        return ((OurString) dataList[1].elementAt(index)).toString();
    }
}
