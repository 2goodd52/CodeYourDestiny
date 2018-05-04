/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.model.Game;
import org.model.Level;
import org.model.interfaces.GameInformation;

/**
 *
 * @author Dean
 */
public class GameList extends JPanel {
    
    public GameList(final Game... games)
    {
        super();
        setBackground(new Color(228, 221, 205));
        setBorder(BorderFactory.createEtchedBorder());
        for (final Game game : games)
            add(createGame(game));
        
    }
    
    private JPanel createGame(final Game game)
    {
        final JPanel container = createContainer();
        Annotation annotation = game.getClass().getDeclaredAnnotation(GameInformation.class);
        if(annotation != null)
        {
            final GameInformation gameInformation = (GameInformation) annotation;
            container.add(createTitle(gameInformation.gameName()));
        }
        for (final Level level : game.getLevels())
        {
            container.add(createLevel(game, level));
        }
        return container;
    }
    
    private JPanel createContainer()
    {
        JPanel gameContainer = new JPanel();
        gameContainer.setBorder(BorderFactory.createEtchedBorder());
        gameContainer.setLayout(new BoxLayout(gameContainer, BoxLayout.Y_AXIS));
        return gameContainer;
    }
    
    private JPanel createTitle(final String name)
    {
        JPanel title = new JPanel();
        title.setAlignmentX(0);
        JLabel titleText = new JLabel(name);
        titleText.setFont(titleText.getFont().deriveFont(20f));
        titleText.setAlignmentX(0);
        title.setBackground(new Color(197, 187, 163));
        title.add(titleText);
        return title;
    }
    
    private JPanel createLevel(final Game game, final Level level)
    {
        JPanel levelPanel = new JPanel();
        levelPanel.setAlignmentX(0);
        levelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.X_AXIS));
        levelPanel.setBackground(new Color(228, 221, 205));
        JLabel levelDetails = new JLabel();
        levelDetails.setText("<html><body style='width:180px'><u>Level " + level.getNumber() + "</u><br>" + level.getDescription());
        levelPanel.add(levelDetails);
        final JButton playButton = new JButton("Play");
        playButton.setBackground(new Color(197, 187, 163));
        //if(level.getNumber() > 1 && !game.getLevels()[level.getNumber() - 2].isCompleted())
            //playButton.setEnabled(false);
        playButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                game.setLevelNumber(level.getNumber());
                UserInterface.getSingleton().setGame(game);
            }
            
        });
        levelPanel.add(playButton);
        levelPanel.setFocusable(false);
        return levelPanel;
    }
    
}
