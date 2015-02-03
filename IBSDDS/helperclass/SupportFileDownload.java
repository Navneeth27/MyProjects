/**
 * 
 */
package com.helperclass;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.SecretKey;

public class SupportFileDownload 
{
	public static String download( String ftpServer, String ftpuser, String ftppassword,String foldername,
	         String fileName) throws MalformedURLException,
	         IOException
	   {
		 String s="";
		   boolean flag=false;
	      if (ftpServer != null && fileName != null )
	      {
	         StringBuffer sb = new StringBuffer( "ftp://" );
	         // check for authentication else assume its anonymous access.
	         if (ftpuser != null && ftppassword != null)
	         {
	            sb.append( ftpuser );
	            sb.append( ':' );
	            sb.append( ftppassword );
	            sb.append( '@' );
	         }
	         sb.append( ftpServer );
	         sb.append( '/' );
	         sb.append( '/' );
	         
	         sb.append( foldername );
	         sb.append( '/' );
	        sb.append(fileName);
	         /*
	          * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
	          * listing
	          */
	         
	         System.out.println("   url  :"+sb.toString());
	       
	         BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;
	         try
	         {
	            URL url = new URL( sb.toString() );
	            URLConnection urlc = url.openConnection();
	            byte[] contents = new byte[1024];
	            
	            int i=0;
	            /* Download Location */
	         //   String dir="C:\\Documents and Settings\\dhs\\My Documents\\Downloads\\PHR_Records\\"+username+"\\SupportFiles";
//	            File f = new File(dir);
//	            if(!f.exists())
//	            {
//	            	f.mkdir();
//	            }
	            bis = new BufferedInputStream( urlc.getInputStream());
	         //   bos = new BufferedOutputStream( new FileOutputStream(f+"\\"+fileName) );
	            System.out.println("Dounloaded......");
	            
	            System.out.println("Url : " + url);
	            while( (i = bis.read(contents)) != -1){ 
	                s = new String(contents, 0, i);               
	            }
	  
	        
	         
	            System.out.println("   My file data  :"+s);
	            
	         }
	         finally
	         {
	            if (bis != null)
	               try
	               {
	                  bis.close();
	               }
	               catch (IOException ioe)
	               {
	                  ioe.printStackTrace();
	               }
	            
	         }
	      }
	      else
	      {
	         System.out.println( "Input not available" );
	      }
	      return s.trim();
	   }
}
