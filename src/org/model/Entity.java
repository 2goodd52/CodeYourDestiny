/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

import org.model.games.cargame.Map;

/**
 *
 * @author Dean
 */
public abstract class Entity {
    
    public int tileX;
    public int tileY;
    
    public int destinationX;
    public int destinationY;
    
    public int pixelX;
    public int pixelY;
    
    public double angle;
    
    protected boolean errorThrown;
    protected String errorMessage;
    
    public void setLocation(final int x, final int y)
    {
        this.tileX = x;
        this.tileY = y;
        this.destinationX = x;
        this.destinationY = y;
        this.pixelX = tileX * 48;
        this.pixelY = tileY * 48;
        this.errorThrown = false;
    }
    
    public boolean hasThrownError()
    {
        return errorThrown;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public boolean hasCollided(final Map map)
    {
        return false;
    }
    
}
