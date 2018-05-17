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
    
    /**
     * Sets the location of the entity in the grid
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     */
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
    
    /**
     * @return true if an error has been declared by this Entity
     */
    public boolean hasThrownError()
    {
        return errorThrown;
    }
    
    /**
     * @return the error message associated with this Entity
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    /**
     * Returns whether or not the Entity has collided with a specific Map tile
     * @param map The map the entity is within
     * @return Returns whether or not the Entity has collided with a specific Map tile
     */
    public boolean hasCollided(final Map map)
    {
        return false;
    }
    
}
