/**
 @Author : Munna Kumar Singh
 Date : Aug 31, 2012
 File : ReadLastLine.java
 Package : com.kumar
*/
package com.helperclass;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
 
public class ReadLastLineOfFile
{
 
	public static String  readLastLineOfAFile(String filePath)
	{
		String lastLine = "";
		
		try 
		{
			FileInputStream in = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			 
			String strLine = null,tmp;
			 
			  while ((tmp = br.readLine()) != null)
			  {
			     strLine = tmp;
			  }
			 
			  lastLine = strLine;
			 
			  in.close();
			  
		} catch (Exception e) 
		{
			System.out.println("Opps,Exception in com.kumar.util.ReadLastLineOfFile : ");
			e.printStackTrace();
		}
		
		return lastLine;
	}
	
	
	
/* Testing The Development */	
	public static void main(String[] args) throws Exception 
	 {
		String filePath = "c:\\append\\a.txt";
		String lastLine = "";
		lastLine = readLastLineOfAFile(filePath);
		System.out.println("Last Line :");
		System.out.println(lastLine);
		  
	 }
}