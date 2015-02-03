package com.action.admin;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UploadFile extends HttpServlet
{
	String ftpserver = "dhsinformatics.com";
    String ftpusername = "dhsinf17";
    String ftppassword = "nikisujai";
    String username = "";
    String dirToUploadFile="";
    ServletInputStream sis=null;
    
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	  {
		
		String path="";
		String fileName = "";
		String fName = "";
		String extension="";
       
		HttpSession hs = request.getSession(true);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache");
    
        String err = "";
        String lastFileName = "";
        
       String contentType = request.getContentType();
       String boundary = "";
       
       int BOUNDARY_WORD_SIZE = "boundary=".length();
       if ((contentType == null) || (!contentType.startsWith("multipart/form-data")))
       {
	         err = "Ilegal ENCTYPE : must be multipart/form-data\n";
	         err = err + "ENCTYPE set = " + contentType;
       }
       else
       {
          boundary = contentType.substring(contentType.indexOf("boundary=") + BOUNDARY_WORD_SIZE);
          boundary = "--" + boundary;
            
          try
          {
            sis = request.getInputStream();
            byte[] b = new byte[1024];
            int x = 0;
            int state = 0;
            String name = null; String contentType2 = null;
            FileOutputStream buffer = null;
            
            while ((x = sis.readLine(b, 0, 1024)) > -1)
            {
	             String s = new String(b, 0, x);
	             if (s.startsWith(boundary))
	             {
		               state = 0;
		               name = null;
		               contentType2 = null;
		               fileName = null;
	             }
	             else if ((s.startsWith("Content-Disposition")) && (state == 0))
	             {
		               state = 1;
		                if (s.indexOf("filename=") == -1)
		                {
		                	name = s.substring(s.indexOf("name=") + "name=".length(), s.length() - 2);
		                }
		                else
		                {
		                	name = s.substring(s.indexOf("name=") + "name=".length(), s.lastIndexOf(";"));
		                	fileName = s.substring(s.indexOf("filename=") + "filename=".length(), s.length() - 2);
		                	if (fileName.equals("\"\""))
		                    {
		                		fileName = null;
		                    }
		                    else
		                    {
		                    	String userAgent = request.getHeader("User-Agent");
		                    	String userSeparator = "/";
		                    	if (userAgent.indexOf("Windows") != -1)
		                    	{
		                    		userSeparator = "\\";
		                    	}
		                    	if (userAgent.indexOf("Linux") != -1)
			                    {
		                    		userSeparator = "/";
			                    }
		                   
		                    	fileName = fileName.substring(fileName.lastIndexOf(userSeparator) + 1, fileName.length() - 1);
		                    	if (fileName.startsWith("\""))
			                    {
		                    		fileName = fileName.substring(1);
			                    }
		                    }
				                 
		                	name = name.substring(1, name.length() - 1);
				            if (!name.equals("file"))
				                      continue;
				            if (buffer != null)
				            {
				                buffer.close();
				            }
			                lastFileName = fileName;
							fName=fileName;
							int dotPos = fName.lastIndexOf(".");
							extension = fName.substring(dotPos);
			                System.out.println("File Name : " + fileName);
			                
			               path = request.getRealPath("") +"\\Files\\Upload\\" + fileName;
			               hs.setAttribute("filepath", path);
				           System.out.println("Path : " + path);
				           System.out.println("File Namem : " + fileName);
				           buffer = new FileOutputStream(path);
		               }
	      
	            }
	            else if ((s.startsWith("Content-Type")) && (state == 1))
	            {
	            	state = 2;
	            	contentType2 = s.substring(s.indexOf(":") + 2, s.length() - 2);
	            }
	           else if ((s.equals("\r\n")) && (state != 3))
	           {
	        	   	state = 3;
	           }
	           else
	           {
		          if (!name.equals("file"))
		                    continue;
		            buffer.write(b, 0, x);
	           }
	     }
      
       sis.close();
      buffer.close();
      }
      catch (IOException e)
      {
    	  err = e.toString();
      }
   }
    
     boolean ok = err.equals("");
     if (!ok)
     {
        out.println(err);
     }
   
     response.sendRedirect(request.getContextPath()+"/FTPUpload?path="+path+"&fname="+lastFileName);
    
	}
					
}

