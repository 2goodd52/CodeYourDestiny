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
        
    /**
     * Sets the code area for this Game
     * @param codeArea The CodeTextAreaWrapper for this game
     */
    public void setCodeArea(final CodeTextAreaWrapper codeArea)
    {
        this.codeArea = codeArea;
    }
    
    /**
     * Adds the input String to the method body of 'GameMemoryClass' and compiles
     * @param userCode The code to add to the method body
     * @return Diagnostic containing error information, if an error is thrown only.
     */
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
    
    /**
     * Attempts to invoke the users generated class
     * @return true if the code is invoked without error
     */
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
    
    /**
     * @return the GameMemoryClass associated with this Game
     */
    public GameMemoryClass getGeneratedClass()
    {
        return gameClass;
    }
    
    /**
     * @return the GamePanel associated with this class, or a new instance if not valid
     */
    public GamePanel getGamePanel()
    {
        if(gamePanel == null)
            gamePanel = new GamePanel(this);
        return gamePanel;
    }
    
    /**
     * Sets the current line of code being run
     * @param line The line number of the code to highlight
     */
    public void updateRunningLine(final int line)
    {
        currentCodeLine = line;
        codeArea.setRunningOnLine(line);
    }
    
    /**
     * Displays a red highlight on the current running code line
     */
    public void displayError()
    {
        codeArea.setErrorOnLine(currentCodeLine);
    }
    
    /**
     * @return true if the users code is still being invoked
     */
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
    
    /**
     * @return the current level number associated with this Game
     */
    public int getLevelNumber()
    {
        return level;
    }
    
    /**
     * Sets the current level number associated with this Game
     * @param level the current level number
     */
    public void setLevelNumber(final int level)
    {
        this.level = level;
    }
    
    public abstract Level getLevel();
    
    public abstract Level[] getLevels();
    
    public abstract void update();
    
    public abstract void render(final Graphics g);
    
}
