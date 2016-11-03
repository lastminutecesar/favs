package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class GenericDao {

	/**
	 * From csvFile turn the information into dom object
	 * 
	 * @param csvFile
	 */
	protected void csvToObject(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					processLine(line.split(splitBy));					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	protected abstract void processLine(String[] line);
	
}
