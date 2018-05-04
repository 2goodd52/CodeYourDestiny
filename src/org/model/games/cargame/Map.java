/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.games.cargame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Dean
 */
public class Map {
   
    private Tile[] tiles;
    private int width;
    private int height;
    private Tile finishTile;
    
    public Map(final int width, final int height, final int[] tileArray)
    {
        this.tiles = new Tile[width * height];
        this.width = width;
        this.height = height;
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                tiles[x + y * height] = new Tile(x, y, tileArray[x + y * height]);
                if(tileArray[x + y * height] >= 6 && tileArray[x + y * height] <= 10)
                    finishTile = tiles[x + y * height];
            }
        }
        
    }
    
    public Tile getTileAt(final int x, final int y)
    {
        return tiles[x + y * height];
    }

    public Tile getFinishTile()
    {
        return finishTile;
    }
    
    public void render(final Graphics g)
    {
        for (final Tile t : tiles)
        {
            t.render(g);
        }
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if(tiles[x + y * height].getType() != 1)
                {
                    g.setColor(new Color(0, 0, 0, 50));
                    g.drawRect(x * 48, y * 48, 48, 48);
                }
            }
        }
    }
    
}
