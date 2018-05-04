/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.util.GameMemoryClassCompiler;

/**
 *
 * @author Dean
 */
public class GameMemoryClass {
    
    private LinkedList<String> includedImports;
    private LinkedList<Object> includedVariables;
    private LinkedList<String> includedMethodCode;
    private GameMemoryClassCompiler compiler;
    
    public String className;
    public String classExtension;
    
    public void addImports(final String... imports)
    {
        if(includedImports == null)
            includedImports = new LinkedList<>();
        for (final String impor : imports)
           includedImports.add(impor);
    }
    
    public void addVariables(final Object... variables)
    {
        if(includedVariables == null)
            includedVariables = new LinkedList<>();
        for (final Object variable : variables)
            includedVariables.add(variable);
    }
    
    public void addMethodCode(final String... methods)
    {
        if(includedMethodCode == null)
            includedMethodCode = new LinkedList<>();
        for (final String method : methods)
            includedMethodCode.add(method);
    }
    
    public Object[] getVariables()
    {
        if(includedVariables == null)
            return new Object[0];
        return includedVariables.toArray();
    }
    
    public String toSource()
    {
        final StringWriter buffer = new StringWriter();
        final PrintWriter writer = new PrintWriter(buffer);
        if(includedImports != null)
        {
            for (final String imports : includedImports)
            {
                writer.println(imports);
            }
            writer.println();
        }
        writer.println("public class " + className + " " + ((classExtension == null) ? 
                                                                                    "{" : 
                                                                                    "extends " + classExtension + " {"));
        writer.println();
        if(includedVariables != null)
        {
            for (final Object variable : includedVariables)
            {
                writer.println("\tpublic " + variable.getClass().getName() + " " + variable.getClass().getSimpleName().toLowerCase() + ";");
            }
            writer.println();
            String classConstructor = "\tpublic " + className + "(";
            for (final Object variable : includedVariables)
            {
                classConstructor += variable.getClass().getName() + " " + variable.getClass().getSimpleName().toLowerCase() + ", ";
            }
            classConstructor = classConstructor.substring(0, classConstructor.length() - 2);
            classConstructor += ") {";
            writer.println(classConstructor);
            writer.println();
            for (final Object variable : includedVariables)
            {
                writer.println("\t\tthis." + variable.getClass().getSimpleName().toLowerCase() + " = " + variable.getClass().getSimpleName().toLowerCase() + ";");
            }
            writer.println();
            writer.println("\t}");
            writer.println();
        }
        
        writer.println("\tpublic void run() {");
        writer.println();
        writer.println("\t\ttry {");
        writer.println();
        if(includedMethodCode != null)
        {
            int userCodeLine = 0;
            for (final String code : includedMethodCode)
            {
                if(!code.trim().startsWith("if") && !code.trim().startsWith("for") && !code.trim().startsWith("while") && code.trim().length() > 1)
                    writer.println(includedVariables.get(0).getClass().getSimpleName().toLowerCase() + ".updateRunningLine(" + (userCodeLine) + ");");
                writer.println("\t\t\t" + code);
                userCodeLine++;
            }
            writer.println();
        }
        writer.println("\t\t} catch(final Exception e) { System.out.println(\"Error: \"+e.getMessage()); }");
        writer.println("\t}");
        writer.println();
        writer.println("}");
        writer.close();
        return buffer.toString();
    }
    
    /**
     * Uses or creates an instance of GameMemoryClassCompiler and then compiles the code
     * @return Whether or not the class compiled correctly
     */
    public boolean compile()
    {
        if(compiler == null)
            compiler = new GameMemoryClassCompiler(this);
        return compiler.compile();
    }
    
    /**
     * Attempts to create an instance of the compiled class and invoke the run method
     */
    public void invokeMethod()
    {
        final Class<?>[] constructorClasses = new Class[includedVariables.size()];
        final Object[] constructorParams = new Object[includedVariables.size()];
        for (int index = 0; index < includedVariables.size(); index++)
        {
            constructorClasses[index] = includedVariables.get(index).getClass();
            constructorParams[index] = includedVariables.get(index);
        }
        try {
            final Object invokedInstance = compiler.getCompiledClass().getConstructor(constructorClasses).newInstance(constructorParams);
            Method runMethod = compiler.getCompiledClass().getMethod("run", null);
            runMethod.setAccessible(true);
            runMethod.invoke(invokedInstance, null);
        } catch (Exception e) {
            System.out.println("Unable to invoke method " + e.getMessage());
        }
    }
    
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics()
    {
        return compiler.getDiagnostics();
    }
    
    public void clearMethodCode()
    {
        if(includedMethodCode != null)
        {
            includedMethodCode.clear();
            includedMethodCode = null;
        }
    }
    
    public void write()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Dean/Desktop/outputclass.java"));
            writer.write(toSource());
            writer.close();
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
}
