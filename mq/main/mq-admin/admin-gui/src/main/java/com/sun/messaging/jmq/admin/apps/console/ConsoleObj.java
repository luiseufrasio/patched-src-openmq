/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2000-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * @(#)ConsoleObj.java	1.20 06/27/07
 */ 

package com.sun.messaging.jmq.admin.apps.console;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/** 
 * This class represents the superclass of any object that can be 
 * manipulated in the JMQ Administration Console.
 * <P>
 * This object may need to contain information that is relevant to
 * any piece of the console GUI as well as <EM>real</EM> information
 * used to perform the administration task. This includes things
 * like:
 * <UL>
 * <LI>What icon to show in the explorer pane for this object
 * <LI>What menus to enable when it is selected
 * <LI>What inspector pane class to use when inspecting this
 * object
 * </UL>
 * For convenience, this class extends javax.swing.tree.DefaultMutableTreeNode
 * because in all the cases so far, it is displayed in a JTree
 * component.
 * <P>
 * This makes it convenient to get information on children
 * objects/nodes as the DefaultMutableTreeNode class has methods 
 * for this.
 *
 */
public abstract class ConsoleObj extends DefaultMutableTreeNode {

    /**
     * Create/initialize the admin explorer GUI component.
     */
    public ConsoleObj() {
    } 

    /** 
     * Return a string representation of this object.
     * This is the string that will be displayed in
     * the explorer pane JTree node.
     *
     * @return String representation of this object/node.
     */
    public String toString()  {
	return (getExplorerLabel());
    }

    /**
     * Returns the popup menu to display for this object.
     * Uses the getExplorerPopupMenuItemMask() method to
     * determine what menu items should be displayed in the
     * popup.
     *
     * @param actionMgr The action manager to use to fetch
     *			the actions used in constructing the
     *			popup menu.
     * @return The popup menu to display for this object.
     */
    public JPopupMenu getExporerPopupMenu(ActionManager actionMgr)  {
	JPopupMenu popup;
	JMenuItem menuItem;
	String	label = this.toString();
	int		mask = getExplorerPopupMenuItemMask();
				
	popup = new JPopupMenu(label);
	popup.setLabel(label);
							 
	checkActionFlag(actionMgr, popup, mask, ActionManager.CONNECT);
	checkActionFlag(actionMgr, popup, mask, ActionManager.DISCONNECT);
	checkActionFlag(actionMgr, popup, mask, ActionManager.QUERY_BROKER);
	checkActionFlag(actionMgr, popup, mask, ActionManager.PAUSE);
	checkActionFlag(actionMgr, popup, mask, ActionManager.RESUME);
	checkActionFlag(actionMgr, popup, mask, ActionManager.RESTART);
	checkActionFlag(actionMgr, popup, mask, ActionManager.SHUTDOWN);
	checkActionFlag(actionMgr, popup, mask, ActionManager.ADD);
	checkActionFlag(actionMgr, popup, mask, ActionManager.DELETE);
	checkActionFlag(actionMgr, popup, mask, ActionManager.PURGE);
	checkActionFlag(actionMgr, popup, mask, ActionManager.PROPERTIES);
	
	return (popup);
    }

    /**
     * Returns true if this object can be inspected, false otherwise.
     *
     * @return true if this object can be inspected, false otherwise.
     */
    public boolean canBeInspected()  {
	if (getInspectorPanelClassName() == null)  {
	    return (false);
	}
	return (true);
    }

    /**
     * Returns the string label associated with the passed action flag
     * relevant to this object.
     * <P>
     * Some actions like ADD have different meanings depending on the currently
     * selected object. This method is used to obtain the label for the
     * menu item label or for toolbar tooltip for the passed action when
     * this particular object is selected.
     * <P>
     * Objects interested in displaying a particular label for some action
     * should override this method.
     * <P>
     * A return of null would mean that the label would not be changed.
     * The currently displayed would be used.
     * <P>
     * The forMenu parameter indicates if this label will be used in a menu.
     * This gives the ConsoleObj implementation of returning different
     * strings depending on the situation. For example in Asian locales,
     * the menu lables contain characters that are solely for mnemonics
     * e.g. "XXX (A)" which would look awkward in anything other than a
     * menu item.
     *
     * @see ActionManager
     * @see AMenuBar
     * @see AToolBar
     *
     * @return Action label for the passed action.
     *
     */
    public String getActionLabel(int actionFlag, boolean forMenu)  {
	return (null);
    }

    /**
     * Returns the action icon associated with the passed action flag
     * relevant to this object.
     * <P>
     * Some actions like CONNECT have different icons associated to it
     * depending on the currently selected object. 
     * This method is used to obtain the icon for the menu item or for 
     * toolbar button for the passed action when this particular object
     * is selected.
     * <P>
     * Objects interested in displaying a particular icon in the toolbar button
     * or menu item for some action should override this method.
     * <P>
     * A return of null would mean that the label would not be changed.
     * The currently displayed would be used.
     *
     * @see ActionManager
     * @see AMenuBar
     * @see AToolBar
     *
     * @return Action icon for the passed action.
     *
     */
    public ImageIcon getActionIcon(int actionFlag)  {
	return (null);
    }

    /*
     * Checks if a particular action flag is set in the specified mask
     * and adds the corresponding action to the passed popup menu if it is.
     */
    private void checkActionFlag(ActionManager actionMgr, JPopupMenu popup, 
					int mask, int actionFlag)  {
	if (flagSet(mask, actionFlag))  {
	    JMenuItem item = popup.add(actionMgr.getAction(actionFlag));

	    /*
	     * Check if there is a mnemonic associated with this action.
	     * If there is, set it on the menu item
	     */
	    char m = actionMgr.getCharMnemonic(actionFlag);
	    if ((m != (char)0))  {
		item.setMnemonic(m);
	    }

	    /*
	     * Check if the ConsoleObj has a specific label to use for this
	     * action.
	     */
	    String label = getActionLabel(actionFlag, true);

	    if (label != null)  {
		item.setText(label);
	    }

	    /*
	     * Check if the ConsoleObj has a specific icon to use for this
	     * action.
	     */
	    ImageIcon icon = getActionIcon(actionFlag);
	    if (icon != null)  {
		item.setIcon(icon);
	    }
	}
    }

    /*
     * Returns whether a action flag is set in
     * the specified mask.
     */
    private boolean flagSet(int mask, int actionFlag)  {
	return ((mask & actionFlag) == actionFlag);
    }


    /**
     * Returns the label displayed in the explorer pane for
     * this object. This is the label for the JTree node.
     *
     * @return label used in explorer JTree node
     */
    public abstract String getExplorerLabel();

    /**
     * Returns the tooltip displayed in the explorer pane for
     * this object.
     *
     * @return tooltip displayed in explorer JTree node for
     *		this object.
     */
    public abstract String getExplorerToolTip();

    /**
     * Returns the ImageIcon used for this object in the explorer
     * pane JTree node.
     *
     * @return the ImageIcon used for this object in the explorer
     * 		pane JTree node.
     */
    public abstract ImageIcon getExplorerIcon();

    /**
     * Returns the bit mask representing the menu items to display
     * for this object in the explorer pane.
     *
     * @return the bit mask representing the menu items to display
     *		for this object in the explorer pane.
     */
    public abstract int getExplorerPopupMenuItemMask();


    /**
     * Returns the bit mask representing the actions to activate
     * when this object is selected.
     *
     * @return the bit mask representing the actions to activate
     * 		when this object is selected.
     */
    public abstract int getActiveActions();


    /**
     * Returns the classname for the inspector panel used to inspect
     * this object.
     *
     * @return the classname for the inspector panel used to inspect
     *		this object.
     */
    public abstract String getInspectorPanelClassName();

    /**
     * Returns the identifier for the inspector panel used to inspect
     * this class. This is primarily used to identify the inspector
     * panel in a CardLayout.
     *
     * @return the identifier for the inspector panel used to inspect
     * this class. 
     */
    public abstract String getInspectorPanelId();

    /**
     * Returns a string that can be used as a header for the inspector
     * panel.
     *
     * @return the string that can be used as a header for the inspector
     *			panel.
     */
    public abstract String getInspectorPanelHeader();
}
