/**
 * 
 */
package org.iqra.operationsapp.dynadmin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Abdul
 * 06-Mar-2018
 * 
 */
public class PropertyFileWriter {
	
public static void saveProperties(String fileName, String key, String value) {
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File dest;

		try {
			// Creating properties files from Java program
			PropertiesChanger props = new PropertiesChanger();
			File file = new File(fileName);
			if (file.exists() && !file.isDirectory()) {
				fis = new FileInputStream(file);
				props.load(fis);
			}
			else
			{
				String path = file.getPath();
				path=path.substring(0, path.lastIndexOf("\\"));
				dest = new File(path);
				dest.mkdirs();
			}
			props.setProperty(key, value);
			if (fis != null) {
				fis.close();
			}
			fos = new FileOutputStream(fileName);
			props.save(fos);
			if(fos!=null)
			{
				fos.close();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
