/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.model.Game;
import org.model.games.cargame.CarGame;
import org.view.components.GameContainer;

/**
 *
 * @author Dean
 */
public class UserInterface extends JFrame {
    
    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;
    
    public static UserInterface singleton;
    private Game game = new CarGame();
    
    private GameContainer gamePanel;
    private CodePanel codePanel;
    
    private HomePanel homePanel;
    private GameList gameList;
    
    private JPanel leftPanel = new JPanel() {     
        
        @Override
        public void setSize(final Dimension dimensions)
        {
            super.setSize(dimensions);
            setPreferredSize(dimensions);
            setMinimumSize(dimensions);
            setMaximumSize(dimensions);
        }
            
        
    };
    private JPanel rightPanel = new JPanel() {
        
        @Override
        public void setSize(final Dimension dimensions)
        {
            super.setSize(dimensions);
            setPreferredSize(dimensions);
            setMinimumSize(dimensions);
            setMaximumSize(dimensions);
        }
            
        
    };
    
    public UserInterface()
    {
        setTitle("Code Your Destiny!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        try {
            setIconImage(ImageIO.read(getClass().getResource("/images/icon.png")));
        } catch (IOException ex) {
            System.out.println("Unable to set window Icon.");
        }
       
        setLayout(new BorderLayout());
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
        
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        container.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                Dimension d = getSize();
                Dimension left = new Dimension((int) ((double) d.width * 0.7), d.height);
                Dimension right = new Dimension((int) ((double) d.width * 0.285), d.height);
                leftPanel.setSize(left);
                rightPanel.setSize(right);
                leftPanel.invalidate();
                rightPanel.invalidate();
                container.validate();
                container.repaint();
                
            }
        });
        
        resetHomePanel();
        container.add(leftPanel);
        
        resetGameList(true);
        container.add(rightPanel);
        
        add(container, BorderLayout.CENTER);
    }
    
    /**
     * Sets the left panel to the game container
     * @param restart true if the gamePanel instance is to be recreated
     */
    public final void resetGamePanel(final boolean restart)
    {
        leftPanel.removeAll();
        if(gamePanel == null || restart)
            gamePanel = new GameContainer(game);
        leftPanel.add(gamePanel);
        validate();
        repaint();
    }
    
    /**
     * Sets the right panel to the code container
     * @param restart true if the codePanel instance is to be recreated
     */
    public final void resetCodePanel(final boolean restart)
    {
        rightPanel.removeAll();
        if(codePanel == null || restart) 
            codePanel = new CodePanel(game);
        rightPanel.add(codePanel);
        validate();
        repaint();
    }
    
    public final void resetHomePanel()
    {
        leftPanel.removeAll();
        if(homePanel == null)
            homePanel = new HomePanel();
        leftPanel.add(homePanel);
        validate();
        repaint();
    }
    
    public final void resetGameList(final boolean restart)
    {
        rightPanel.removeAll();
        if(gameList == null || restart)
            gameList = new GameList(game);
        rightPanel.add(gameList);
        validate();
        repaint();
    }
    
    public final void setGame(final Game game)
    {
        this.game = game;
        resetGamePanel(true);
        resetCodePanel(true);
    }
    
    public final void setHome()
    {
        resetHomePanel();
        resetGameList(true);
    }
    
    /**
     * Gets the Singleton instance of this class
     * @return The singleton instance of the class
     */
    public static UserInterface getSingleton()
    {
        if(singleton == null)
            singleton = new UserInterface();
        return singleton;
    }
    
    public static void main(final String[] args) throws IOException
    {
        EventQueue.invokeLater(() -> {
                UserInterface.getSingleton().setVisible(true);
        });
    }
    
}
