/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.games.cargame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Dean
 */
public class Tile {
    
    private int gridX;
    private int gridY;
    private int tileType;
    private BufferedImage image;
    
    public Tile(final int gridX, final int gridY, final int imageIndex)
    {
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileType = imageIndex;
        try {
            this.image = ImageIO.read(getClass().getResource("/images/" + TILE_NAMES[imageIndex] + ".png"));
        } catch (IOException ex) {
            System.out.println("Unable to load image.");
        }
    }
    
    public void render(final Graphics g)
    {
        if(image != null)
            g.drawImage(image, gridX * 48, gridY * 48, null);
    }
    
    public int getX()
    {
        return gridX;
    }
    
    public int getY()
    {
        return gridY;
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(gridX * 48, gridY * 48, 48, 48);
    }
    
    public int getType()
    {
        return tileType;
    }
    
    private static final String[] TILE_NAMES = {
        "floor_road",
        "floor_wall",
        "floor_space_north",
        "floor_space_east",
        "floor_space_south",
        "floor_space_west",
        "floor_space_north_target",
        "floor_space_east_target",
        "floor_space_south_target",
        "floor_space_west_target"
    };
    
}
