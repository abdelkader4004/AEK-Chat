package GUI;

import java.awt.Component;

import javax.swing.JLabel;

import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;

class ContctListCellRenderer   extends DefaultTableCellRenderer {
  public Component getTableCellRendererComponent(JTable table,

                                                 Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,

                                                 int column) {

    //Si la valeur de la cellule est un JButton, on transtype notre valeur

    if (value instanceof IconLabel) {

      return (JLabel) value;

    }

    else {

      return this;
    }

  }

}
