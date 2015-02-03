/**
 * 
 */
package com.dao;

import java.sql.Connection;

import java.sql.DriverManager;

import com.globaldata.Global;
import com.helperclass.*;


public class DAO 
{
	private static DAO dao=null;
	private DAO()
	{
		
	}
	public static DAO getInstance()
	{
		if(dao==null)
		{
			dao=new DAO();
		}
		return dao;
	}
	
	public Connection connector()
	{
		Connection con=null;
		try {

			Class.forName(Global.JDBC_DRIVER);
			
			System.out.println("Driver has loaded");
			con = DriverManager.getConnection(Global.JDBC_HOST_URL_WITH_DBNAME,Global.DATABASE_USERNAME, Global.DATABASE_PASSWORD);
			System.out.println("Connected" + con);

		} catch (Exception e) {
			System.out.println("Exception in serverconnector-->connector(): "+ e);
		}
		return con;
	}
}
