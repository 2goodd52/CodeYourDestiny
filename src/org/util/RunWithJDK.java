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
    
    private File getRunningLocation() throws Exception
    {
        return new File(RunWithJDK.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }
    
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
        System.out.println(new RunWithJDK());
    }
            
    
}
