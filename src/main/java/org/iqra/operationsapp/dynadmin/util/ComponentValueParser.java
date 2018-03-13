/**
 * 
 */
package org.iqra.operationsapp.dynadmin.util;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * @author Abdul
 * 08-Mar-2018
 * 
 */
public class ComponentValueParser {
	
	public static String getComponetValue(String responseBody){
		String componentValue = null;
		
        BufferedReader reader = new BufferedReader(new StringReader(responseBody));
        
        try{
            String line = reader.readLine();
            while (line != null) {
                if(line.contains("<h3>Value</h3>"))
                {
                	line = reader.readLine();
                	componentValue = line;
                	line = null;
                }
                line = reader.readLine();
            }
        }catch(IOException ioe){
        	System.out.println(ioe);
        }
        return componentValue;
	}

}
