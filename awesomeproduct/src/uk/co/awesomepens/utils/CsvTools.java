package uk.co.awesomepens.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CsvTools {

	public static void writeSingleLine(String filePath, String[] header, String[] data) 
	{ 
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(filePath); 
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        // adding header to csv 
	        writer.writeNext(header); 
	  
	        // add data to csv 
	        writer.writeNext(data); 
	  
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	} 

	
	public static String[] readFirstRecord(String filePath){
		String[] nextRecord = null;
		try {
	            Reader reader = Files.newBufferedReader(Paths.get(filePath));
	            CSVReader csvReader = new CSVReader(reader);
	         
	            // Reading Records One by One in a String array
	            csvReader.skip(1);
	            nextRecord = csvReader.readNext();

	            csvReader.close();
	            
	            
	    } 
		catch (IOException e) {
				// TODO: handle exception
			e.printStackTrace(); 
		}
		return nextRecord;
	}
	
 
}
