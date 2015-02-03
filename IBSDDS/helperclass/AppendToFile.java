/**
 @Author : Munna Kumar Singh
 Date : Aug 31, 2012
 File : AppendToFileExample.java
 Package : com.kumar
*/
package com.helperclass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
 
public class AppendToFile 
{
    
	public static boolean appendDataToFile(String filePath,String dataToAppend)
	{
		boolean flag = false;
		try
		{
            
    		//File file =new File("c:\\append\\a.txt");
    		File file =new File(filePath);
 
    		//if file doesnt exists, then create it
    		if(!file.exists())
    		{
    			file.createNewFile();
    		}
 
    		/*
    		 * 1. Replace all existing content with new content.
    		 *    FileWriter fileWritter = new FileWriter(file);
    		*/
    		
    		/*
    		 * 2. Append new content.
    		 *    FileWriter fileWritter = new FileWriter(file,true);
    		*/
    		
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.newLine();//appending a new line after the existing file content
	        bufferWritter.write(dataToAppend);
	        bufferWritter.close();
	        
	        flag = true;
	        
	        System.out.println("Done");
    	}
		catch(IOException e)
		{
			System.out.println("Opps Exception In com.kumar.util.AppendToFile :");
    		e.printStackTrace();
    	}
		return flag;
	}
	
	/* Testing The Development */
	
	public static void main( String[] args )
    {	
    	String filePath = "c:\\append\\a.txt";
    	String dataToAppend = "Hello Mr. Kumar";
    	appendDataToFile(filePath, dataToAppend);
    	System.out.println("Data Appended Sucessfully.......");
    }
}
