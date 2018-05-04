/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.model.Game;

/**
 *
 * @author Dean
 */
public class GamePanel extends JPanel {
    
    private Game game;
    
    public GamePanel(final Game game)
    {
        super();
        this.game = game;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setIgnoreRepaint(true);
        try {
            new Thread(new Runnable() {
                
                @Override
                public void run()
                {
                    while (true)
                    {
                        game.update();
                        repaint();
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                
            }).start();
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Renders the game onto this panel
     * @param g The Graphics object associated with this panel.
     */
    @Override
    public void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        game.render(g);
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(480, 480);
    }
    
    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(480, 480);
    }
    
    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(480, 480);
    }
    
    
}
