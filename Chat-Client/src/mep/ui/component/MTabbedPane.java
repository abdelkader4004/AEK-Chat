package mep.ui.component;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


/**
 * Cr�er un JTabbedPane avec un bouton de fermeture et avec la meme tete que ceux de swt
 */
public class MTabbedPane extends JTabbedPane {

    /**
     * Contantes pour definir l'onglet fermant.<br>
     * WITH_CLOSE_CROSS : affiche une croix pour fermer l'onglet<br>
     * NONE : affiche l'onglet de base d'un JTabbedPane<br>
     */
    public static final int WITH_CLOSE_CROSS = 1;
    /**
     * Contantes pour definir l'onglet fermant.<br>
     * WITH_CLOSE_CROSS : affiche une croix pour fermer l'onglet<br>
     * NONE : affiche l'onglet de base d'un JTabbedPane<br>
     */
    public static final int WITH_NOTHING = 2;
    protected EventListenerList listenerList = new EventListenerList();
    private ThemeUI themeUI;
    private Color selectedColor;
    private Color noneSelectedColor;

    /**
     * @param selectedColor     : couleur de l'onglet selectionn�
     * @param noneSelectedColor : couleur des onglets non selectionn�s
     */
    public MTabbedPane(Color selectedColor, Color noneSelectedColor) {
        super();
        this.selectedColor = selectedColor;
        this.noneSelectedColor = noneSelectedColor;


        this.setBackground(noneSelectedColor);
        this.addMouseListener(new ecouteurDeMouse());
        this.addMouseMotionListener(new ecouteurDeMouseMotion());

        this.themeUI = new ThemeUI(this.selectedColor, this.noneSelectedColor);
        this.setUI(themeUI);

        themeUI.setTabbedPane(this);
    }


    public void insertTab(String title, Icon icon, Component component, String tip,
                          int index, int constraint) {

        switch (constraint) {
            case WITH_NOTHING:
                this.insertTab(title, icon, component, tip, index);
                break;
            case WITH_CLOSE_CROSS:
                this.insertTab(title, new CloseTabIcon(icon, title, noneSelectedColor), component, tip, index);
                break;
            default:
                this.insertTab(title, icon, component, tip, index);
                break;
        }
    }


    /**
     * Lance un evenement a tous les ecouteurs lorsque q'un onglet a �t� ferm�
     *
     * @param object
     */
    private void fireCloseTabAction(int tabNumber) {
        CloseTabListener[] listeners = (CloseTabListener[]) listenerList.getListeners(CloseTabListener.class);


        CloseTabEvent event = new CloseTabEvent(this, tabNumber);

        for (int i = listeners.length - 1; i >= 0; i--)
            listeners[i].closeAction(event);
    }


    /**
     * permet d'ajouter un composant dans la liste de ceux qui veulent
     * �tre inform�s de l'�v�nement
     */
    public void addCloseTabAction(CloseTabListener listener) {
        this.listenerList.add(CloseTabListener.class, listener);
    }


    /**
     * @param title      : titre de l'onglet
     * @param component  : composant que contient l'onglet
     * @param constraint : contrainte qui definit la pr�sence de la croix de fermeture ou non
     */
    public void addTab(String title, Component component, int constraint) {
        switch (constraint) {
            case WITH_CLOSE_CROSS:
                super.addTab(title, new CloseTabIcon(null, title, noneSelectedColor), component);
                break;

            case WITH_NOTHING:
                super.addTab(title, component);
                break;

            default:
                super.addTab(title, component);
                break;
        }
    }

    /**
     * @param title      : titre de l'onglet
     * @param component  : composant que contient l'onglet
     * @param constraint : contrainte qui definit la pr�sence de la croix de fermeture ou non
     * @param icone      : icone a afficher du cot� gauche du texte
     */
    public void addTab(String title, Component component, int constraint, Icon icone) {
        switch (constraint) {
            case WITH_NOTHING:
                this.addTab(title, component, WITH_NOTHING);
                this.setIconAt(this.getTabCount() - 1, icone);
                break;
            case WITH_CLOSE_CROSS:
                this.addTab(title, component, WITH_CLOSE_CROSS);
                this.setIconAt(this.getTabCount() - 1, icone);
                break;
            default:
                this.addTab(title, component, WITH_NOTHING);
                this.setIconAt(this.getTabCount() - 1, icone);
                break;
        }
    }


    /**
     * Permet de changer l'icone a gauche du texte
     */
    public void setIconAt(int position, Icon icone) {
        if (getIconAt(position) instanceof CloseTabIcon)
            ((CloseTabIcon) getIconAt(position)).setIcon(icone);
        else
            super.setIconAt(position, icone);
    }


    private class ecouteurDeMouse extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int tabNumber = getUI().tabForCoordinate(MTabbedPane.this, e.getX(), e.getY());
            if (tabNumber < 0 || !(getIconAt(tabNumber) instanceof CloseTabIcon)) return;
            Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY())) {


                if (listenerList.getListenerCount() == 0)
                    remove(tabNumber);
                else
                    fireCloseTabAction(tabNumber);


                //reloadage de l'UI pour tuer les evenements pendants
                //themeUI = new ThemeUI(selectedColor, noneSelectedColor);
                //setUI(themeUI);
                //themeUI.setTabbedPane(JTabbedPanePlenadis.this);

            }
        }


        public void mouseEntered(MouseEvent e) {
            for (int i = 0; i < getComponentCount(); i++)
                if (getIconAt(i) instanceof CloseTabIcon)
                    if (getIconAt(i) instanceof CloseTabIcon) {
                        ((CloseTabIcon) getIconAt(i)).setColor(noneSelectedColor);
                        ((CloseTabIcon) getIconAt(i)).setRollOved(false);
                    }
            repaint();
        }


        public void mouseExited(MouseEvent e) {
            for (int i = 0; i < getComponentCount(); i++)
                if (getIconAt(i) instanceof CloseTabIcon) {
                    ((CloseTabIcon) getIconAt(i)).setColor(noneSelectedColor);
                    ((CloseTabIcon) getIconAt(i)).setRollOved(false);
                }
            repaint();
        }

    }


    private class ecouteurDeMouseMotion extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            int tabNumber = getUI().tabForCoordinate(MTabbedPane.this, e.getX(), e.getY());
            if (tabNumber < 0 || !(getIconAt(tabNumber) instanceof CloseTabIcon)) {
                for (int i = 0; i < getTabCount(); i++)
                    if (getIconAt(i) instanceof CloseTabIcon) {
                        ((CloseTabIcon) getIconAt(i)).setColor(noneSelectedColor);
                        ((CloseTabIcon) getIconAt(i)).setRollOved(false);
                    }
                repaint();
                return;
            }
            Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY()))
                ((CloseTabIcon) getIconAt(tabNumber)).setRollOved(true);
            else {
                for (int i = 0; i < getTabCount(); i++)
                    if (getIconAt(i) instanceof CloseTabIcon) {
                        ((CloseTabIcon) getIconAt(i)).setColor(noneSelectedColor);
                        ((CloseTabIcon) getIconAt(i)).setRollOved(false);
                    }

                ((CloseTabIcon) getIconAt(tabNumber)).setColor(Color.GRAY);
            }
            repaint();
        }
    }


}


class CloseTabIcon implements Icon {
    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private boolean isRollOved = false;

    private Icon fileIcon;

    private int titleLen;
    private String title;

    private Color fond;

    public CloseTabIcon(Icon fileIcon, String title, Color fond) {
        this.fond = fond;
        this.title = title;
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }


    public void setRollOved(boolean b) {
        isRollOved = b;
    }

    public void setIcon(Icon icone) {
        this.fileIcon = icone;
    }


    public void setColor(Color fond) {
        this.fond = fond;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;

        titleLen = g.getFontMetrics().stringWidth(title);

        if (isRollOved)
            g.setColor(Color.RED);
        else
            g.setColor(fond);


        int xCross = x + titleLen + 10 + (fileIcon != null ? fileIcon.getIconWidth() : 0);
        int y_p = y + (fileIcon != null ? fileIcon.getIconHeight() / 2 - 6 : 2);

        //g.drawRect(xCross+1, y_p+1, 11, 11);
        g.drawLine(xCross + 3, y_p + 3, xCross + 10, y_p + 10);
        g.drawLine(xCross + 3, y_p + 4, xCross + 9, y_p + 10);
        g.drawLine(xCross + 4, y_p + 3, xCross + 10, y_p + 9);
        g.drawLine(xCross + 10, y_p + 3, xCross + 3, y_p + 10);
        g.drawLine(xCross + 10, y_p + 4, xCross + 4, y_p + 10);
        g.drawLine(xCross + 9, y_p + 3, xCross + 3, y_p + 9);
        g.setColor(Color.BLACK);

        if (fileIcon != null)
            fileIcon.paintIcon(c, g, x, y);

    }

    public int getIconWidth() {
        return (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    public int getIconHeight() {
        if (fileIcon != null)
            if (fileIcon.getIconHeight() > height)
                return fileIcon.getIconHeight();

        return height + 1;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos + titleLen + 10 + (fileIcon != null ? fileIcon.getIconWidth() : 0), y_pos, width, height);
    }
}


class ThemeUI extends BasicTabbedPaneUI {

    private MTabbedPane pane;


    private Image mOnglet0;
    private Image sOnglet0;
    private Image rOnglet0;

    private Image mOnglet1;
    private Image sOnglet1;
    private Image dOnglet1;

    private Color selectedColor;

    public ThemeUI(Color selectedColor, Color noneSelectedColor) {

        this.selectedColor = selectedColor;

        mOnglet0 = drawMiddleTab(selectedColor);
        sOnglet0 = drawGSideTab(selectedColor);
        rOnglet0 = drawRightSelectedTab(selectedColor);

        mOnglet1 = drawMiddleTab(noneSelectedColor);
        sOnglet1 = drawGSideTab(noneSelectedColor);
        dOnglet1 = drawDSideTab(noneSelectedColor);

    }


    public void setTabbedPane(MTabbedPane pane) {
        this.pane = pane;
    }


    protected void paintTabBorder(Graphics arg0, int arg1, int arg2, int arg3,
                                  int arg4, int arg5, int arg6, boolean arg7) {
    }


    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

        Graphics2D g2d = (Graphics2D) g;


        /** Lissage du texte et des dessins */
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);


        if (isSelected) {
            g2d.drawImage(sOnglet0, x, y, 7, h, null);
            g2d.drawImage(mOnglet0, x + 7, y, w - 14, h, null);
            g2d.drawImage(rOnglet0, x + w - 7, y, 36, h, null);
        } else {
            Color temp = pane.getBackgroundAt(tabIndex);

            mOnglet1 = drawMiddleTab(temp);
            sOnglet1 = drawGSideTab(temp);
            dOnglet1 = drawDSideTab(temp);

            g2d.drawImage(sOnglet1, x, y, 7, h, null);
            g2d.drawImage(mOnglet1, x + 7, y, w + 1 - 14, h, null);
            g2d.drawImage(dOnglet1, x + w - 6, y, 7, h, null);
        }

    }

    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

        super.paintContentBorder(g, tabPlacement, selectedIndex);
    }


    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        Rectangle rect = getTabBounds(pane, selectedIndex);

        g.setColor(this.selectedColor);
        g.fillRect(x, y, x + w, y + h);

        g.setColor(Color.GRAY);
        g.drawLine(x, y, rect.x, y);
        g.drawLine(rect.width + rect.x + 25, y, x + w, y);
    }


    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        g.setColor(Color.GRAY);
        g.drawLine(x, y, x, y + h);
    }

    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        g.setColor(Color.GRAY);
        g.drawLine(x + w - 1, y, x + w - 1, y + h);
    }

    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        Rectangle rect = getTabBounds(pane, selectedIndex);

        g.setColor(Color.GRAY);
        g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
    }


    public Image drawRightSelectedTab(Color c) {

        Image i = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(36, 26, Transparency.TRANSLUCENT);
        Graphics g = i.getGraphics();


        g.setColor(Color.GRAY);
        g.drawLine(0, 0, 1, 0);
        g.drawLine(2, 1, 3, 1);
        g.drawLine(4, 2, 6, 2);
        g.drawLine(7, 3, 8, 3);
        g.drawLine(9, 4, 26, 21);
        g.drawLine(27, 22, 28, 22);
        g.drawLine(29, 23, 31, 23);
        g.drawLine(32, 24, 34, 24);
        g.drawLine(35, 25, 36, 25);

        g.setColor(c);
        g.drawRect(0, 1, 1, 25);
        g.drawRect(2, 2, 1, 25);
        g.fillRect(4, 3, 3, 25);
        g.fillRect(7, 4, 2, 25);
        g.fillRect(9, 5, 1, 25);
        g.fillRect(10, 6, 1, 25);
        g.fillRect(11, 7, 1, 25);

        g.fillRect(12, 8, 1, 25);
        g.fillRect(13, 9, 1, 25);
        g.fillRect(14, 10, 1, 25);
        g.fillRect(15, 11, 1, 25);
        g.fillRect(16, 12, 1, 25);
        g.fillRect(17, 13, 1, 25);
        g.fillRect(18, 14, 1, 25);
        g.fillRect(19, 15, 1, 25);
        g.fillRect(20, 16, 1, 25);
        g.fillRect(21, 17, 1, 25);

        g.fillRect(22, 18, 1, 25);
        g.fillRect(23, 19, 1, 25);
        g.fillRect(24, 20, 1, 25);

        g.fillRect(25, 21, 1, 25);
        g.fillRect(26, 22, 1, 25);

        g.fillRect(27, 23, 2, 25);

        return i;
    }


    public Image drawMiddleTab(Color c) {
        Image i = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1, 26, Transparency.TRANSLUCENT);
        Graphics g = i.getGraphics();


        g.setColor(Color.GRAY);
        g.drawRect(0, 0, 1, 1);
        g.setColor(c);
        g.drawRect(0, 1, 1, 25);

        return i;
    }


    protected void paintText(Graphics arg0, int arg1, Font arg2, FontMetrics arg3,
                             int arg4, String arg5, Rectangle arg6, boolean arg7) {

        Graphics2D g2d = (Graphics2D) arg0;


        /** Lissage du texte et des dessins */
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        if (pane.getIconAt(arg4) instanceof CloseTabIcon)
            if (pane.getSelectedIndex() == arg4 - 1)
                super.paintText(g2d, arg1, arg2, arg3, arg4, arg5, new Rectangle(arg6.x, arg6.y, arg6.width, arg6.height), arg7);
            else
                super.paintText(g2d, arg1, arg2, arg3, arg4, arg5, new Rectangle(arg6.x - 7, arg6.y, arg6.width, arg6.height), arg7);
        else if (pane.getSelectedIndex() == arg4 - 1)
            super.paintText(g2d, arg1, arg2, arg3, arg4, arg5, new Rectangle(arg6.x + 6, arg6.y, arg6.width, arg6.height), arg7);
        else
            super.paintText(g2d, arg1, arg2, arg3, arg4, arg5, new Rectangle(arg6.x - 1, arg6.y, arg6.width, arg6.height), arg7);
    }


    protected void paintIcon(Graphics arg0, int arg1, int arg2, Icon arg3,
                             Rectangle arg4, boolean arg5) {
        if (pane.getIconAt(arg2) instanceof CloseTabIcon)
            if (pane.getSelectedIndex() == arg2 - 1)
                super.paintIcon(arg0, arg1, arg2, arg3, new Rectangle(arg4.x - 2, arg4.y, arg4.width, arg4.height), arg5);
            else
                super.paintIcon(arg0, arg1, arg2, arg3, new Rectangle(arg4.x - 9, arg4.y, arg4.width, arg4.height), arg5);
        else if (pane.getSelectedIndex() == arg2 - 1)
            super.paintIcon(arg0, arg1, arg2, arg3, new Rectangle(arg4.x + 4, arg4.y, arg4.width, arg4.height), arg5);
        else
            super.paintIcon(arg0, arg1, arg2, arg3, new Rectangle(arg4.x - 3, arg4.y, arg4.width, arg4.height), arg5);

    }


    public Image drawGSideTab(Color c) {
        Image i = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(7, 26, Transparency.TRANSLUCENT);
        Graphics g = i.getGraphics();


        g.setColor(Color.GRAY);
        g.drawLine(6, 0, 6, 0);
        g.drawLine(5, 1, 4, 1);
        g.drawLine(3, 2, 3, 2);
        g.drawLine(2, 3, 2, 3);
        g.drawLine(1, 4, 1, 5);
        g.drawLine(0, 6, 0, 26);

        g.setColor(c);
        g.drawLine(1, 26, 1, 6);
        g.drawLine(2, 26, 2, 4);
        g.drawLine(3, 26, 3, 3);
        g.drawLine(4, 26, 4, 2);
        g.drawLine(5, 26, 5, 2);
        g.drawLine(6, 26, 6, 1);

        return i;
    }


    public Image drawDSideTab(Color c) {
        Image i = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(7, 26, Transparency.TRANSLUCENT);
        Graphics g = i.getGraphics();


        g.setColor(Color.GRAY);
        g.drawLine(0, 0, 0, 0);
        g.drawLine(1, 1, 2, 1);
        g.drawLine(3, 2, 3, 2);
        g.drawLine(4, 3, 4, 3);
        g.drawLine(5, 4, 5, 5);
        g.drawLine(6, 6, 6, 26);

        g.setColor(c);
        g.drawLine(5, 26, 5, 6);
        g.drawLine(4, 26, 4, 4);
        g.drawLine(3, 26, 3, 3);
        g.drawLine(2, 26, 2, 2);
        g.drawLine(1, 26, 1, 2);
        g.drawLine(0, 26, 0, 1);

        return i;
    }

    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        if (pane.getSelectedIndex() == tabIndex - 1 && pane.getIconAt(tabIndex) instanceof CloseTabIcon)
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 20;
        else if (pane.getSelectedIndex() == tabIndex - 1 && !(pane.getIconAt(tabIndex) instanceof CloseTabIcon))
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 15;
        else if (pane.getIconAt(tabIndex) instanceof CloseTabIcon)
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 5;
        else
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    }


    protected void paintFocusIndicator(Graphics arg0, int arg1,
                                       Rectangle[] arg2, int arg3, Rectangle arg4, Rectangle arg5,
                                       boolean arg6) {
    }

}