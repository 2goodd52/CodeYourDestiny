/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.tools.Diagnostic;
import org.model.Game;
import org.view.CodeMenu;

/**
 *
 * @author Dean
 */
public class CodeTextArea extends JTextArea {
    
    private CodeMenu codeMenu;
    private Game game;
    private ErrorPopup errorMessage;
    private DefaultHighlighter highlighter =  (DefaultHighlighter) getHighlighter();
    private DefaultHighlighter.DefaultHighlightPainter errorPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 0, 0, 150));
    private DefaultHighlighter.DefaultHighlightPainter runningPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(0, 255, 0, 150));
    
    public CodeTextArea(final Game game)
    {
        super();
        setLineWrap(true);
        codeMenu = new CodeMenu(this);
        codeMenu.setEntries(game, game.getGeneratedClass().getVariables());
        errorMessage = new ErrorPopup();
        highlighter.setDrawsLayeredHighlights(false);
        this.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(final KeyEvent event)
            {
                switch (event.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                    {
                        if(codeMenu.selectNext())
                            event.consume();
                        event.consume();
                        break;
                    }
                    
                    case KeyEvent.VK_UP:
                    {
                        if(codeMenu.selectPrevious())
                            event.consume();
                        break;
                    }
                    
                    case KeyEvent.VK_TAB:
                    case KeyEvent.VK_ENTER:
                    {
                        if(codeMenu.isVisible())
                            event.consume();
                        break;
                    }
                    
                }
                highlighter.removeAllHighlights();
                errorMessage.close();
                requestFocusInWindow();
                
            }
            
            @Override
            public void keyReleased(final KeyEvent event)
            {
                
                switch (event.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                    {
                        event.consume();
                        break;
                    }
                    
                    case KeyEvent.VK_UP:
                    {
                        event.consume();
                        break;
                    }
                    
                    case KeyEvent.VK_TAB:
                    case KeyEvent.VK_ENTER:
                    {
                        if(codeMenu.isVisible())
                            replaceInputText();
                        event.consume();
                        break;
                    }
                    
                }
                if(!event.isConsumed())
                    codeMenu.update();
                
                highlighter.removeAllHighlights();
                requestFocusInWindow();
                
            }
        });
    }

    public String getInputText()
    {
        try {
            int caretPosition = getLineOfOffset(getCaretPosition());
            int startOffset = getLineStartOffset(caretPosition);
            int endOffset = getLineEndOffset(caretPosition);
            return getText(startOffset, (endOffset - startOffset));
        } catch(final BadLocationException ble) {
            ble.printStackTrace();
            return null;
        }
    }
    
    public void replaceInputText()
    {
        try {
            int caretPosition = getLineOfOffset(getCaretPosition());
            int startOffset = getLineStartOffset(caretPosition);
            int endOffset = getLineEndOffset(caretPosition);
            
            getDocument().remove(startOffset, endOffset - startOffset);
            getDocument().insertString(startOffset, codeMenu.getSelected().toString(), null);
            
            setCaretPosition(getLineEndOffset(caretPosition));
            
            codeMenu.close();
            
        } catch(final BadLocationException ble) {
            ble.printStackTrace();
        }
    }
    
    public Point getLinePosition()
    {
        try {
            final Rectangle bounds = modelToView(getCaret().getDot());
            return new Point((int) bounds.getX(), (int) bounds.getY() + this.getGraphics().getFontMetrics().getHeight());
        } catch(final Exception e) {
            e.printStackTrace();
        }
        return new Point(0, 0);
    }
    
    /**
     * Highlights the given line and displays the Diagnostic message
     * @param lineNumber The line the users code has an error on
     * @param diagnostic The Diagnostic object containing the error details
     */
    public void setErrorOnLine(final int lineNumber, final Diagnostic diagnostic)
    {
        highlighter.removeAllHighlights();
        try {
            
            int startOffset = getLineStartOffset(lineNumber);
            int endOffset = getLineEndOffset(lineNumber);
            highlighter.addHighlight(startOffset, endOffset, errorPainter);
            setCaretPosition(endOffset);
            
            codeMenu.close();
       
            String message = diagnostic.getMessage(null);
            if(message.contains("\n"))
            {
                final String[] parts = message.split("\n");
                message = "<html>";
                message += parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).trim();
                message += "<br>";
                message += parts[1].trim();
               
                errorMessage.showError(this, message, 5, getLinePosition().y);   
            } else
                errorMessage.showError(this, message, 5, getLinePosition().y);   
            
            requestFocusInWindow();
            
        } catch(final Exception e) {
            System.out.println("Unable to highlight error line.");
        }
    }
    
    public void setRunningOnLine(final int lineNumber)
    {
        highlighter.removeAllHighlights();
        invalidate();
        repaint();
        try {
            
            int startOffset = getLineStartOffset(lineNumber);
            int endOffset = getLineEndOffset(lineNumber);
            highlighter.addHighlight(startOffset, endOffset, runningPainter);
            
        } catch(final Exception e) {
            System.out.println("Unable to highlight error line.");
        }
    }
    
    public void setErrorOnLine(final int lineNumber)
    {
        highlighter.removeAllHighlights();
        invalidate();
        repaint();
        try {
            
            int startOffset = getLineStartOffset(lineNumber);
            int endOffset = getLineEndOffset(lineNumber);
            highlighter.addHighlight(startOffset, endOffset, errorPainter);
            
        } catch(final Exception e) {
            System.out.println("Unable to highlight error line.");
        }
    }
        
     
}
