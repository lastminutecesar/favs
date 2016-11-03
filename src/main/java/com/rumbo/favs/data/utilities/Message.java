package com.rumbo.favs.data.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to manage properties file
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class Message {

	private static final String MESSAGES_PROPERTIES = "messages.properties";	
	
	/**
	 * Get message from message property properties
	 *  
	 * @param property
	 * @return String
	 */
	public String getMessageProperty(String property){
		
		return getProperty(property, MESSAGES_PROPERTIES);
	}
	
	/**
	 * Get a property from file
	 * 
	 * Can't be static method because
	 * getClass() method(Object) isn't static
	 * 
	 * @param property
	 * @param file
	 * @return String 
	 */
	private String getProperty(String property, String file){
			
		InputStream input = null;
		
		try {
			Properties prop = new Properties();
			ClassLoader classLoader = getClass().getClassLoader();
			input = new FileInputStream(classLoader.getResource(file).getFile());

			if (input != null){
				prop.load(input);
				return prop.getProperty(property);
			}		
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return null;
	}
	
}