package com.cedars.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class PdfCompareBase64 {
	
	public String encodeFileToBase64Binary(File file) {

		//File originalFile = new File(filename);
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = Base64.getEncoder().encodeToString(bytes);
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return encodedBase64;
	}
	
	public static void main(String[] args){
		
		PdfCompareBase64 instance = new PdfCompareBase64();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		java.util.Date startTime = Calendar.getInstance().getTime();
		System.out.println(">>> Starting Comparison at: "+ sdf.format(startTime));
		
		String srcDir1 = "C:/temp/PDFCompare/from_centricity";	// 16k
		String srcDir2 = "C:/temp/PDFCompare/from_onbase";		// 10k
		//String srcDir1 = "//cshsefs3/System_Integration/System_Integration/OnBase/EHS_Delta_2/Documents/compare/from_centricity";	// 16k
		//String srcDir2 = "//cshsefs3/System_Integration/System_Integration/OnBase/EHS_Delta_2/Documents/compare/from_onbase";		// 10k
		
		HashMap<String,String> map1 = new HashMap<String,String>();   
		HashMap<String,String> map2 = new HashMap<String,String>();
		
		
		try{
			
			System.out.println(">>> Loading MAP1: "+ srcDir1 +" ...");
			Path sourceDir1 = FileSystems.getDefault().getPath(srcDir1);
			DirectoryStream<Path> stream1 = Files.newDirectoryStream(sourceDir1);
			Iterator<Path> iter1 = stream1.iterator();
			
			//load MAP1 with <(DocID + InstanceID),(Filename)> 
			int counter = 0;
			while (iter1.hasNext()) {
				Path file1 = iter1.next();
			    String fileName1 = file1.getFileName().toString();
			    
			    if(fileName1.contains(".pdf")){
				    System.out.println("fileName1 = "+fileName1);
			    	File file = new File(srcDir1 + "/" + fileName1);
			    	
			    	String base64 = instance.encodeFileToBase64Binary(file);
			    	map1.put(base64, fileName1);
			
			    	counter++;
				    if(counter % 10 == 0){
				    	break;
				    	//System.out.println("["+counter+"]");
				    }
			    }
			}
			System.out.println(">>> MAP1 Initalized");
			//System.out.println(map1);
			    
			System.out.println(">>> Loading MAP2: "+ srcDir2 +" ...");
			Path sourceDir2 = FileSystems.getDefault().getPath(srcDir2);
			DirectoryStream<Path> stream2 = Files.newDirectoryStream(sourceDir2);
			Iterator<Path> iter2 = stream2.iterator();

			//load MAP2 with <(DocID + InstanceID),(Filename)> 
			counter = 0;
			while (iter2.hasNext()) {
				Path file2 = iter2.next();
			    String fileName2 = file2.getFileName().toString();
			    
			    if(fileName2.contains(".pdf")){
				    System.out.println("fileName2 = "+fileName2);
			    	File file = new File(srcDir2 + "/" + fileName2);
			    	
			    	String base64 = instance.encodeFileToBase64Binary(file);
			    	map2.put(base64, fileName2);
				    
				    counter++;
				    if(counter % 10 == 0){
				    	break;
				    	//System.out.println("["+counter+"]");
				    }
			    }
			}		
			System.out.println(">>> MAP2 Initalized");
			//System.out.println(map2);
			
			System.out.println(">>> Starting MAP Comparison ...");
			
			// Compare MAP1 to MAP2
			Iterator<String> iterMap1 = map1.keySet().iterator();
			while(iterMap1.hasNext()){
				String key1 = iterMap1.next();
				
				if(map2.containsKey(key1)){
					System.out.println("FOUND MATCH|"+map1.get(key1)+"|"+map2.get(key1));
				}else{
					//System.out.println("NO MATCH FOUND: |"+key1+"|"+map1_docID.get(key1));
				}
			}
			
			System.out.println("=========================================================");
			java.util.Date endTime = Calendar.getInstance().getTime();
			System.out.println(">>> Started Comparison at: "+ sdf.format(startTime));
			System.out.println(">>> Stopped Comparison at: "+ sdf.format(endTime));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
