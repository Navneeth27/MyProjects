
package com.action.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.kumar.util.RSA_File_EncryptionDecryption;

public class GenerateKey extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		boolean flag = false;
		RequestDispatcher rd = null;
		
		System.out.println("path :"+req.getContextPath());
		
		String publicKeyFilePath = req.getRealPath("")+"\\Files\\Keys\\publicKey.txt";
		String privateKeyFilePath = req.getRealPath("")+"\\Files\\Keys\\privateKey.txt";
		
		System.out.println("Public Key Path : " + publicKeyFilePath);
		System.out.println("Private Key Path : " + privateKeyFilePath);
		
		try 
		{
			//flag = RSA_File_EncryptionDecryption.generateRSAKey(publicKeyFilePath,privateKeyFilePath);
			//if(flag)
			{
				rd = req.getRequestDispatcher("/AllFiles/JSP/Admin/generate_key.jsp?no=1");
				rd.forward(req,resp);
			}
			//else
			{
				rd = req.getRequestDispatcher("/AllFiles/JSP/Admin/generate_key.jsp?no=2");
				rd.forward(req,resp);
			}
			
		} catch (Exception e) 
		{
			System.out.println("Opps,Exception In GenerateKey Servlet : " );
			e.printStackTrace();
		}
	}
}
