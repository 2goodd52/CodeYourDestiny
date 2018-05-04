/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.games.cargame;

import org.model.Entity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.model.interfaces.SolutionMethod;

/**
 *
 * @author Dean
 */
public class Car extends Entity {
    
    private double destinationAngle;
    private int turnDirection;
    
    private BufferedImage image;
    
    private CountDownLatch latch = new CountDownLatch(0);
    
    public Car()
    {
        try {
            angle = 0;
            image = ImageIO.read(getClass().getResource("/images/car.png"));
        } catch (IOException ex) {
            System.out.println("Could not load image resource.");
        }
    }
    
    public void render(final Graphics g)
    {
        if(image == null)
            return;
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        int x = pixelX;
        int y = pixelY;
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians((int) angle), x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }
    
    /**
     * Rotates the entities image and draws into a new BufferedImage
     * @return the rotated BufferedImage instance.
     */
    public BufferedImage getRotatedImage()
    {
        BufferedImage rotated = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        
        int x = 0;
        int y = 0;
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians((int) angle), x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.dispose();
        return rotated;
    }
    
    public Point getClosestPixel(final int direction)
    {
        final BufferedImage rotated = getRotatedImage();
        switch(direction)
        {
            case 0: //North
            {
                for (int y = 0; y < rotated.getHeight(); y++)
                {
                    for (int x = 0; x < rotated.getWidth(); x++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                            return new Point(x, y);
                    }
                }
                break;
            }
            
            case 1:
            {
                int highestX = -1;
                int lowestY = 48;
                for (int x = rotated.getWidth() - 1; x > 0; x--)
                {
                    for (int y = 0; y < rotated.getHeight(); y++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                        {
                            if(x > highestX)
                                highestX = x;
                            if(y < lowestY)
                                lowestY = y;
                        }
                    }
                }
                return highestX > -1 && lowestY < 48 ? new Point(highestX, lowestY) : new Point(0, 0);
            }
            
            case 2:
            {
                for (int x = rotated.getWidth() - 1; x > 0; x--)
                {
                    for (int y = 0; y < rotated.getHeight(); y++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                            return new Point(x, y);
                    }
                }
            }
            
            case 3:
            {
                int highestX = -1;
                int highestY = -1;
                for (int x = rotated.getWidth() - 1; x > 0; x--)
                {
                    for (int y = rotated.getHeight() - 1; y > 0; y--)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                        {
                            if(x > highestX)
                                highestX = x;
                            if(y > highestY)
                                highestY = y;
                        }
                    }
                }
                return highestX > -1 && highestY > -1 ? new Point(highestX, highestY) : new Point(0, 0);
            }
            
            case 4:
            {
                for (int y = rotated.getHeight() - 1; y > 0; y--)
                {
                    for (int x = 0; x < rotated.getWidth(); x++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                            return new Point(x, y);
                    }
                }
                break;
            }
            
            case 5:
            {
                int lowestX = 48;
                int highestY = -1;
                for (int x = 0; x < rotated.getWidth(); x++)
                {
                    for (int y = rotated.getHeight() - 1; y > 0; y--)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                        {
                            if(x < lowestX)
                                lowestX = x;
                            if(y > highestY)
                                highestY = y;
                        }
                    }
                }
                return lowestX < 48 && highestY > -1 ? new Point(lowestX, highestY) : new Point(0, 0);
            }
            
            case 6:
            {
                for (int x = 0; x < rotated.getWidth(); x++)
                {
                    for (int y = 0; y < rotated.getHeight(); y++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                            return new Point(x, y);
                    }
                }
                break;
            }
            
            case 7:
            {
                int lowestX = 48;
                int lowestY = 48;
                for (int x = 0; x < rotated.getWidth(); x++)
                {
                    for (int y = 0; y < rotated.getHeight(); y++)
                    {
                        final int pixelColour = rotated.getRGB(x, y);
                        if(pixelColour != 0)
                        {
                            if(x < lowestX)
                                lowestX = x;
                            if(y < lowestY)
                                lowestY = y;
                        }
                    }
                }
                return lowestX < 48 && lowestY < 48 ? new Point(lowestX, lowestY) : new Point(0, 0);
            }
            
        }
        return new Point(0, 0);
    }
    
    public Rectangle getClosestImpact()
    {
        final Point topLeft = getClosestPixel(getDirection());
        if(topLeft.x == 0 && topLeft.y == 0)
        {
            return new Rectangle(0, 0, 0, 0);
        }
        final int rectX = (pixelX) + topLeft.x;
        final int rectY = (pixelY) + topLeft.y;
        return new Rectangle(rectX - 2, rectY - 2, 6, 6);
    }
    
    private int getDirection()
    {
        if(destinationX == tileX && destinationY == tileY)
            return -1;
        
        if(destinationX == tileX)
        {
            return destinationY < tileY ? 0 : 4;
        } 
        if(destinationY == tileY)
        {
            return destinationX > tileX ? 2 : 6;
        }
        if(destinationX > tileX && destinationY < tileY)
            return 1;
        else
        if(destinationX > tileX && destinationY > tileY)
            return 3;
        else
        if(destinationX < tileX && destinationY > tileY)
            return 5;
        else
        if(destinationX < tileX && destinationY < tileY)
            return 7;
        
        return -1;
    }  
    
    @SolutionMethod(methodInfo = "Moves the car forward specific number of squares.", level = 5)
    public void forwardX(final int number) throws ErrorThrownException
    {
        for (int index = 0; index < number; index++)
            forward();
    }
        
    @SolutionMethod(methodInfo = "Moves the car forward a single square.", hasImage = true)
    public void forward() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationY = tileY - 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX + 1;
                break;
            }
            
            case 2:
            {
                destinationY = tileY + 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX - 1;
                break;
            }
        }
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    @SolutionMethod(methodInfo = "Reverses the car forward specific number of squares.", level = 5)
    public void reverseX(final int number) throws ErrorThrownException
    {
        for (int index = 0; index < number; index++)
            reverse();
    }
    
    @SolutionMethod(methodInfo = "Reverses the car a single square.", hasImage = true)
    public void reverse() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationY = tileY + 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX - 1;
                break;
            }
            
            case 2:
            {
                destinationY = tileY - 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX + 1;
                break;
            }
        }
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    @SolutionMethod(methodInfo = "Turns the car to the left, the car will travel forward and left a square.", hasImage = true, level = 3)
    public void turnLeft() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationX = tileX - 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX + 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 2:
            {
                destinationX = tileX + 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX - 1;
                destinationY = tileY + 1;
                break;
            }
        }
        destinationAngle = angle - 90;
        turnDirection = -1;
        if(destinationAngle < 0)
            destinationAngle = destinationAngle + 360;
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    @SolutionMethod(methodInfo = "Turns the car to the right, the car will travel forward and right a square.", hasImage = true, level = 3)
    public void turnRight() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationX = tileX + 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX + 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 2:
            {
                destinationX = tileX - 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX - 1;
                destinationY = tileY - 1;
                break;
            }
        }
        destinationAngle = angle + 90;
        turnDirection = 1;
        if(destinationAngle >= 360)
            destinationAngle = destinationAngle - 360;
        latch = new CountDownLatch(1);
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    @SolutionMethod(methodInfo = "Reverses the car to the left, the car will travel backwards and left a square.", hasImage = true, level = 4)
    public void reverseLeft() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationX = tileX - 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX - 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 2:
            {
                destinationX = tileX + 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX + 1;
                destinationY = tileY + 1;
                break;
            }
        }
        destinationAngle = angle + 90;
        turnDirection = 1;
        if(destinationAngle > 360)
            destinationAngle = destinationAngle - 360;
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    @SolutionMethod(methodInfo = "Reverses the car to the left, the car will travel backwards and left a square.", hasImage = true, level = 4)
    public void reverseRight() throws ErrorThrownException
    {
        switch ((int) angle / 90)
        {
            case 0:
            {
                destinationX = tileX + 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 1:
            {
                destinationX = tileX - 1;
                destinationY = tileY + 1;
                break;
            }
            
            case 2:
            {
                destinationX = tileX - 1;
                destinationY = tileY - 1;
                break;
            }
            
            case 3:
            {
                destinationX = tileX + 1;
                destinationY = tileY - 1;
                break;
            }
        }
        destinationAngle = angle - 90;
        turnDirection = -1;
        if(destinationAngle < 0)
            destinationAngle = destinationAngle + 360;
        latch = new CountDownLatch(1);
        try {
            if(!latch.await(10, TimeUnit.SECONDS))
                throw new ErrorThrownException("Timeout error occured.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(errorThrown)
            throw new ErrorThrownException("Car has crashed!");
    }
    
    public void move(final Map map)
    {
        if(hasCollided(map))
        {
            errorThrown = true;
            latch.countDown();
            return;
        }
        if(destinationX == tileX && destinationY == tileY)
        {
            latch.countDown();
            return;
        }
        if(destinationAngle != angle)
        {
            double step = 90 / 48.0;
            if(Math.abs((int) destinationAngle - (int) angle) <= 2)
            {
                angle = destinationAngle;
            } else
            {
                angle += (step * turnDirection);
                if(angle < 0)
                    angle += 360;
                else
                if(angle > 360)
                    angle -= 360;
            }
        }
        if(destinationX != tileX)
        {
            if(pixelX == (destinationX * 48))
            {
                tileX = destinationX;
            } else
            {
                pixelX += (destinationX > tileX ? 1 : -1);
            }
        }
        if(destinationY != tileY)
        {
            if(pixelY == (destinationY * 48))
            {
                tileY = destinationY;
            } else
                pixelY += (destinationY > tileY ? 1 : -1);
        }
    }
    
    public void setLocation(final int x, final int y, final int angle)
    {
        super.setLocation(x, y);
        this.angle = angle;
        this.destinationAngle = angle;
    }
    
    /**
     * Returns whether or not the car has collided with any wall map tiles
     * @param map The map object for this level
     * @return Whether or not the car has collided with a wall tile.
     */
    @Override
    public boolean hasCollided(final Map map)
    {
        if(destinationX == tileX && destinationY == tileY)
            return false;
        final Tile next = map.getTileAt(destinationX, destinationY);
        if(next.getType() != 1)
            return false;
        if(next.getBounds().intersects(getClosestImpact()))
            return true;
        return false;
    }
    
}
