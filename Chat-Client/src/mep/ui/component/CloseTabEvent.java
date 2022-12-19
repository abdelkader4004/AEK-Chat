/*
 * Created on 30 mars 2005
 * CloseTabEvent.java
 */
package mep.ui.component;

/**
 * Objet renvoyé au listener lors de la fermeture d'un onglet
 * @author mep
 *
 */
public class CloseTabEvent {
    private MTabbedPane tabbedPane;
    private int tabIndex;
    
    public CloseTabEvent (MTabbedPane tabbedPane, int tabIndex) {
        this.tabbedPane = tabbedPane;
        this.tabIndex = tabIndex;
    }

    
    /**
     * 
     * @return l'index de l'onglet cliqué
     */
    public int getTabIndex () {
        return this.tabIndex;
    }
    
    /**
     * 
     * @return le tabbedPane qui a subit le clique
     */
    public MTabbedPane getTabbedPane () {
        return this.tabbedPane;
    }
    
}
