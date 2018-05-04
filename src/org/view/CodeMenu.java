/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import org.model.interfaces.SolutionMethod;
import org.view.components.CodeTextArea;

/**
 *
 * @author Dean
 */
public class CodeMenu extends JPopupMenu {
    
    private CodeTextArea codeTextArea;
    private Object[] suggestedEntries;
    private JList suggestedList;
    
    public CodeMenu(final CodeTextArea codeTextArea)
    {
        this.codeTextArea = codeTextArea;
        setBorder(null);
        setOpaque(false);
    }
    
    public void setEntries(final Object... objects)
    {
        ArrayList<String> entries = new ArrayList<>();
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
                    if(method.getParameterCount() == 0)
                        entries.add(includedClass.getSimpleName().toLowerCase() + "." + method.getName() + "();");
                    else
                        entries.add(includedClass.getSimpleName().toLowerCase() + "." + method.getName() + "(1);");
                }
            }
        }
        suggestedEntries = entries.toArray();
    }
    
    /**
     * Updates the PopupMenu with the users current input text
     * If there are available suggestions it will display
     */
    public void update()
    {
        final String inputText = codeTextArea.getInputText().trim();
        updateSuggestionList(inputText);
        setVisible(false);
        if(suggestedEntries == null || suggestedList.getModel().getSize() < 1 || inputText == null || inputText.isEmpty())
        {
            setVisible(false);
            return;
        }
        removeAll();
        add(suggestedList);
        suggestedList.setSelectedIndex(0);
        if(!isVisible())
            show(codeTextArea, 0, codeTextArea.getLinePosition().y);
        codeTextArea.requestFocusInWindow();
    }
    
    public boolean selectNext()
    {
        if(isShowing())
            suggestedList.setSelectedIndex(Math.min(suggestedList.getSelectedIndex() + 1, suggestedList.getModel().getSize() - 1));
        return isShowing();
    }
    
    public boolean selectPrevious()
    {
        if(isShowing())
            suggestedList.setSelectedIndex(Math.max(suggestedList.getSelectedIndex() - 1, 0));
        return isShowing();
    }
    
    public Object getSelected()
    {
        return suggestedList.getSelectedValue();
    }
    
    public void close()
    {
        removeAll();
        setVisible(false);
    }
    
    /**
     * Updates the JList displaying suggested entries that are part of the users input
     * @param inputText The users current input
     */
    private void updateSuggestionList(final String inputText)
    {
        ArrayList<Object> suggestions = new ArrayList<>();
        for (final Object suggestion : suggestedEntries)
        {
            if(suggestion.toString().toLowerCase().contains(inputText.toLowerCase()) &&
               !suggestion.toString().equalsIgnoreCase(inputText.trim()))
                suggestions.add(suggestion);
        }
        suggestedList = new JList(suggestions.toArray());
        suggestedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    
}
