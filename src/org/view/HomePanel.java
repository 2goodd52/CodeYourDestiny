/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dean
 */
public class HomePanel extends JPanel {
    
    private BufferedImage background;
    
    public HomePanel()
    {
        super();
        try {
            background = ImageIO.read(getClass().getResource("/images/home.png"));
        } catch(final Exception e) {
            System.out.println("Could not load background image.");
        }
    }
    
    @Override
    public void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
    
}
