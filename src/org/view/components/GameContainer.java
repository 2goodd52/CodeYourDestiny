/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.model.Game;
import org.view.UserInterface;

/**
 *
 * @author Dean
 */
public class GameContainer extends JPanel {
    
    public GameContainer(final Game game)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(197, 187, 163));
        
        GamePanel gamePanel = game.getGamePanel();
        gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(new GameInfo(game));
        add(gamePanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(197, 187, 163));
        
        JButton nextButton = new JButton("Next level");
        nextButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                game.setLevelNumber(game.getLevelNumber() + 1);
                UserInterface.getSingleton().resetCodePanel(true);
                UserInterface.getSingleton().resetGamePanel(true);
            }
            
            
        });
        JButton previousButton = new JButton("Previous level");
        previousButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                game.setLevelNumber(game.getLevelNumber() - 1);
                UserInterface.getSingleton().resetCodePanel(true);
                UserInterface.getSingleton().resetGamePanel(true);
            }    
        });
        JButton exitButton = new JButton("Exit Game");
        exitButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the Menu?", "Exit to Menu?",  JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)
                {
                    UserInterface.getSingleton().setHome();
                }
            }
            
        });
        if(!game.getLevel().isCompleted() || game.getLevels().length == game.getLevel().getNumber())
            nextButton.setEnabled(false);
        if(game.getLevelNumber() <= 1)
            previousButton.setEnabled(false);
        exitButton.setBackground(new Color(228, 221, 205));
        previousButton.setBackground(new Color(228, 221, 205));
        nextButton.setBackground(new Color(228, 221, 205));
        buttonPanel.add(exitButton, BorderLayout.WEST);
        buttonPanel.add(previousButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel);
    }
    
}
