package com.action.dataowner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dao.AdminDAO;

import com.dao.*;

public class CloudSelection
{
	static int numofCloud;
	public static int getCloud()
	{
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		boolean flag=false;
		
		String c_url="";
		String c_username="";
		String c_Password="";
		int c_id=0;
		
		 int val;
		
		 DAO dao=DAO.getInstance();
		 con=dao.connector();
		AdminDAO adminDAO=AdminDAO.getInstance();
		numofCloud=adminDAO.getTotalNumberOfClouds();
		System.out.println("No Of Cloud :"+numofCloud);
		
		String sql="update m_cloud set c_flag=1 where c_id=1";
		try
		{
			st=con.createStatement();
			st.executeUpdate(sql);
			
			String sql1="select *from m_cloud where c_flag=1";
			Statement st1=con.createStatement();
			rs=st1.executeQuery(sql);
			while(rs.next())
			{
				c_id=rs.getInt(1);
				c_url=rs.getString(2);
				c_username=rs.getString(3);
				c_Password=rs.getString(4);
				
			}
			
			System.out.println("C ID 1 -------------- "+c_id);
			System.out.println("C URL 1 -------------- "+c_url);
			System.out.println("C Name 1 -------------- "+c_username);
			System.out.println("C Password 1 -------------- "+c_Password);
			
			
			
			if(numofCloud !=0)
			{
				val = 1+c_id;
				if(val==numofCloud)
				{
					val=1;
				}
				
				Statement st2=con.createStatement();
				String sql2="update m_cloud set c_flag=0 where c_id='"+c_id+"'";
				st2.executeQuery(sql1);
				System.out.println("SQL 1 -------------- "+sql1);
				Statement st3=con.createStatement();
				String sql3 = "update m_cloud set c_flag=1 where c_id='"+val+"'";
				
				st3.executeQuery(sql2);
				System.out.println("SQL 2 ------"+sql2);
				
				
				
			}
			
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return c_id;
	
		
		
		
	}

}
