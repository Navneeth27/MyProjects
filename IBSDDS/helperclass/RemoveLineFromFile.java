/**
 @Author : Munna Kumar Singh
 Date : Aug 31, 2012
 File : RemoveLineFromFile.java
 Package : com.kumar
*/
package com.helperclass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveLineFromFile
{
	public static void removeLineFromFile(String file, String lineToRemove)
	{

		try 
		{

			  File inFile = new File(file);
	
			  if (!inFile.isFile()) 
			  {
			    System.out.println("Parameter is not an existing file");
			    return;
			  }
	
			  //Construct the new file that will later be renamed to the original filename.
			  File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			  String line = null;
	
			  //Read from the original file and write to the new
			  //unless content matches data to be removed.
			  while ((line = br.readLine()) != null) 
			  {
			    if (!line.trim().equals(lineToRemove)) 
			    {
			      pw.println(line);
			      pw.flush();
			    }
			  }
			  
			  pw.close();
			  br.close();
	
			  //Delete the original file
			  if (!inFile.delete()) 
			  {
			    System.out.println("Could not delete file");
			    return;
			  }
	
			  //Rename the new file to the filename the original file had.
			  if (!tempFile.renameTo(inFile))
			    System.out.println("Could not rename file");
	
		}
		catch (FileNotFoundException ex) 
		{
		  System.out.println("Opps,Exception in com.kumar.util.RemoveLineFromFile : ");
		  ex.printStackTrace();
		}
		catch (IOException ex)
		{
		  System.out.println("Opps,Exception in com.kumar.util.RemoveLineFromFile : ");
		  ex.printStackTrace();
		}
	}
	
	/* Test Development */
	public static void main(String[] args) 
	{
		String lineToDelete = "";
		lineToDelete = "India,Delhi";
		lineToDelete = "This content will append to the end of the file";
		//String sourceFile = "c:\\append\\a.txt";
		String sourceFile = "c:\\privateKey.txt";
		
		removeLineFromFile(sourceFile, lineToDelete);
		
		System.out.println("Line Deleted Sucessfully......");
		
	}
}
