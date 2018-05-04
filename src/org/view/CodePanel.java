/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.tools.Diagnostic;
import org.model.Game;
import org.view.components.CodeTextAreaWrapper;
import org.view.components.InfoPanel;
import org.view.components.MethodsPanel;
import org.view.components.TitleBar;

/**
 *
 * @author Dean
 */
public class CodePanel extends JPanel {
 
    public Game game;
    public CodeTextAreaWrapper codeArea;
    public MethodsPanel methodsPanel;
    public InfoPanel infoPanel;
    
    public CodePanel(final Game game)
    {
        super();
        this.game = game;
        this.methodsPanel = new MethodsPanel(game);
        this.infoPanel = new InfoPanel();
        this.infoPanel.setLevel(game);
        this.methodsPanel.setAvailable(game.getGeneratedClass().getVariables());
        this.codeArea = new CodeTextAreaWrapper(game);
        this.game.setCodeArea(codeArea);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        add(infoPanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        add(methodsPanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.02;
        add(new TitleBar("Your code:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.6;
        add(codeArea, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 0.05;
        JButton compile = new JButton("Compile and run");
        compile.addActionListener(new ActionListener() {
            
            long lastClick = 0;
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if((System.currentTimeMillis() - lastClick) < 500)
                    return;
                lastClick = System.currentTimeMillis();
                compile.setEnabled(false);
                compile.setText("Compiling...");
                Diagnostic diagnostic = game.compileUserCode(codeArea.getText());
                if(diagnostic == null)
                {
                    compile.setText("Running...");
                    game.reset();
                    game.invokeUserCode();
                    new Thread(new Runnable() {

                        @Override
                        public void run()
                        {
                            while (game.isRunningUserCode())
                            {
                                try {
                                    Thread.sleep(100);
                                } catch(final InterruptedException ie) {
                                    ie.printStackTrace();
                                }
                            }
                            game.finish();
                            compile.setEnabled(true);
                            compile.setText("Compile and run");
                        }
                    }).start();
                    return;
                }
                compile.setText("Compile and run");
                compile.setEnabled(true);
                codeArea.setErrorOnLine(game.getGeneratedClass().toSource().split("\n")[(int) diagnostic.getLineNumber() - 1], diagnostic);
            }
            
        });
        compile.setAlignmentX(Component.CENTER_ALIGNMENT);
        compile.setBackground(new Color(228, 221, 205));
        add(compile, gbc);
    }
    
}
