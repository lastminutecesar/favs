package com.rumbo.favs.data.utilities;

import java.text.DecimalFormat;

public class MyMath {

	public static String getRoundedFloat(float number){
		
		System.out.println("NUMBER: " + number);
		
		String aux = "";
		
		if (number > 0){			

			String separator = ",";
			String pattern = "00";
			
			DecimalFormat df = new DecimalFormat();
			
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			
			aux = df.format(number);
			
			String[] parts = aux.split(separator);
			
			if (parts.length > 0){
				if (pattern.equals(parts[1])){
					return parts[0];
				}else{
					return aux;
				}	
			}						
		}
		return aux;	
	}	
		
}
