/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

/**
 *
 * @author Dean
 */
public abstract class Level {

    protected String description;
    protected boolean completed;
    protected int levelNumber;

    public Level(final String description, final int levelNumber)
    {
        this.description = description;
        this.levelNumber = levelNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setCompleted(final boolean completed)
    {
        this.completed = completed;
    }

    public boolean isCompleted()
    {
        return completed;
    }
    
    public int getNumber()
    {
        return levelNumber;
    }
    
}
