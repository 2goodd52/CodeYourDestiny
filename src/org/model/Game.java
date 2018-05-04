/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

import java.awt.Color;
import java.awt.Graphics;
import javax.tools.Diagnostic;
import org.view.components.CodeTextAreaWrapper;
import org.view.components.GamePanel;

/**
 *
 * @author Dean
 */
public abstract class Game {
    
    private GameMemoryClass gameClass = new GameMemoryClass();
    private CodeTextAreaWrapper codeArea;
    private boolean runningUserCode;
    private int currentCodeLine = 0;
    
    private GamePanel gamePanel;
    
    private Level currentLevel;
    private int level = 1;
    

    public Game()
    {
        gameClass.className = "GameSolution";
    }
        
    public void setCodeArea(final CodeTextAreaWrapper codeArea)
    {
        this.codeArea = codeArea;
    }
    
    public Diagnostic compileUserCode(final String userCode)
    {
        gameClass.clearMethodCode();
        final String[] codeLines = userCode.split("\n");
        for (final String line : codeLines)
        {
            gameClass.addMethodCode(line);
        }
        if(gameClass.compile())
        {
            return null;
        } else
        {
            for (final Diagnostic diagnostic : gameClass.getDiagnostics())
            {
                return diagnostic;
            }
        }
        return null;
    }
    
    public boolean invokeUserCode()
    {
        try {
            new Thread(new Runnable() {
                public void run()
                {
                    runningUserCode = true;
                    gameClass.invokeMethod();
                    runningUserCode = false;
                }
            }).start();
        } catch(final Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public GameMemoryClass getGeneratedClass()
    {
        return gameClass;
    }
    
    public GamePanel getGamePanel()
    {
        if(gamePanel == null)
            gamePanel = new GamePanel(this);
        return gamePanel;
    }
    
    public void updateRunningLine(final int line)
    {
        currentCodeLine = line;
        codeArea.setRunningOnLine(line);
    }
    
    public void displayError()
    {
        codeArea.setErrorOnLine(currentCodeLine);
    }
    
    public boolean isRunningUserCode()
    {
        return runningUserCode;
    }
    
    public void reset()
    {
        
    }
    
    public void finish()
    {
        
    }
    
    public int getLevelNumber()
    {
        return level;
    }
    
    public void setLevelNumber(final int level)
    {
        this.level = level;
    }
    
    public abstract Level getLevel();
    
    public abstract Level[] getLevels();
    
    public abstract void update();
    
    public abstract void render(final Graphics g);
    
}
