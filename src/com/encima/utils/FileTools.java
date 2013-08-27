package com.encima.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; 
import java.util.Properties;

public class FileTools {
	
	public static String readFileAsString(String filePath)	{
	    BufferedReader reader;
	    System.out.println(filePath);
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line, results = "";
		    while( ( line = reader.readLine() ) != null) {
		        results += line + " \n";
		    }
		    reader.close();
		    System.out.println(results);
		    return results;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void serializeRuleBase(Object drools) {
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream("drools.data");
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject(drools);
			obj_out.close();
			f_out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Drools deserializeRuleBase() {
		FileInputStream f_in;
		try {
			f_in = new FileInputStream("drools.data");
			File droolsFile = new File("drools.data");
			if(droolsFile.exists()) {
				ObjectInputStream obj_in = new ObjectInputStream (f_in);
				Object obj = obj_in.readObject();
				if(obj instanceof Drools) {
					obj_in.close();
					return (Drools) obj;
				}
			}else{
				return DroolsTools.initDrools();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public static Properties getProperties() {
		Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 			return prop;
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		return null;
        }
	}
}
