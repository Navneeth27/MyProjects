
package com.helperclass;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.dao.*;
 
public class SerializeToDatabase
{
  
  public static Connection connection = null;
  public static DAO database = DAO.getInstance();
  
  
  
  private static final String SQL_SERIALIZE_OBJECT = "Update m_config set key_date=?,public_key=?,master_s_key=?,des_secrete_key=?,no_of_clouds=?";
  private static final String SQL_DESERIALIZE_OBJECT = "SELECT public_key FROM m_config";
  private static final String SQL_DESERIALIZE_OBJECT1 = "SELECT master_s_key FROM m_config";
  private static final String SQL_DESERIALIZE_OBJECT2 = "SELECT des_secrete_key FROM m_config";
  
 
  public static void serializeJavaObjectToDB(String date,Object pubKeyObj,Object prvKeyObj,Object desSecreteKey,int no_of_clouds) throws SQLException 
  {
     
	connection = database.connector();
    
	PreparedStatement pstmt = connection.prepareStatement(SQL_SERIALIZE_OBJECT);
 
    // just setting the class name
    pstmt.setString(1,date);
    pstmt.setObject(2,pubKeyObj);
    pstmt.setObject(3,prvKeyObj);
    pstmt.setObject(4,desSecreteKey);
    pstmt.setInt(5,no_of_clouds);
    pstmt.executeUpdate();
   /* 
    ResultSet rs = pstmt.getGeneratedKeys();
    int serialized_id = -1;
    if (rs.next()) {
      serialized_id = rs.getInt(1);
    }
    rs.close();
    */
    
    pstmt.close();
    //return serialized_id;
  }
 
  /**
   * To de-serialize a java object from database
   *
   * @throws SQLException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Object getPublicKey() throws SQLException, IOException,ClassNotFoundException 
  {
    
	connection = database.connector();
	PreparedStatement pstmt = connection.prepareStatement(SQL_DESERIALIZE_OBJECT);
    ResultSet rs = pstmt.executeQuery();
    rs.next();
 
    // Object object = rs.getObject(1);
 
    byte[] buf = rs.getBytes(1);
    ObjectInputStream objectIn = null;
    if (buf != null)
      objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
    Object deSerializedObject = objectIn.readObject();
    rs.close();
    pstmt.close();
    System.out.println("\n\n ***** De-Serialization Information *****\n");
    System.out.println("Java object de-serialized from database. Object: "+ deSerializedObject + " Classname: "+ deSerializedObject.getClass().getName()+"\n\n");
    return deSerializedObject;
  }
  
  public static Object getPrivateKey() throws SQLException, IOException,ClassNotFoundException 
  {
    
	connection = database.connector();
	PreparedStatement pstmt = connection.prepareStatement(SQL_DESERIALIZE_OBJECT1);
    ResultSet rs = pstmt.executeQuery();
    rs.next();
 
    // Object object = rs.getObject(1);
 
    byte[] buf = rs.getBytes(1);
    ObjectInputStream objectIn = null;
    if (buf != null)
      objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
 
    Object deSerializedObject = objectIn.readObject();
 
    rs.close();
    pstmt.close();
    System.out.println("\n\n ***** De-Serialization Information *****\n");
    System.out.println("Java object de-serialized from database. Object: "+ deSerializedObject + " Classname: "+ deSerializedObject.getClass().getName()+"\n\n");
    return deSerializedObject;
  }
  
  /* Getting DES Secrete Key From DB */
  
  public static Object getDESsecreteKey() throws SQLException, IOException,ClassNotFoundException 
  {
    
	connection = database.connector();
	PreparedStatement pstmt = connection.prepareStatement(SQL_DESERIALIZE_OBJECT2);
    ResultSet rs = pstmt.executeQuery();
    rs.next();
 
    // Object object = rs.getObject(1);
 
    byte[] buf = rs.getBytes(1);
    ObjectInputStream objectIn = null;
    if (buf != null)
      objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
 
    Object deSerializedObject = objectIn.readObject();
 
    rs.close();
    pstmt.close();
    System.out.println("\n\n ***** De-Serialization Information *****\n");
    System.out.println("Java object de-serialized from database. Object: "+ deSerializedObject + " Classname: "+ deSerializedObject.getClass().getName()+"\n\n");
    return deSerializedObject;
  }
 
  /**
   * Serialization and de-serialization of java object from mysql
   *
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws IOException
 * @throws NoSuchAlgorithmException 
   */
  /* Testing The Algorithm */
  public static void main(String args[]) throws ClassNotFoundException,SQLException, IOException, NoSuchAlgorithmException 
  {
        
		String userName = "kumar";
		
	    // a sample java object to serialize
	   
	    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
	    // serializing java object to mysql database
	  //  serializeJavaObjectToDB(userName, key);
	 
	    // de-serializing java object from mysql database
	    // Key objFromDatabase = (Key) (deSerializeJavaObjectFromDB(connection,userName));
	    SecretKey objFromDatabase = (SecretKey)(getPublicKey());
	    connection.close();
   }
  
  
      
}
