/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.model.Game;
import org.model.interfaces.SolutionMethod;

/**
 *
 * @author Dean
 */
public class MethodsPanel extends JPanel {
    
    private JLabel title = new JLabel("Useful Methods");
    private Game game;
    
    public MethodsPanel(final Game game)
    {
        super();
        this.game = game;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(10, 20, 20, 0)));
        setBackground(new Color(228, 221, 205));
        title.setForeground(new Color(54, 45, 34));
        title.setOpaque(true);
        title.setBackground(new Color(197, 187, 163));
        title.setFont(title.getFont().deriveFont(20f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        title.setToolTipText("Useful methods that can help you complete this level.");
        add(title);
        add(new JLabel(" "));
    }
    
    public void setAvailable(final Object... objects)
    {
        removeAll();
        add(title);
        add(new JLabel(" "));
        for (final Object object : objects)
        {
            final Class<?> includedClass = object.getClass();
            final Method[] methods = includedClass.getDeclaredMethods();
            for (final Method method : methods)
            {
                if(method.isAnnotationPresent(SolutionMethod.class))
                {
                    Annotation annotation = method.getAnnotation(SolutionMethod.class);
                    SolutionMethod solution = (SolutionMethod) annotation;
                    if(game.getLevelNumber() < solution.level())
                        continue;
                    JLabel label = new JLabel(includedClass.getSimpleName().toLowerCase() + "." + method.getName() + "();", SwingConstants.CENTER);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    label.setToolTipText("<html><center><h3><font color=rgb(100,100,100)>" + includedClass.getSimpleName().toLowerCase() + "." + method.getName() + "();<h4>" + solution.methodInfo() +
                                     (solution.hasImage() ? "<br><br><img src=\"" + getClass().getResource("/images/" + method.getName() + ".png") + "\">" : ""));
                    
                    Dimension d = new Dimension(200, 30);
                    
                    label.setPreferredSize(d);
                    label.setMinimumSize(d);
                    label.setSize(d);
                    label.setMaximumSize(d);
                    label.setBorder(BorderFactory.createEtchedBorder());
                    label.setBackground(new Color(197, 187, 163));
                    label.setOpaque(true);
                    add(label);
                    add(createSpacer());
                }
            }
        }
    }
    
    private JLabel createSpacer()
    {
        Dimension d = new Dimension(100, 5);
        final JLabel label = new JLabel();
        label.setPreferredSize(d);
        label.setMinimumSize(d);
        label.setSize(d);
        label.setMaximumSize(d);
        return label;
    }
    
}
