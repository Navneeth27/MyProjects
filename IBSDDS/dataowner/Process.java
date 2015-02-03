package com.action.dataowner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;







public class Process 
{
	public static String encryptMessage1(byte[] v,String key1,String key2)
	{
		String c="";
		
			
			 for(int i=0;i<v.length;i++)
			  {
				 c = c + RNS_Main.Encry(v[i],key1,key2) + "#";
			  }
			
		return c;
	}
	
	
	public static String decryptMessage(String val,String fileName,String key1,String key2,String outpath)
	{
		 int i;
	        String t_val = val.substring(0, val.length()-1);

	     
	        String[] s = t_val.split("#");
	        System.out.println("  S length :"+s.length);
	        
	        byte[] va = new byte[s.length];

	        int cc;

	        for (i = 0; i < s.length; i++)
	        {
	            
	            cc = RNS_Main.Decrypt(s[i],key1,key2);
	             va[i] =( byte) cc;
	            
	         }
	    	String file="";
	        String filename1="";
	        try
			{ 
	        	java.security.ProtectionDomain pd =	Process.class.getProtectionDomain();
				
				java.security.CodeSource cs = pd.getCodeSource();
				java.net.URL url = cs.getLocation();
				java.io.File f = new File( url.getFile());
				String path=f.getParent();
				String filepath[]=path.split("WEB-INF");
				Properties property = new Properties();
//				FileInputStream in=null;
//				in = new FileInputStream(path+"\\mod.properties");
				
				filepath[0]=filepath[0].substring(0, filepath[0].length()-1);
	        	System.out.println("  fileName  :"+fileName);
	        	System.out.println("  filepath[0]  :"+filepath[0]);
				  filename1=filepath[0]+"\\Files\\Download\\"+fileName;
				  System.out.println("*****************************path  :"+path);
				  System.out.println("*****************************filename1  :"+filename1);
				FileOutputStream fileOut1 = new FileOutputStream(outpath);
				fileOut1.write(va);
				fileOut1.flush();
				fileOut1.close();
				
			}
			catch(Exception e)
			{
				System.out.println("Opp's error is in Utility getPro(String str) "+e);
			}
			
				return outpath;
			
	
		
	}
	
	
}
