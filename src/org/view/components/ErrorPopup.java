/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Dean
 */
public class ErrorPopup extends JPopupMenu {
    
    private JLabel errorMessage = new JLabel("<html><center><font color=red>Error");
    
    public ErrorPopup()
    {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        setBackground(new Color(255, 0, 0, 30));
        add(errorMessage);
    }
    
    public void showError(final Component container, final String message, final int x, final int y)
    {
        errorMessage.setText(message);
        if(!isVisible())
        {
            this.show(container, x, y);
        }
    }
    
    public void close()
    {
        if(isVisible())
            setVisible(false);
    }
    
}
