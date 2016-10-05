package com.rumbo.favs.data.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MyMath {

	public static String getRoundedFloat(float number){
		String aux = "";
		
		if (number > 0){			

			String separator = ",";
			String pattern = "00";
			String patternBis = "0";
			
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

		    dfs.setDecimalSeparator(',');
		    dfs.setGroupingSeparator('.');
		    
		    df.setDecimalFormatSymbols(dfs);
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			
			aux = df.format(number);
			
			String[] parts = aux.split(separator);
			
			if (parts.length > 0){
				if (pattern.equals(parts[1]) || patternBis.equals(parts[1])){
					return parts[0];
				}else{
					return aux;
				}	
			}						
		}
		return aux;	
	}	
		
}
