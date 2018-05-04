/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import java.lang.annotation.Annotation;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.model.Game;
import org.model.interfaces.GameInformation;

/**
 *
 * @author Dean
 */
public class InfoPanel extends JPanel {
    
    private JLabel textArea;
    private String gameName;
    private int level;
    
    public InfoPanel()
    {
        super();
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(197, 187, 163));
        textArea = new JLabel();
        add(textArea);
    }
    
    public void setLevel(final Game game)
    {
        Annotation annotation = game.getClass().getDeclaredAnnotation(GameInformation.class);
        if(annotation != null)
        {
            final GameInformation gameInformation = (GameInformation) annotation;
            textArea.setText("<html><center><h2>" + gameInformation.gameName() + "</h2><font color=blue>" + gameInformation.gameSummary());
        }
    }
    
}
