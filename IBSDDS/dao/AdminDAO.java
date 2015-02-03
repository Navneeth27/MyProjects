/**
 * 
 */
package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.dao.DAO;

public class AdminDAO
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static AdminDAO adminDAO=null;
	private AdminDAO()
	{
		
	}
	public static AdminDAO getInstance()
	{
		if(adminDAO == null)
		{
			adminDAO= new AdminDAO();
		}
		return adminDAO;
	}
	public boolean checkAdmin(String admin,String pass)
	{
		boolean flag=false;
		try
		{
			DAO dao=DAO.getInstance();
			
			connection=dao.connector();
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_admin where adminid='"+admin+"' and password='"+pass+"'");
			
			while(resultSet.next())
			{
				flag=true;
			}
			System.out.println("Admin Login Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Exception in AdminrDAO-->checkAdmin(String admin,String pass): "+ e);
		}
		return flag;
	}
	
	
	
/* Get Profile Details (Starts) */
	
	public static ResultSet getProfile(String username)
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_admin where adminid='"+username+"'";
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getProfile(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/* Get Profile Details (Ends) */
	
	
	public boolean editProfile(String [] s) 
	{
		boolean flag=false;
		String sql = "";
		try
		{   
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_admin set name='"+s[1]+"',address='"+s[2]+"',phone='"+s[3]+"',email='"+s[4]+"' where id='"+s[0]+"'";
			System.out.println("******* Edit Profile Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Admin Edit Profile Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-editProfile() :");
			e.printStackTrace();
		}
		return flag;
	}
	public boolean chnagePass(int id,String cpass) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_admin set password='"+cpass+"' where id='"+id+"'";
			System.out.println("******* Change Password Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Admin Change Pass Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-chnagePass() : ");
			e.printStackTrace();
		}
		return flag;
	}
	
	
/* Get Server Details (Starts) */
	
	public static ResultSet getServerDetails()
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_cloud";
			System.out.println("******* Server Info *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getServerDetails(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Server Details (Ends) */
	
/* Get Specific Server Details (Starts) */
	
	public static ResultSet getServerSpecificDetails(int serverId)
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_cloud where c_id='"+serverId+"'";
			System.out.println("******* Get Specific Server Info *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getServerSpecificDetails(int serverId): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/* Get Specific Server Details (Ends) */
	
/* Add Server Details (Starts) */
	
	public boolean addServerDetails(String hostName, String username, String password) 
	{
		boolean flag=false;
		String sql = "";
		String url="";
		try
		{
			url = ""+hostName;
			
						
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "insert into m_cloud (c_url,c_username,c_password) values('"+url+"','"+username+"','"+password+"')";
			System.out.println("******* Add Server Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Add Server Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-addServerDetails() :");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Add Server Details (Ends) */
	
/* Check Server Existence (Starts) */
	
	public boolean checkServerHostExistence(String hostName) 
	{
		boolean flag=false;
		String sql = "";
		String url = "";
		try
		{
            url = "ftp://"+hostName;
			
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select * from m_cloud where c_url='"+url+"'";
			System.out.println("******* Check Server Existence *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				flag=true;
			}
			System.out.println("Check Server Existence Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-checkServerHostExistence : ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Check Server Existence (Ends) */
	
/* Edit Server Details (Starts) */
	
	public boolean editServerDetails(String [] s) 
	{
		boolean flag=false;
		String sql = "";
		String url = "";
		try
		{   
			url = "ftp://"+s[1];
			
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_cloud set c_url='"+url+"',c_username='"+s[2]+"',c_password='"+s[3]+"' where c_id='"+s[0]+"'";
			System.out.println("******* Edit Server Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Admin Edit Server Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-editServerDetails(String [] s)  :");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Edit Server Details (Starts) */
	

/* Delete Server Details (Starts) */
	
	public boolean deleteServerDetails(int serverId) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "delete from m_cloud where c_id='"+serverId+"'";
			System.out.println("******* Delete Server Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Delete Server Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in deleteServerDetails(int serverId) :" );
			e.printStackTrace();
		}
		return flag;
	}
	
/* Delete Server Details (Ends) */
	
	public ResultSet getUsers( )
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_user ");
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getUsers(): "+ e);
		}
		return resultSet;
	}
	
	
	
	public String getUser(int id)
	{
		String name="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select u_name from m_user where u_code='"+id+"'");
			while(resultSet.next())
			{
				name=resultSet.getString(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getUser(int id): "+ e);
		}
		return name;
	}
	
/* Check User Existence (Starts) */
	
	public boolean checkUserExistence(String username) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select * from m_user where username='"+username.trim()+"'";
			System.out.println("******* Check User Existence *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				flag=true;
			}
			System.out.println("Check User Existence Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-checkUserExistence(String username) : ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Check User Existence (Ends) */
	
/* Add User Details (Starts) */
	
	public boolean addUserDetails(String username, String password, String name, String email,int department1,int department2,int designation1,int designation2,String address,String city,String phone,String mobile) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "insert into m_user (username,password,u_name,dept_code,dept_code1,design_code,design_code1,u_address,u_city,u_cell,u_phone,u_email) values('"+username+"','"+password+"','"+name+"','"+department1+"','"+department2+"','"+designation1+"','"+designation2+"','"+address+"','"+city+"','"+phone+"','"+mobile+"','"+email+"')";
			System.out.println("******* Add User Details *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("User Registeration Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-addUserDetails : ");
			e.printStackTrace();
		}
		return flag;
	}

/* Add User Details (Ends) */
	
/* Get Department By Id(Starts) */
	
	public String getDepartmentName(int id)
	{
		String name="";
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select domain_code from m_domain where domain_code='"+id+"'";
			System.out.println("****** Get Department Name By Id *******");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				name=resultSet.getString(1);
			}
			
			System.out.println("Department Name : " + name);
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getDepartmentName(int id): ");
			e.printStackTrace();
		}
		return name;
	}
	
/* Get Department By Id(Ends) */
	
/* Get Designation By Id(Starts) */
	
	public String getDesignationName(int id)
	{
		String name="";
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select sdomain_code from m_subdomain where sdomain_code='"+id+"'";
			System.out.println("****** Get Designation Name By Id *******");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				name=resultSet.getString(1);
			}
			
			System.out.println("Designation Name : " + name);
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getDesignationName(int id): ");
			e.printStackTrace();
		}
		return name;
	}
	
/* Get Designation By Id(Ends) */
	
/* Edit User Details (Starts) */

	public boolean editUserDetails(String [] s) 
	{
		boolean flag=false;
		String sql = "";
		try
		{   
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_user set userid='"+s[1]+"',password='"+s[2]+"',u_name='"+s[3]+"',domain_code='"+s[4]+"',sdomain_code='"+s[5]+"',u_doj='"+s[6]+"',u_email='"+s[7]+"' where u_code='"+s[0]+"'";
			System.out.println("******* Edit User Details *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Edit User Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-editUserDetail(String [] s): ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Edit User Details (Ends) */
	
	public static ResultSet getSpecificUserDetails(int id)
	{
		String name="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_user where u_code='"+id+"'");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getSpecificUserDetails(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Delete User Details (Starts) */
	
	public boolean deleteUserDetails(int userId) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "delete from m_user where u_code='"+userId+"'";
			System.out.println("******* Delete User Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Delete User Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in Admin-deleteUserDetails(int userId) :" );
			e.printStackTrace();
		}
		return flag;
	}
	
/* Delete User Details (Ends) */
	
	
/* Get Department Details (Starts) */
	
	public static ResultSet getDepartmentDetails()
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_domain";
			System.out.println("******* Domain Info *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getDepartmentDetails(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Department Details (Ends) */
	
	
/* Get Designation Details (Starts) */
	
	public static ResultSet getDesignationDetails()
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_subdomain";
			System.out.println("******* Designation Info *********\n");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getDesignationDetails(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Designation Details (Ends) */
	
/* Get Department Details (Starts) */
	
	public ResultSet getDepartments()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_department");
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getDepartments(): "+ e);
		}
		return resultSet;
	}
	
/* Get Department Details (Ends) */
	

/* Get Designation Details (Starts) */
	
	public ResultSet getDesignations()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_designation");
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getDesignations(): "+ e);
		}
		return resultSet;
	}
	
/* Get Department Details (Ends) */
	
	
/* Get Admin Upload Transaction(Starts) */

	public ResultSet getAdminUploadTransaction()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_upload");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getFiles() : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Admin Upload Transaction(Ends) */
	
/* Get Admin Download Transaction(Starts) */

	public ResultSet getAdminDownloadTransaction()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_download");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getFiles() : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Admin Download Transaction(Ends) */
	
/* Get Last Key Updated(Starts) */
	
	/*public String getLastKeyUpdatedDate()
	{
		String date="";
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select key_date from m_config";
			System.out.println("****** Last Key Updation Date *******");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				date=resultSet.getString(1);
			}
			
			System.out.println("Key Updated On : " + date);
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getLastKeyUpdatedDate(): ");
			e.printStackTrace();
		}
		return date;
	}
	*/
/* Get Last Key Updated(Ends) */
	
/* Getting The Number Of Cloud(Starts)*/

	public int getTotalNumberOfClouds()
	{
		int clouds=0;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "SELECT COUNT(*) FROM m_cloud";
			System.out.println("****** Total Number Of Clouds *******");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				clouds = resultSet.getInt(1);
			}
			
			System.out.println("Total Number Of Clouds : " + clouds);
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getTotalNumberOfClouds():");
			e.printStackTrace();
		}
		return clouds;
	}
	
/* Getting The Number Of Cloud(Ends)*/
	
/* Add Upload Transaction (Starts) */
	
	public boolean addUploadTransaction(String ufileName,String ufileType, String udate,String subject,int cloudId,int ownerId) 
	{
		boolean flag=false;
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="insert into m_file_upload (f_name,f_type,f_upload_date,f_subject,cloud_id,owner_id) values('"+ufileName+"','"+ufileType+"','"+udate+"','"+subject+"','"+cloudId+"','"+ownerId+"')";
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Upload Transaction Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-addUploadTransaction.....");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Add Upload Transaction (Ends) */	

/* Get Upload File Details(Starts) */

	public ResultSet getUploadFiles()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_file_upload");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-getUploadedFiles(int ownerId) : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Upload File Details(Ends) */
	
	public ResultSet getUploadedFiles(int ownerId)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_file_upload where owner_id='"+ownerId+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-getUploadedFiles(int ownerId) : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Upload File Details By fileId(Starts) */

	public String getUploadFiles(int fileId)
	{
		String fnam = "";
		String owneid = "";
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			//sql = "select * from m_file_upload where f_code='"+fileId+"'";
			sql = "select a.f_name,b.ownerid from m_file_upload a,m_owner b where f_code='"+fileId+"' and owner_id=id";
			System.out.println("@@@@@@@@@@@ Upload Info @@@@@@@@@@@@@@@");
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next())
			{
				fnam = resultSet.getString(1);
				owneid = resultSet.getString(2);
			}
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getUploadFiles() : ");
			e.printStackTrace();
		}
		return fnam+"~"+owneid;
	}
	
/* Get Upload File Details By fileId(Ends) */
	
/* Delete Uploaded File Details (Starts) */
	
	public boolean deleteUploadedFiles(int uploadId) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "delete from m_file_upload where f_code='"+uploadId+"'";
			System.out.println("******* Delete Uploaded File Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Delete UploadedFile Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-deleteUploadedFiles(int uploadId) :" );
			e.printStackTrace();
		}
		return flag;
	}
	
/*  Delete Uploaded File Details (Ends) */
	
/* Add AcessControl Details (Starts) */
	
	public boolean addAcessControl(int fileCode,int deptCode,int designationCode) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "insert into m_acess_control(f_code,domain_code,subdomain_code) values('"+fileCode+"','"+deptCode+"','"+designationCode+"')";
			System.out.println("******* Add AcessControl Details *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Add AcessControl Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-addAcessControl() : ");
			e.printStackTrace();
		}
		return flag;
	}

/* Add AcessControl Details (Ends) */
	
/* Get Access Control Details(Starts) */

	public ResultSet getAccessControls()
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_acess_control");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getAccessControls() : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Access Control Details(Ends) */
	
/* Delete Access Control Details (Starts) */
	
	public boolean deleteAccessControls(int acId) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "delete from m_acess_control where ac_code='"+acId+"'";
			System.out.println("******* Delete Server Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Access Control Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-deleteAccessControls(int acId) :" );
			e.printStackTrace();
		}
		return flag;
	}
	
/*  Delete Access Control Details (Ends) */
	
/* Edit Access Control Details (Starts) */
	
	public boolean editAccessControls(int [] s) 
	{
		boolean flag=false;
		String sql = "";
		try
		{   
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_acess_control set f_code='"+s[1]+"',dept_code='"+s[2]+"',desig_code='"+s[3]+"' where ac_code='"+s[0]+"'";
			System.out.println("******* Edit Access Controls Details Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Admin Access Controls Details Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-editAccessControls(int [] s)  :");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Edit Access Control Details(Ends) */
	
/* Get Max User ID(Starts)*/	
	
	public int getMaxUserID()
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select u_code from m_user");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("Max User ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO--> getMaxUserID(): ");
			e.printStackTrace();
		}
		return i;
	}
	
/* Get Max User ID(Ends)*/	
	
/* Get Max FileAccessControl ID(Starts)*/	
	
	public int getMaxFileAccessControlID()
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select max(ac_code) from m_acess_control");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("Max Upload ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO--> getMaxUploadID(): ");
			e.printStackTrace();
		}
		return i;
	}
	
/* Get Max FileAccessControl ID(Ends)*/	

/* Get Specific FileAccessControl Info(Starts) */

	public ResultSet getFileAccessControlInfo(int accessId)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_acess_control where ac_code='"+accessId+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getFileAccessControlInfo : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Specific FileAccessControl Info(Ends) */
	
/* Get File Name(Starts) */
	
	public String getFileName(int fileId)
	{
		String fileName="";
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select f_name from m_file_upload where f_code='"+fileId+"'";
			System.out.println("****** Get File Name By Id *******");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				fileName=resultSet.getString(1);
			}
			
			System.out.println("File Id : "+fileId+"\nFile Name : " + fileName);
		}
		catch(Exception e)
		{
			System.out.println("Exception in adminDAO-->getLastKeyUpdatedDate(): ");
			e.printStackTrace();
		}
		return fileName;
	}
	
/* Get File Name(Ends) */
	
/* Download Authentication1(Starts) */

	public boolean downloadAuthentication1(int fileId,int deptId1,int designationId1)
	{
		boolean flag = false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select * from m_acess_control where f_code='"+fileId+"' and dept_code='"+deptId1+"' and desig_code='"+designationId1+"'";
			System.out.println("*********** Login Authentication Info **************");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				flag = true;
			}
			
			System.out.println("Download Authentication1 Status : " + flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-downloadAuthentication1() : ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Download Authentication1(Ends) */
	
/* Download Authentication2(Starts) */

	public boolean downloadAuthentication2(int fileId,int deptId2,int designationId2)
	{
		boolean flag = false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select * from m_acess_control where f_code='"+fileId+"' and dept_code='"+deptId2+"' and desig_code='"+designationId2+"'";
			System.out.println("*********** Login Authentication Info **************");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				flag = true;
			}
			
			System.out.println("Download Authentication2 Status : " + flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-downloadAuthentication2() : ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Download Authentication2(Ends) */
		
	
	
// #################  All About The Data Owner #################	
	

	public boolean checkDataOwner(String username,String password)
	{
		boolean flag=false;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_owner where ownerid='"+username+"' and password='"+password+"'");
			while(resultSet.next())
			{
				flag=true;
			}
			System.out.println("Data Owner Login Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Exception in AdminrDAO-->checkAdmin(String admin,String pass): "+ e);
		}
		return flag;
	}
	
	
/* Get Profile Details (Starts) */
	
	public static ResultSet getOwnerProfile(String username)
	{
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="select * from m_owner where ownerid='"+username+"'";
			System.out.println("Update -----------:"+sql);
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-getOwnerProfile(): ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Profile Details (Ends) */
	
/* Edit Data Owner Profile Details(Starts) */
	public boolean editOwnerProfile(String [] s) 
	{
		boolean flag=false;
		String sql = "";
		try
		{   
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_owner set name='"+s[1]+"',email='"+s[2]+"' where id='"+s[0]+"'";
			
			System.out.println("******* Edit Data Owner Profile Info *********");
			System.out.println("Data Updated =========== :"+sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Admin Edit Data Owner Profile Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in AdminDAO-editOwnerProfile() :");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Edit Data Owner Profile Details() */

/* Check Data Owner Existence (Starts) */
	
public boolean checkOwner(String username,String password)
{
	boolean flag=false;
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		resultSet = statement.executeQuery("select * from m_owner where ownerid='"+username+"' and password='"+password+"'");
		while(resultSet.next())
		{
			flag=true;
		}
		System.out.println("Data Owner Existence Status : "+flag);
	}
	catch(Exception e)
	{
		System.out.println("Exception in AdminrDAO-->checkOwner(String username,String password): ");
		e.printStackTrace();
	}
	return flag;
}

/* Check Data Owner Existence (Ends) */

/* Change Data Owner Password (Starts) */
public boolean chnageOwnerPass(int id,String cpass) 
{
	boolean flag=false;
	String sql = "";
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		sql = "update m_owner set password='"+cpass+"' where id='"+id+"'";
		System.out.println("******* Change Data Owner Password Info *********\n");
		System.out.println(sql);
		int i=statement.executeUpdate(sql);
		if(i!=0)
		{
			flag=true;
		}
		System.out.println("Change Data Owner Password Status : "+flag);
	}
	catch(Exception e)
	{
		System.out.println("Opp's Error is in AdminDAO-chnageOwnerPass(int id,String cpass)  : ");
		e.printStackTrace();
	}
	return flag;
}
/* Change Data Owner Password (Ends) */

/* Getting The Data Owner Details (Starts) */
public ResultSet getDataOwners()
{
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		resultSet = statement.executeQuery("select * from m_owner");
	}
	catch(Exception e)
	{
		System.out.println("Exception in adminDAO-->getDataOwners(): ");
		e.printStackTrace();
	}
	return resultSet;
}

/* Getting The Data Owner Details (Ends) */

/* Add Data Owner Details (Starts) */

public boolean addDataOwnerDetails(String username, String password, String name,String address,String rnsKey,String desKey) 
{
	boolean flag=false;
	String sql = "";
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		sql = "insert into m_owner (ownerid,password,name,email,do_key1,do_key2) values('"+username+"','"+password+"','"+name+"','"+address+"','"+rnsKey+"','"+desKey+"')";
		System.out.println("******* Add Data Owner Details *********\n");
		System.out.println(sql);
		int i=statement.executeUpdate(sql);
		if(i!=0)
		{
			flag=true;
		}
		System.out.println("Data Owner Registeration Status : "+flag);
	}
	catch(Exception e)
	{
		System.out.println("Opp's Error is in AdminDAO-addDataOwnerDetails() : ");
		e.printStackTrace();
	}
	return flag;
}

/* Add Data Owner Details (Ends) */

/* Getting Specific Data Owner Details (Starts) */
public static ResultSet getSpecificDataOwnerDetails(int id)
{
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		resultSet = statement.executeQuery("select * from m_owner where id='"+id+"'");
	}
	catch(Exception e)
	{
		System.out.println("Exception in adminDAO-->getSpecificDataOwnerDetails(int id): ");
		e.printStackTrace();
	}
	return resultSet;
}
/* Getting Specific Data Owner Details (Ends) */

/* Edit Data Owner Details (Starts) */
public boolean editDataOwnerDetails(String [] s) 
{
	boolean flag=false;
	String sql = "";
	try
	{   
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		sql = "update m_owner set name='"+s[1]+"',email='"+s[2]+"' where id='"+s[0]+"'";
		System.out.println("******* Update Data Owner Info *********");
		System.out.println(sql);
		int i=statement.executeUpdate(sql);
		if(i!=0)
		{
			flag=true;
		}
		System.out.println("Update Data Owner Status : "+flag);
	}
	catch(Exception e)
	{
		System.out.println("Opp's Error is in AdminDAO-editDataOwnerDetails(String [] s) :");
		e.printStackTrace();
	}
	return flag;
}
/* Edit Data Owner Details (Ends) */

/* Delete Data Owner Details (Starts) */
public boolean deleteDataOwnerDetails(int dataWonerId) 
{
	boolean flag=false;
	String sql = "";
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		sql = "delete from m_owner where id='"+dataWonerId+"'";
		System.out.println("******* Delete Data Owner Details Info *********");
		System.out.println(sql);
		int i=statement.executeUpdate(sql);
		if(i!=0)
		{
			flag=true;
		}
		System.out.println("Delete Data Owner Details Status : "+flag);
	}
	catch(Exception e)
	{
		System.out.println("Opp's Error is in Admin-deleteDataOwnerDetails(int userId) :" );
		e.printStackTrace();
	}
	return flag;
}
/* Delete Data Owner Details (Ends) */

/* Get DataOwnerId By username(Starts)*/	

public int getDataOwnerId(String username)
{
	int id=0;
	try
	{
		DAO dao=DAO.getInstance();
		connection=dao.connector();
		statement = connection.createStatement();
		resultSet = statement.executeQuery("select id from m_owner where ownerid='"+username+"'");
		while(resultSet.next())
		{
			id=resultSet.getInt(1);
		}
		System.out.println("Data OwnerId["+username+"] : "+id);
	}
	catch(Exception e)
	{
		System.out.println("Exception in AdminDAO-->getDataOwnerId(String username): ");
		e.printStackTrace();
	}
	return id;
}

/* Get DataOwnerId By username(Starts))*/	
	
}
