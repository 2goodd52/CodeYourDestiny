/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.games.cargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.imageio.ImageIO;
import org.model.Game;
import org.model.interfaces.GameInformation;
import org.view.UserInterface;

/**
 *
 * @author Dean
 */
@GameInformation(gameName = "Parking problems", gameSummary = "Try and park the car in different scenarios.")
public class CarGame extends Game {
    
    private Car car = new Car();
    private ParkingLevel currentLevel;
    
    private UserMessage userMessage;
    
    /**
     * Creates a new instance of this game, and adds variables to the GameMemoryClass object
     */
    public CarGame()
    {
        super();
        setLevelNumber(1);
        getGeneratedClass().addVariables(this, car);
    }
    
    @Override
    public void update()
    {
        if(userMessage != null)
            return;
        if(car.hasThrownError())
            return;
        car.move(currentLevel.getMap());
    }
    
    @Override
    public void render(final Graphics g)
    {
        if(currentLevel.getMap() != null)
            currentLevel.getMap().render(g);
        if(car != null)
            car.render(g);
        if(userMessage != null)
            userMessage.render(g, 240, 240);
    }
    
    @Override
    public void reset()
    {
        car.setLocation(currentLevel.getStartX(), currentLevel.getStartY(), currentLevel.getStartAngle());
        userMessage = null;
    }
    
    @Override
    public void finish()
    {
        if(car.hasThrownError())
        {
            displayError();
            userMessage = new UserMessage();
            userMessage.color = Color.RED;
            userMessage.title = "Unlucky!";
            userMessage.message = "You crashed the car!";
        } else
        if(car.tileX == currentLevel.getMap().getFinishTile().getX() && car.tileY == currentLevel.getMap().getFinishTile().getY() && ((int) car.angle) == currentLevel.getFinishAngle())
        {
            userMessage = new UserMessage();
            userMessage.color = new Color(0, 100, 0);
            userMessage.title = "Congratulations";
            userMessage.message = "You have completed the level.";
            currentLevel.setCompleted(true);
        } else
        {
            userMessage = new UserMessage();
            userMessage.color = new Color(100, 0, 0);
            userMessage.title = "Failed!";
            userMessage.message = "You failed to park correctly!";
        }
        UserInterface.getSingleton().resetGamePanel(true);
    }
    
    @Override
    public void setLevelNumber(final int level)
    {
        if(level > LEVELS.length)
            return;
        super.setLevelNumber(level);
        currentLevel = LEVELS[level - 1];
        reset();
    } 
    
    private static final ParkingLevel[] LEVELS = {
        new ParkingLevel(1, 5, 5, 0, 0, new int[] {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 0
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 1
            1, 1, 1, 2, 2, 6, 2, 1, 1, 1, // Row 2
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 3
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 4
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 5
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 6
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 7
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 8
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // Row 9
        }, "Simply park the car <font color=red>forward</font> in the highlighted space."),
        new ParkingLevel(2, 5, 4, 0, 0, new int[] {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 0
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 1
            1, 1, 1, 2, 2, 2, 2, 1, 1, 1, // Row 2
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 3
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 4
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1, // Row 5
            1, 1, 1, 4, 4, 8, 4, 1, 1, 1, // Row 6
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 7
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 8
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // Row 9
        }, "In this level you must <font color=red>reverse</font> the car into the highlighted space."),
        new ParkingLevel(3, 5, 5, 0, 270, new int[] {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 0
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 1
            1, 1, 1, 2, 2, 2, 2, 2, 1, 1, // Row 2
            1, 1, 9, 0, 0, 0, 0, 0, 1, 1, // Row 3
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 4
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 5
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 6
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 7
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 8
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // Row 9
        }, "In this level you must park the car <font color=red>forward</font> in the highlighted space."),
        new ParkingLevel(4, 5, 5, 0, 180, new int[] {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 0
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 1
            1, 1, 1, 2, 2, 2, 6, 2, 1, 1, // Row 2
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 3
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 4
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 5
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 6
            1, 1, 5, 0, 0, 0, 0, 0, 1, 1, // Row 7
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 8
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // Row 9
        }, "In this level you must park the car <font color=red>backwards</font> in the highlighted space."),
        new ParkingLevel(5, 8, 8, 270, 270, new int[] {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // Row 0
            1, 1, 9, 0, 0, 0, 0, 0, 0, 1, // Row 1
            1, 1, 5, 0, 0, 0, 0, 0, 0, 1, // Row 2
            1, 1, 1, 1, 1, 1, 1, 0, 0, 1, // Row 3
            1, 1, 1, 1, 1, 1, 0, 0, 0, 1, // Row 4
            1, 1, 0, 0, 0, 0, 0, 0, 0, 1, // Row 5
            1, 1, 0, 0, 1, 1, 1, 1, 1, 1, // Row 6
            1, 1, 0, 0, 1, 1, 1, 1, 1, 1, // Row 7
            1, 1, 0, 0, 0, 0, 0, 0, 0, 1, // Row 8
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // Row 9
        }, "In this level you must park the car <font color=red>forwards</font> in the highlighted space."),
    };
    
    @Override
    public ParkingLevel getLevel()
    {
        return currentLevel;
    }
    
    @Override
    public ParkingLevel[] getLevels()
    {
        return LEVELS;
    }
    
    class UserMessage {
        
        String title;
        String message;
        Color color;
        BufferedImage image;
        
        public void render(final Graphics g, int x, int y)
        {
            if(image == null)
            {
                try {
                    image = ImageIO.read(getClass().getResource("/images/car_info.png"));
                } catch(final Exception e) {
                    System.out.println("Unable too load image");
                }
                return;
            }
            final Font titleFont = g.getFont().deriveFont(20f).deriveFont(Font.BOLD);
            final Font messageFont = g.getFont().deriveFont(15f);
            g.drawImage(image, x - 115, y - 40, null);
            g.setColor(color);
            g.setFont(titleFont);
            g.drawString(title, x - (g.getFontMetrics().stringWidth(title) / 2), y - 5);
            g.setFont(messageFont);
            g.drawString(message, x - (g.getFontMetrics().stringWidth(message) / 2), y + 15);
            
        }
        
    }
    
    
    
}
