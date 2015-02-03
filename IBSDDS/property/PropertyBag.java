
package com.property;
import java.util.Properties;
import java.io.*;


public class PropertyBag 
{
	private static final String FILE_NAME = "Pro.Properties";
    private static Properties propFile = null;
    
    static 
    {
        loadProperties();
    }

    /**
     * @return the property
     */
    public static String getProperty(String key) 
    {
        if (propFile == null)
        {
            loadProperties();
        }
        
        return propFile.getProperty(key);
    }

    public static void loadProperties() 
    {
        if (propFile == null) 
        {
            propFile = new Properties();
            try 
            {
                propFile.load(new FileInputStream(FILE_NAME));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            catch (Exception exp) 
            {
                exp.printStackTrace();
            }
        }
    }
} 