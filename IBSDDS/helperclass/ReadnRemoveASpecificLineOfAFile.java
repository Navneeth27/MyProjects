/**
 @Author : Munna Kumar Singh
 Date : Sep 10, 2012
 File : RemoveASpecificLineOfAFile.java
 Package : com.kumar
*/
package com.helperclass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReadnRemoveASpecificLineOfAFile 
{
	public static String readSpecificLine(String filePath) throws IOException
	{
		ArrayList<String> lines = new ArrayList<String>();
        String out = "";
	    // read the file into lines
	    BufferedReader r = new BufferedReader(new FileReader(filePath));
	    String in;
	    while ((in = r.readLine()) != null)
	    lines.add(in);
	    r.close();

	    // Read 2nd Last Line of a file
	    out = lines.get(lines.size() - 2);
	    
	    return out;
	}
	
	/* Check For OtherFile Operation Here */
	
	public static void main(String[] args) throws IOException 
	{
		String fileName = "C:\\a\\d_privateKey.txt";

	    ArrayList<String> lines = new ArrayList<String>();

	    // read the file into lines
	    BufferedReader r = new BufferedReader(new FileReader(fileName));
	    String in;
	    while ((in = r.readLine()) != null)
	        lines.add(in);
	    r.close();

	    // check your condition
	    String secondFromBottom = lines.get(lines.size() - 2);
	    System.out.println(secondFromBottom);
	    if (secondFromBottom.matches("Hello World!")) {
	        lines.remove(lines.size() - 1);
	        lines.add("My fixed string");
	    }  

	    // write it back
	    PrintWriter w = new PrintWriter(new FileWriter(fileName));
	    for (String line : lines)
	        w.println(line);
	    w.close();
	}
}
