/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.games.cargame;

import org.model.Level;

/**
 *
 * @author Dean
 */
public class ParkingLevel extends Level {
    
    private final Map map;
    private final int startX;
    private final int startY;
    private final int startAngle;
    private final int finishAngle;

    public ParkingLevel(final int number, final int startX, final int startY, final int startAngle, final int finishAngle, final int[] MAP_TILES, final String description)
    {
        super(description, number);
        this.map = new Map(10, 10, MAP_TILES);
        this.startX = startX;
        this.startY = startY;
        this.startAngle = startAngle;
        this.finishAngle = finishAngle;
    }

    public Map getMap()
    {
        return map;
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public int getStartAngle()
    {
        return startAngle;
    }
    
    public int getFinishAngle()
    {
        return finishAngle;
    }
    
}
