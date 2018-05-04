/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.util;

import java.io.File;

/**
 *
 * @author Dean
 */
public class RunWithJDK {
    
    /**
     * Runs the application with a version of JDK found on the users machine.
     * @throws Exception if the running location or JDK file system cannot be found.
     */
    public RunWithJDK() throws Exception
    {
        final String jdkLocation = locateJDK();
        final File jarLocation = getRunningLocation();
        if(jdkLocation == null)
        {
            System.out.println("No JDK found, unable to launch.");
            return;
        }
        if(jarLocation == null)
        {
            System.out.println("Unable to get jar location.");
            return;
        }
        String command = "\"" + jdkLocation + "/bin/java.exe\"";
        command += " -cp \"" + jarLocation + "\" org.view.UserInterface";
        Runtime.getRuntime().exec(command);
    }
    
    /**
     * Gets the location of the currently running class
     * @return The location of this class on the file system
     * @throws Exception
     */
    private File getRunningLocation() throws Exception
    {
        return new File(RunWithJDK.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }
    
    /**
     * Attempts to locate the users Java installations to find a version of JDK
     * @return the location to a copy of JDK
     * @throws Exception 
     */
    private String locateJDK() throws Exception
    {
        for (char drive = 'A'; drive < 'Z'; drive++)
        {
            final File java32Bit = new File(drive + ":/Program Files (x86)/Java/");
            final File java64Bit = new File(drive + ":/Program Files/Java/");
            if(java32Bit.exists())
            {
                final File[] folders = java32Bit.listFiles();
                for (final File folder : folders)
                {
                    if(folder.getName().toLowerCase().contains("jdk"))
                        return folder.getAbsolutePath().replaceAll("\\\\", "/");
                }
            }
            if(java64Bit.exists())
            {
                final File[] folders = java64Bit.listFiles();
                for (final File folder : folders)
                {
                    if(folder.getName().toLowerCase().contains("jdk"))
                        return folder.getAbsolutePath().replaceAll("\\\\", "/");
                }
            }
        }
        return null;
    }
    
    public static void main(final String[] args) throws Exception
    {
        new RunWithJDK();
    }
            
    
}
