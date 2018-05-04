/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.lang.annotation.Annotation;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.model.Game;
import org.model.interfaces.GameInformation;

/**
 *
 * @author Dean
 */
public class GameInfo extends JPanel {
    
    private JLabel gameTitle;
    private JLabel gameLevel;
    private JLabel levelDescription;
    
    public GameInfo(final Game game)
    {
        super();
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(197, 187, 163));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Annotation annotation = game.getClass().getDeclaredAnnotation(GameInformation.class);
        if(annotation != null)
        {
            final GameInformation gameInformation = (GameInformation) annotation;
            levelDescription = new JLabel("<html><center><h2>" + gameInformation.gameName() + "</h2>Level: <font color=red>" + game.getLevelNumber() + "</font><br><br><p>" + game.getLevel().getDescription() + "</p>", SwingConstants.CENTER);
            levelDescription.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
            add(levelDescription);
        }
    }
    
}
