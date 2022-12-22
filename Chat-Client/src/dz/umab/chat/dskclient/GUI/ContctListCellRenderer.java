package dz.umab.chat.dskclient.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class ContctListCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,

                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,

                                                   int column) {

        //Si la valeur de la cellule est un JButton, on transtype notre valeur

        if (value instanceof IconLabel) {

            return (JLabel) value;

        } else {

            return this;
        }

    }

}
