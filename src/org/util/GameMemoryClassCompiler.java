/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import org.model.GameMemoryClass;

/**
 *
 * @author Dean
 */
public class GameMemoryClassCompiler {
    
    private GameMemoryClass gameClass;
    private DiagnosticCollector<JavaFileObject> diagnostics;
    private Class<?> compiledClass;
    
    public GameMemoryClassCompiler(final GameMemoryClass gameClass)
    {
        this.gameClass = gameClass;
    }
    
    /**
     * Compiles the GameClass objects source code, if successful sets the compiledClass object to the compiled class.
     * @return Whether or not the class successfully compiled.
     */
    public boolean compile()
    {
        final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        final JavaFileObject javaClass = 
                new SimpleJavaFileObject(URI.create("string:///" + gameClass.className.replace('.','/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE) {
            
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors)
            {
                return gameClass.toSource();
            }
            
        };
        gameClass.write();
        diagnostics = new DiagnosticCollector<>();
        final Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaClass);
        final CompilationTask task = javaCompiler.getTask(null, null, diagnostics, null, null, compilationUnits);
        if(!task.call())
            return false;
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File("").toURI().toURL() });
            compiledClass = Class.forName(gameClass.className, true, classLoader);
        } catch (ClassNotFoundException ex)
        {
            System.err.println("Class not found: " + ex);
            return false;
        } catch (MalformedURLException ex)
        {
            System.err.println("Malformed class: " + ex);
            return false;
        }
        return true;
    }
    
    public Class<?> getCompiledClass()
    {
        return compiledClass;
    }
    
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics()
    {
        return diagnostics.getDiagnostics();
    }
    
}
