
package com.action.user;

import javax.servlet.RequestDispatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;


public class NewUser extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			String userid=request.getParameter("userid");
			String pass=request.getParameter("password");
			String name=request.getParameter("name");
			String domain=request.getParameter("domain");
			String subdomain=request.getParameter("subdomain");
			String email=request.getParameter("email");
			
			String identy_tocken="";
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
			   //get current date time with Date()
			   Date date = new Date();
			   
			   String doj=dateFormat.format(date);
			   System.out.println(dateFormat.format(date));
			   
			   String value=""+name+"+"+doj+"+"+""+email+"+"+domain+"+"+subdomain;
			   
			   System.out.println("Input :"+value);
			   
			   HashingTechnique g1 = new HashingTechnique();
				
			   identy_tocken = g1.MD5(value);
			   
			   String MDKeyFilePath = request.getRealPath("")+"\\Files\\Keys\\MDKey.txt";
			   
			  
			   System.out.println("File Path :"+MDKeyFilePath);
			   FileWriter fstream = new FileWriter(MDKeyFilePath);
				
				BufferedWriter out = new BufferedWriter(fstream);

		        out.write(identy_tocken);
		        out.close();
		        
		        File f = new File(MDKeyFilePath);
		        
		        String fnm = f.getName();
		        
		        boolean sendMailflag=CL_SendMail.sendPersonalizedMail(email, "lalith.shywa@gmail.com", "nagvanman", "Download Secret Key File", "Identity Tocken", MDKeyFilePath, "smtp.gmail.com", "465",fnm);
			  
				System.out.println("Identity Tocken :"+identy_tocken);
			
			RequestDispatcher rd = null;
			String path = null;
			UserDAO userDao=UserDAO.getInstance();
			
			boolean result=userDao.addUser(userid, pass,name,domain,subdomain,doj,email,identy_tocken);
			if(result)
			{
					rd = request.getRequestDispatcher("index.jsp?no=6");
					rd.forward(request,response);
					
			}
			else
			{
				response.sendRedirect("index.jsp?no=4");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
