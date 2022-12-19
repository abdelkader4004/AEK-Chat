package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTabbedPane;
 
 
/**
 *
 * @author  alain
 */
public class JTabbedPaneWithCloseIcons extends javax.swing.JTabbedPane{
    JTabbedPaneWithCloseIcons moi; //pour avoir acces au l'element this dans les class internes
	public JTabbedPaneWithCloseIcons() {
		super();
        moi =this;
	}
 
	public void addTab(String title, Component component,int endroit) {
            super.addTab(title, component); //on ajoute une Tab � JTabbedPane
            super.setTabComponentAt(endroit, new CloseTabPanel(title)); //on applique le closeTabPanel a l'element "endroit"
	}
        
		//fonction qui permet d'affich� le bouton close
        public void afficheIconAt(int endroit){
            ((CloseTabPanel)moi.getTabComponentAt(endroit)).afficheIcon(true);
        }
		//fonction qui permet d'enlever le bouton close
        public void cacheIconAt(int endroit){
            ((CloseTabPanel)moi.getTabComponentAt(endroit)).afficheIcon(false);
        }
       
    
 
class CloseTabPanel extends JPanel{
        JButton button; 
        
	//constructeur sans boolean  qui de base met un bouton close
    public CloseTabPanel(String titre) {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setOpaque(false);
            JLabel label = new JLabel(titre);
            add(label);
            button = new TabButton();
            add(button);
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}
	//constructeur avec boolean  qui permet de choisir si oui ou non on veux un bouton close
		public CloseTabPanel(String titre, boolean b){
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setOpaque(false);
            JLabel label = new JLabel(titre);
            add(label);
            button = new TabButton();
            if(b){
            add(button);
            }
            //add more space to the top of the component
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
        //permet d'afficher ou cacher le bouton close
        public void afficheIcon(boolean b){
            if(b){
                if(this.getComponentCount()==1)
                    this.add(button);
            }else{
                if(this.getComponentCount()>1)
                    this.remove(button);
            }
        }
}
class TabButton extends JButton implements ActionListener {
    public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Fermer cet onglet");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Rends le bouton transparent
            setContentAreaFilled(false);
            //pas besoin d'avoir le focus
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addActionListener(this);            
        }
		/*
		* fonction qui ferme l'onglet du bouton close sur lequel on a cliqu�
		*/
    public void actionPerformed(ActionEvent e) {
            int X = new Double(((JButton)e.getSource()).getMousePosition().getX()).intValue();
            int Y = new Double(((JButton)e.getSource()).getMousePosition().getY()).intValue();
        
            int i = moi.getUI().tabForCoordinate((JTabbedPane)moi, X,Y);
            if (i != -1) {
                moi.remove(i);
            }
        }
 
        //we don't want to update UI for this button
        public void updateUI() {
        }
 
        //dessine la croix dans le bouton
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            } 
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }            
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
}
