package dz.umab.chat.dskclient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener pour un JPopupMenu sur une JTable
 *
 * @author schniouf
 */
public class PopupTableListener extends MouseAdapter {


    /**
     * Le tableau
     */
    private JTable table;

    /**
     * Le popup menu
     */
    private JPopupMenu popup;

    /**
     * @param table
     * @param popup
     */
    public PopupTableListener(JTable table, JPopupMenu popup) {
        super();
        this.table = table;
        this.popup = popup;
    }

    /**
     * M�thode appel�e � l'enfoncement d'un bouton de la souris
     */
    public void mousePressed(MouseEvent e) {
        // S'il s'agit d'un clic droit, on simule un clic gauche
        // pour s�lectionner l'�l�ment situ� sous la souris
        if (SwingUtilities.isRightMouseButton(e)) {
            Robot r;
            try {
                r = new Robot();
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * M�thode appel�e au rel�chement d'un bouton de la souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) // Si c'est un clic pour afficher un popup menu
        {
            // On n'affiche le popup que si un �l�ment est s�lectionn�
            if (table.getSelectedRow() >= 0)
                popup.show(table, e.getX(), e.getY());
        }
    }

    public JPopupMenu getPopup() {
        return popup;
    }

    public void setPopup(JPopupMenu popup) {
        this.popup = popup;
    }

    public JTable getTable() {
        return table;
    }

    public void setList(JTable table) {
        this.table = table;
    }

}
 