/**
 * 
 */
package com.helperclass;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.crypto.SecretKey;

import com.action.dataowner.Process;
import com.dao.UserDAO;


public class FileDownload 
{
	public static String download( String ftpServer, String ftpuser, String ftppassword,String downloadDir,String fileName) throws MalformedURLException,
	         IOException
	   {
		   boolean flag=false;
		   String s="";
		   String filename1="";
		 //  String dir =null;
		   String f2="C:/Downloads/"+fileName;
		   
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
	         sb.append( downloadDir );
	         sb.append( '/' );
	         sb.append( fileName );
	         
	         /*
	          * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
	          * listing
	          */
	         sb.append( ";type=i" );
	         System.out.println("   url  :"+sb.toString());
		       
	         BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;
	        
	         try
	         {
	            URL url = new URL( sb.toString() );
	            URLConnection urlc = url.openConnection();
	            /* Download Location */
	           
	          /*  java.security.ProtectionDomain pd =	Process.class.getProtectionDomain();
				
				java.security.CodeSource cs = pd.getCodeSource();
				java.net.URL url1 = cs.getLocation();
				java.io.File f = new File( url.getFile());
				String path=f.getParent();
				String filepath[]=path.split("WEB-INF");
				Properties property = new Properties();
//				FileInputStream in=null;
//				in = new FileInputStream(path+"\\mod.properties");
				
				filepath[0]=filepath[0].substring(6, filepath[0].length()-1);
	        	System.out.println("  fileName  :"+fileName);
	        	System.out.println("  filepath[0]  :"+filepath[0]);
				  filename1=filepath[0]+"\\Files\\Download\\"+fileName;
				  
				  System.out.println("Path =======  :"+filename1);*/
				  
	          
	            
	            // dir = "D:/Downloads/";
	          //  File f = new File(dir);
	             
//	            if(!f.exists())
//	            {
//	            	f.mkdir();
//	            }
	            bis = new BufferedInputStream( urlc.getInputStream());
	            bos = new BufferedOutputStream( new FileOutputStream(f2) );
	            System.out.println("File Dounloaded Sucessfully..");
	            
	            System.out.println("Url : " + url);
	            

	           int i;
	            while ((i = bis.read()) != -1)
	            {
	               bos.write( i );
	            }
	          }
	         catch (Exception e)
	         {
				System.out.println("Opps,Exception in FileDownload Servlet :");
				e.printStackTrace();
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
	            if (bos != null)
	               try
	               {
	                  bos.close();
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
	      return f2;
	   }
}
