
package com.action.user;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;


public class UserLogin extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		String file_path="";
		String username = "";
	    String dirToUploadFile="";
	    ServletInputStream sis=null;
	    String key_value="";
		try
		{
			HttpSession hs=request.getSession();
			String username1=(String) hs.getAttribute("userid");
			String pass=(String) hs.getAttribute("pass");
			
		
			System.out.println("User Name :"+username1);
			System.out.println("Password :"+pass);
			
			UserDAO userDao=UserDAO.getInstance();
			
			
			String path="";
			String fileName = "";
			String fName = "";
			String extension="";
	       
			FileOutputStream buffer = null;
			
			HttpSession hs1 = request.getSession(true);
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
	            
	         // try
	          //{
	            sis = request.getInputStream();
	            byte[] b = new byte[1024];
	            int x = 0;
	            int state = 0;
	            String name = null; String contentType2 = null;
	            
	            
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
				                
				                
				                 file_path=request.getRealPath("")+"\\Files\\Keys\\"+fileName;
				          		 
				                
				          		
				                buffer=new FileOutputStream(file_path);
				               
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
		             System.out.println("====== Login Status ======");
		             
		             key_value = ReadIdentyTocken.readfile(file_path);
		             
		             System.out.println("Key Val="+key_value);
		    			
		    			boolean result=userDao.checkUser(username1, pass);
		    			
		    			boolean flag=userDao.checkIdenty(username1,key_value);
		    			
		    			if(flag)
		    			{
		    				RequestDispatcher rd=null;
		    				int uid = userDao.getID1(username1);
		    				HttpSession hs2=request.getSession();
		    				hs1.setAttribute("username", username1);
		    				hs1.setAttribute("userid",uid);
		    				
		    				System.out.println("******** User Login Info ********");
		    				System.out.println("  Userid : " + uid);
		    				System.out.println("Username : " + username1);
		    				System.out.println("Password : " + pass);
		    				
		    				
		    				rd=request.getRequestDispatcher("/AllFiles/JSP/User/userhome.jsp");
		    				rd.forward(request,response);
		    			}
		    			else
		    			{
		    				response.sendRedirect("index.jsp?no=4");
		    			}
		     }
	      
	       sis.close();
	      buffer.close();
	     // }
	     // catch (IOException e)
	      //{
	    	//  err = e.toString();
	      //}
	      
	      
	      
	   
	    
			
		}
		catch (IOException e)
	      {
	    	  System.out.println("IOException :"+e);;
	      }
		catch(Exception e)
		{
			System.out.println("Exception :"+e);
		}
	}
}
