/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.tools.Diagnostic;
import org.model.Game;

/**
 *
 * @author Dean
 */
public class CodeTextAreaWrapper extends JScrollPane {
    
    private CodeTextArea codeTextArea;
    private JTextArea lineNumberArea;
    private Game game;

    public CodeTextAreaWrapper(final Game game)
    {
        super();
        this.game = game;
        codeTextArea = new CodeTextArea(game);
        codeTextArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        lineNumberArea = new JTextArea(" 1 ");
        lineNumberArea.setBackground(Color.LIGHT_GRAY);
        lineNumberArea.setEditable(false);
        
        codeTextArea.getDocument().addDocumentListener(new DocumentListener() {
            
            public String getText()
            {
                final int caretPosition = codeTextArea.getDocument().getLength();
                final Element root = codeTextArea.getDocument().getDefaultRootElement();
                String lineNumbers = " 1 " + "\n";
                for (int lines = 2; lines < root.getElementIndex(caretPosition) + 2; lines++)
                {
                    lineNumbers += " " + lines + " \n";
                }
                return lineNumbers;
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                lineNumberArea.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                lineNumberArea.setText(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                lineNumberArea.setText(getText());
            }
            
        });
        
        getViewport().add(codeTextArea);
        setRowHeaderView(lineNumberArea);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    public String getText()
    {
        return codeTextArea.getText();
    }
    
    public void setErrorOnLine(final String line, final Diagnostic error)
    {
        final String[] lines = codeTextArea.getText().split("\n");
        if(lines.length < 1)
            return;
        int lineNumber = -1;
        for (int index = 0; index < lines.length; index++)
        {
            if(lines[index].equals(line.trim()))
            {
                lineNumber = index;
                break;
            }
        }
        if(lineNumber == -1)
            return;
        codeTextArea.setErrorOnLine(lineNumber, error);
    }
    
    public void setErrorOnLine(final int lineNumber)
    {
        codeTextArea.setErrorOnLine(lineNumber);
    }
    
    public void setRunningOnLine(final int lineNumber)
    {
        codeTextArea.setRunningOnLine(lineNumber);
    }
    
}
