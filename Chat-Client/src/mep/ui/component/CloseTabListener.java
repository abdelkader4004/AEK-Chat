/*
 * Created on 30 mars 2005
 * CloseTabListener.java
 */
package mep.ui.component;

import java.util.EventListener;

/**
 * Listener pour catcher les evenements de fermeture d'onglet
 *
 * @author mep
 */
public interface CloseTabListener extends EventListener {
    /**
     * M�thode appeler lorsque que la croix est cliqu� dans un MTabbedPanePlus
     *
     * @param event
     */
    public void closeAction(CloseTabEvent event);
}
