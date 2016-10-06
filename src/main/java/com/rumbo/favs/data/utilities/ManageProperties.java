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
public class ManageProperties {

	private final String MESSAGES_PROPERTIES = "messages.properties";	
	private final String CONFIG_PROPERTIES = "config.properties";
	private final String FILE_PROPERTIES = "files.properties";
		
	public static final String CSV_FILE_RESOURCE_FOLDER = "csvFileResourceFolder";
	
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
	 * Get property from config properties
	 * 
	 * @param property
	 * @return String
	 */
	public String getConfigProperty(String property){
		
		return getProperty(property, CONFIG_PROPERTIES);
	}
	
	/**
	 * Get property from file properties
	 * 
	 * @param property
	 * @return String
	 */
	public String getFilesProperty(String property){
		
		return getProperty(property, FILE_PROPERTIES);
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
		
		Properties prop = new Properties();		
		InputStream input = null;
		
		try {
			//Get file from resources folder
			ClassLoader classLoader = getClass().getClassLoader();
			input = new FileInputStream(classLoader.getResource(file).getFile());

			if (input != null){
				// Load a properties file
				prop.load(input);
				// Get properties value
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
