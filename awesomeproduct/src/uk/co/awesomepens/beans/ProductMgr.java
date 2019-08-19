package uk.co.awesomepens.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import org.apache.commons.collections4.Get;

import com.opencsv.CSVWriter;

import uk.co.awesomepens.model.Product;
import uk.co.awesomepens.model.ProductAttribute;
import uk.co.awesomepens.utils.CsvTools;


public class ProductMgr {
	
	private List<ProductAttribute> attributes;
	private List<String> header;
	private JTextArea outputArea;
	public final String NEWLINE = System.lineSeparator();
	private InputStream inputStream;

	public List<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ProductAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public ProductMgr(JTextArea outputArea) {
		this();
		this.outputArea = outputArea;
	}

	public ProductMgr(){
		attributes = new ArrayList<ProductAttribute>();
		attributes = InitializeAttributes();
		

		
	}
	
	public void createMetaCsv(String productFolderPath, Product product) {
		String csvFilePath = productFolderPath + File.separator +"meta.csv";
		
		header = new ArrayList<String>();
		header.add("Sku");
		header.add("Name");
		header.add("Published");
		header.add("Short description");
		header.add("Description");
		header.add("Stock");
		header.add("Weight (kg)");
		header.add("Length (cm)");
		header.add("Width (cm)");
		header.add("Height (cm)");
		header.add("Sale price");
		header.add("Regular price");
		header.add("Categories");
		header.add("Tags");
		header.add("Images");
		header.add("Meta: _wpm_gtin_code");
		
		
		for(int i = 0; i<product.getAttributes().size(); i++) {
			header.add("Attribute " + Integer.toString(i+1) + " name");
			header.add("Attribute " + Integer.toString(i+1) + " value(s)");
		}
		
		List<String> data = new ArrayList<String>();
		data.add(product.getSku());
		data.add(product.getName());
		data.add(product.getPublished()? "1" : "0");
		data.add(product.getShortDesciption());
		data.add(product.getDescription());
		data.add(Integer.toString(product.getStock()));
		data.add(product.getWeight() == 0 ? "" : Double.toString(product.getWeight()));
		data.add(product.getLength() == 0 ? "" : Double.toString(product.getLength()));
		data.add(product.getWidth() == 0 ? "" : Double.toString(product.getWidth()));
		data.add(product.getHeight() == 0 ? "" : Double.toString(product.getHeight()));
		data.add(product.getSalePrice() == 0 ? "" : Double.toString(product.getSalePrice()));
		data.add(product.getRegularPrice() == 0 ? "" : Double.toString(product.getRegularPrice()));
		data.add(product.getCategories());
		data.add(product.getTags());
		data.add(product.getImages());
		data.add(product.getGtin());
		
		LinkedHashMap<String, String> attributes = product.getAttributes();
		
		Iterator<Entry<String, String>> it = attributes.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			data.add(entry.getKey());
			data.add(entry.getValue());
		}
		
		CsvTools.writeSingleLine(csvFilePath, header.toArray(new String[0]), data.toArray(new String[0]));
		
	}
	
	// Return thumbnail image path by scanning the give fold and search for the jpg file end with s.jpg.
	public static String getThumbnailPath(String productDirPath){
		String imgPath = null;
		File[] files = new File(productDirPath).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        if(file.getName().endsWith("s.jpg")){
		        	
		        	imgPath = productDirPath + File.separator + file.getName();
		        	return imgPath;
		        }
		    }
		}		
		return imgPath;
	}
	
	public static Product loadProductFromCsv(String productDirPath) {
		Product product = null;
		String[] record = null;
		String metaPath = null;
		
		File[] files = new File(productDirPath).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if(file.getName().compareTo("meta.csv")==0) {
					metaPath = productDirPath + File.separator +"meta.csv";
					break;
				}
			}
		}
		
		if (metaPath != null) {
			product = new Product();
			record = CsvTools.readFirstRecord(metaPath);
			if (record != null) {
				product.setSku(record[0]);
				product.setName(record[1]);
				product.setPublished(Boolean.parseBoolean(record[2]));
				product.setShortDesciption(record[3]);
				product.setDescription(record[4]);
				product.setStock(Integer.parseInt(record[5]));
				product.setWeight(record[6].isEmpty()? 0 : Double.parseDouble(record[6]));
				product.setLength(record[7].isEmpty()? 0 : Double.parseDouble(record[7]));
				product.setWidth(record[8].isEmpty()? 0 : Double.parseDouble(record[8]));
				product.setHeight(record[9].isEmpty()? 0 : Double.parseDouble(record[9]));
				product.setSalePrice(record[10].isEmpty()? 0 : Double.parseDouble(record[10]));
				product.setRegularPrice(record[11].isEmpty()? 0 : Double.parseDouble(record[11]));
				product.setCategories(record[12]);
				product.setTags(record[13]);
				product.setImages(record[14]);
				product.setGtin(record[15]);
				
				LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
//				attributes.put(record[16], record[17]);
				
				if (record.length>16) {
					for (int i = 16; i < record.length; i+=2) {
						attributes.put(record[i], record[i+1]);
					}
				}
				
				product.setAttributes(attributes);
			}
			
		}
		
		return product;
	}
	
	public static Boolean hasAttributeItem(String str, String spliter, String item) {
		
		String[] items = str.split(spliter);
		
		for(String s : items) {
			if (s.compareTo(item)==0 )
				return true;
		}
		return false;
	}
	
	public static String getImageURLFromProductDir(String productDirPath)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		String prefixString = ""; //"https://awesomepens.co.uk/wp-content/uploads/" + year + "/" + String.format("%02d", month) + "/";
		String str = "";
		File[] files = new File(productDirPath).listFiles();
		List<String> imgNameStrings = new ArrayList<String>();
		for (File file : files) {
		    if (file.isFile()) {
		    	if(file.getName().endsWith("s.jpg"))
		    		str += prefixString + file.getName();
		        if(file.getName().endsWith(".jpg")){
		        	imgNameStrings.add(file.getName());
		        }
		    }
		}
		for (int i = 1; i<10; i++) {
			for (String img : imgNameStrings) {
				int index = 0;
				try {
					index = Integer.parseInt(String.valueOf(img.charAt(img.length()-5)));
				}
				catch (Exception e) {
				}
				if (index == i) {
					str += ", " + prefixString + img;
				}
			}
				
		}

		return str;
	}
	
	public static void renameProductImages(String productDirPath) {
		String name = getProductNameFromPath(productDirPath);
		name = name.replaceAll(" ", "-").replaceAll("---", "-");
		File[] files = new File(productDirPath).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	if(file.getName().endsWith(".jpg")) {
		    		String end = file.getName().substring(file.getName().length()-5);
		    		String newname = name + "-" + end;
		    		file.renameTo(new File(productDirPath + File.separator + newname));
		    	}
		    }
		}
	}
	
	
	public List<String> getAttributeNameList(List<ProductAttribute> attributes) {
		List<String> names = new ArrayList<String>();
		
		for (ProductAttribute attribute : attributes) {
			names.add(attribute.getName());
		}
		
		return names;		
	}
	
	public List<String> getAttributeValuesbyName(String name)
	{
		List<String> values = new ArrayList<String>();

		for (ProductAttribute attribute : this.attributes) {

			if (attribute.getName().compareTo(name)==0)
				return attribute.getValues();
		}
		
		return values;
	}
	
	public static String getProductNameFromPath(String path)
	{
		String[] strs = path.split( Pattern.quote(File.separator));
		return strs[strs.length-1];
	}
	
	public static String getCategoryFromPath(String path) {
		String str = path.substring(path.indexOf("Catalog"+File.separator), path.lastIndexOf(File.separator));
		return str.replaceAll("Catalog"+Pattern.quote(File.separator), "").replaceAll(Pattern.quote(File.separator), " > ").replaceAll("&", "&amp;");	
	}
	
	public void BatchProcessProducts(List<File> productFolders, String outputFolderPath) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = dateFormat.format(new Date());
		File folder = new File(outputFolderPath + File.separator + timeStamp);
		folder.mkdir();
		File imageFolder = new File(folder.getPath() + File.separator + "jpeg");
		imageFolder.mkdir();
		String outputFilePath = folder.getPath()+ File.separator +"product_import_"+ timeStamp + ".csv";

		
		try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(outputFilePath); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter outputWriter = new CSVWriter(outputfile);
	        
	        outputWriter.writeNext(header.toArray(new String[0]));
	        int processedNo = 0;
	        for(File dir : productFolders)
	        {
	        	File item = new File(dir.getPath() + File.separator + "meta.csv");
	        	
	        	if (item.exists()) {
		        	String[] data = CsvTools.readFirstRecord(item.getPath());
		        	outputWriter.writeNext(data);
		        	
		        	
		        	if(hasQualifiedImages(dir))
		        	{
		        		outputLine("Processing " + dir.getPath());
		        		//copy images
			    		File[] files = dir.listFiles();
			    		for (File file : files) {
			    		    if (file.isFile()) {
			    		    	if(file.getName().endsWith(".jpg")) {
			    		    		File outFile = new File(imageFolder + File.separator + file.getName());	    		    		
			    		    		Files.copy(file.toPath(), outFile.toPath());
			    		    		outputLine("Copy image: " + file.getName());
			    		    	}
			    		    }
			    		}
			    		
			    		for (File file : files) {
			    		    if (file.isFile()) {
			    		    	if(file.getName().endsWith(".flg"))
			    		    		file.delete();
			    		    }
			    		}
			    		
			    		File processedFlag = new File(dir + File.separator + timeStamp + ".flg");
			    		processedFlag.createNewFile();
			        	processedNo++;
			        	
			        	outputLine("Process completed! " + dir.getPath());
		        	}
		        	else {
		        		outputLine("No qulified images found in " + dir.getPath());
		        	}
	        	}
	        	else {
	        		outputLine("meta.csv not found in " + dir.getPath());
	        	}
	        	

	        	
	        }
	        
	        outputLine("===========");
	        outputLine( processedNo + " of " + productFolders.size() + " are processed!" );
	        outputLine("Output file: " + outputFilePath);
	  
	        // closing writer connection 
	        outputWriter.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	    	outputLine("Exception: " + e); 
	    } 
	}
	
	private List<ProductAttribute> InitializeAttributes()
	{
		
		Properties prop = new Properties();
		
		FileInputStream inputStream;
		
		String propFileName = "./attributes.properties";
		try {
		inputStream = new FileInputStream(propFileName);
		
		if (inputStream != null) {
			
				prop.load(inputStream);
		
			} 
		inputStream.close();
		} catch (Exception e) {
			outputLine("Exception: " + e);
		} 
		
		
//		String propFileName = "attributes.properties";
//
//		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//		try {
//		if (inputStream != null) {
//			
//				prop.load(inputStream);
//		
//			} else {
//				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//			}
//		} catch (Exception e) {
//			outputLine("Exception: " + e);
//		} finally {
//			try {
//				inputStream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				outputLine("Exception: " + e);
//			}
//		}
		
		List<String> names = (List<String>)Collections.list(prop.propertyNames());
        Collections.sort(names);
        
        List<String> values;
        
        for (String name : names) {
        	ProductAttribute productAttribute = new ProductAttribute();
        	productAttribute.setName(name);
        	
        	values = new ArrayList<String>();
        	String[] valuesStr = prop.getProperty(name).split(",");
			for (String value : valuesStr) {
				values.add(value.trim());
			}
			if (name == "Brand") {
				Collections.sort(values,String.CASE_INSENSITIVE_ORDER);
			}
			
			productAttribute.setValues(values);
			this.attributes.add(productAttribute);
        }
        
		
		return attributes;
	}
	
	public List<Integer> noMetaFoundIndices(List<File> productFolders){
		
		List<Integer> indices = new ArrayList<Integer>();
		
		Object[] folders = productFolders.toArray();
		
		for (int i = 0; i<folders.length; i++) {
//			boolean flag = false;
//			
//			File[] files = ((File)folders[i]).listFiles();
//			for (File file : files) {
//				if (file.isFile()) {
//					if(file.getName().compareTo("meta.csv")==0) {
//						flag = true;
//						break;
//					}
//				}
//			}
			boolean flag = hasMetaCSV((File)folders[i]);
			if (!flag)
				indices.add(i);
		}
		
		return indices;
	}
	
	
	
	public List<Integer> noImageFoundIndices(List<File> productFolders) {
		List<Integer> indices = new ArrayList<Integer>();
		Object[] folders = productFolders.toArray();
		
		for (int i = 0; i<folders.length; i++) {
//			int imageCount = 0;
//			boolean flag = false;
//			
//			File[] files = ((File)folders[i]).listFiles();
//			for (File file : files) {
//				if (file.isFile()) {
//					if(file.getName().endsWith(".jpg")) {
//						imageCount++;
//						if(file.getName().endsWith("s.jpg"))
//							flag = true;
//					}
//					
//				}
//			}
			boolean flag = hasQualifiedImages((File)folders[i]);
			if (!flag)
				indices.add(i);
		}
		
		return indices;
	}
	
	public List<Integer> hasExportedIndices(List<File> productFolders) {
		List<Integer> indices = new ArrayList<Integer>();
		Object[] folders = productFolders.toArray();
		
		for (int i = 0; i<folders.length; i++) {

			boolean flag = hasExported((File)folders[i]);
			if (flag)
				indices.add(i);
		}
		
		return indices;
	}
	
	public boolean hasMetaCSV(File productFolder)
	{
		boolean flag = false;
		
		File[] files = productFolder.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if(file.getName().compareTo("meta.csv")==0) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public boolean hasExported(File productFolder) {
		boolean flag = false;
		
		File[] files = productFolder.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if(file.getName().endsWith(".flg")) {
					flag = true;
					break;
				}
			}

		}
		
		return flag;
	}
	
	public boolean hasQualifiedImages(File folder) {
		boolean imageFlag = false;
		
		int imageCount = 0;
		boolean flag = false;
		
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if(file.getName().endsWith(".jpg")) {
					imageCount++;
					if(file.getName().endsWith("s.jpg"))
						flag = true;
				}
				
			}
		}
		if (flag && imageCount > 1)
			imageFlag = true;
		
		return imageFlag;
	}
	
	public List<File> getUnexportedProductPaths(String rootFolder){
		
		List<File> folders = new ArrayList<File>();
		
		seachProducts(new File(rootFolder), folders);
		
		return folders;
	}
	
	private void seachProducts(File parent, List<File> productFolders){
		
		if(hasChildFolder(parent)) {
			File[] childFolders = parent.listFiles();
			
			for(File child : childFolders) {
				if(child.isDirectory())
					seachProducts(child, productFolders);
			}
		}
		else {
			if(!hasExported(parent))
				productFolders.add(parent);
		}
			
		
	}
	
	private boolean hasChildFolder(File parent) {
		boolean flag = false;
		
		File[] files = parent.listFiles();
		
		for(File file : files) {
			if(file.isDirectory()) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	private void outputLine(String str) {
		this.outputArea.append(str + NEWLINE);
	}
}
