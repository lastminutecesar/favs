package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class GenericDao {

	/**
	 * Read csv file
	 * 
	 * @param csvFile
	 */
	protected void csvToObject(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			ClassLoader classLoader = getClass().getClassLoader();
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(classLoader.getResource(csvFile).getFile()));
				while ((line = br.readLine()) != null) {
					processLine(line.split(splitBy));					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	protected abstract void processLine(String[] line);
	
}
