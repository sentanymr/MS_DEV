package com.cedars.util;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.lowagie.text.pdf.PdfReader;

public class PdfCompare {

	public static void main(String args[]){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		java.util.Date startTime = Calendar.getInstance().getTime();
		System.out.println(">>> Starting Comparison at: "+ sdf.format(startTime));
		
		String srcDir1 = "C:/temp/PDFCompare/from_centricity";	// 16k
		String srcDir2 = "C:/temp/PDFCompare/from_onbase";		// 10k
		//String srcDir1 = "//cshsefs3/System_Integration/System_Integration/OnBase/EHS_Delta_2/Documents/compare/from_centricity";	// 16k
		//String srcDir2 = "//cshsefs3/System_Integration/System_Integration/OnBase/EHS_Delta_2/Documents/compare/from_onbase";		// 10k
		
		HashMap<String,String> map1_docID = new HashMap<String,String>();   
		HashMap<String,String> map2_docID = new HashMap<String,String>();
		
		HashMap<String,String> map1_InstID = new HashMap<String,String>();   
		HashMap<String,String> map2_InstID = new HashMap<String,String>();
		
		HashMap<String,String> map1_createDate = new HashMap<String,String>();   
		HashMap<String,String> map2_createDate = new HashMap<String,String>();
		
		
		try{
			/***
			PdfReader reader = new PdfReader(srcDir1 + "/" + "BERNARD_BETTY_JEAN_1923-07-01_2010-09-01_5d1.pdf");
		    if (reader.getMetadata() == null) {
		    	System.out.println("No XML Metadata.");
		    } else {
		    	String rawMetaData = new String(reader.getMetadata());
		    	System.out.println("XML Metadata: " + rawMetaData);
		    	String docID = PdfCompare.extractDocumentID(rawMetaData);
		    	String instanceID = PdfCompare.extractInstanceID(rawMetaData);
		    	
		    }
			****///
			
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
				    //System.out.println("fileName1 = "+fileName1);
			    	File file = new File(srcDir1 + "/" + fileName1);
			    	
				    PdfReader reader = new PdfReader(srcDir1 + "/" + fileName1);
				    if (reader.getMetadata() == null) {
				    	System.out.println("No XML Metadata|"+fileName1);
				    } else {
				    	String rawMetaData = new String(reader.getMetadata());
				    	//System.out.println("XML Metadata: " + rawMetaData);
				    	String docID = PdfCompare.extractDocumentID(rawMetaData);
				    	String instanceID = PdfCompare.extractInstanceID(rawMetaData);
				    	String createDate = PdfCompare.extractCreateDate(rawMetaData);
				    	if(!docID.isEmpty()){
				    		//map2.put(docID +","+instanceID, fileName2);
				    		map1_docID.put(docID, fileName1);
				    	}else{
				    		System.out.println("MISSING DOCID|"+fileName1);
				    	}
				    	
				    	if(!instanceID.isEmpty()){
				    		map1_InstID.put(instanceID, fileName1);
				    	}else{
				    		//System.out.println("MISSING INSTID|"+fileName1);
				    	}
				    	
				    	if(!createDate.isEmpty()){
				    		map1_createDate.put(createDate, fileName1);
				    	}
				    	
				    	//if{
				    	//	System.out.println("ERROR - MISSING ID|"+fileName1);
				    	//}
				    	
				    	//create dump output: CreateDate|DocumentID|InstanceID|FileName|FileSize
				    	//System.out.println(PdfCompare.extractCreateDate(rawMetaData)+"|"+fileName1+"|"+file.length());
				    }
				    counter++;
				    if(counter % 1000 == 0){
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
				    //System.out.println("fileName2 = "+fileName2);
			    	File file = new File(srcDir2 + "/" + fileName2);
				    PdfReader reader = new PdfReader(srcDir2 + "/" + fileName2);
				    if (reader.getMetadata() == null) {
				    	System.out.println("No XML Metadata.");
				    } else {
				    	String rawMetaData = new String(reader.getMetadata());
				    	//System.out.println("XML Metadata: " + rawMetaData);
				    	String docID = PdfCompare.extractDocumentID(rawMetaData);
				    	String instanceID = PdfCompare.extractInstanceID(rawMetaData);
				    	String createDate = PdfCompare.extractCreateDate(rawMetaData);
				    	if(!docID.isEmpty()){
				    		//map2.put(docID +","+instanceID, fileName2);
				    		map2_docID.put(docID, fileName2);
				    	}else{
				    		System.out.println("MISSING UUID|"+fileName2);
				    	}
				    	if(!instanceID.isEmpty()){
				    		map2_InstID.put(instanceID, fileName2);
				    	}else{
				    		//System.out.println("MISSING INSTID|"+fileName2);
				    	}
				    	
				    	if(!createDate.isEmpty()){
				    		map2_createDate.put(createDate, fileName2);
				    	}
				    	
				    	//if{
				    	//	System.out.println("ERROR - MISSING ID|"+fileName2);
				    	//}
				    	
				    	//create dump output: CreateDate|DocumentID|InstanceID|FileName|FileSize
				    	//System.out.println(PdfCompare.extractCreateDate(rawMetaData)+"|"+PdfCompare.extractDocumentID(rawMetaData)+"|"+PdfCompare.extractInstanceID(rawMetaData)+"|"+fileName2+"|"+file.length());
				    	//System.out.println(PdfCompare.extractCreateDate(rawMetaData)+"|"+fileName2+"|"+file.length());	
				    }
				    
				    counter++;
				    if(counter % 1000 == 0){
				    	//System.out.println("["+counter+"]");
				    }
			    }
			}		
			System.out.println(">>> MAP2 Initalized");
			//System.out.println(map2);
			
			System.out.println(">>> Starting MAP Comparison: DOC-ID ...");
			System.out.println(">>> STATUS | UUID | CENTRICITY-FILENAME | ONBASE-FILENAME ...");
			
			// Compare MAP1 to MAP2
			Iterator<String> iterMap1 = map1_docID.keySet().iterator();
			while(iterMap1.hasNext()){
				String key1 = iterMap1.next();
				
				if(map2_docID.containsKey(key1)){
					System.out.println("FOUND MATCH: UUID|"+key1+"|"+map1_docID.get(key1)+"|"+map2_docID.get(key1));
				}else{
					//System.out.println("NO MATCH FOUND: DOCID|"+key1+"|"+map1_docID.get(key1));
				}
			}
			
			/*
			System.out.println(">>> Starting MAP Comparison: INST-ID ...");
			System.out.println(">>> STATUS | UUID | CENTRICITY-FILENAME | ONBASE-FILENAME ...");
			// Compare MAP1 to MAP2
			Iterator<String> iterMap2 = map1_InstID.keySet().iterator();
			while(iterMap2.hasNext()){
				String key1 = iterMap2.next();
				
				if(map2_InstID.containsKey(key1)){
					System.out.println("FOUND MATCH: INSTID|"+key1+"|"+map1_InstID.get(key1)+"|"+map2_InstID.get(key1));
				}else{
					//System.out.println("NO MATCH FOUND: INSTID|"+key1+"|"+map1_InstID.get(key1));
				}
			}
			*/
			/** NOT RELIABLE
			System.out.println(">>> Starting MAP Comparison: CreateDate ...");
			// Compare MAP1 to MAP2
			Iterator<String> iterMap3 = map1_createDate.keySet().iterator();
			while(iterMap3.hasNext()){
				String key1 = iterMap3.next();
				
				if(map2_createDate.containsKey(key1)){
					System.out.println("FOUND MATCH: CREATEDATE|"+key1+"|"+map1_createDate.get(key1)+"|"+map2_createDate.get(key1));
				}else{
					//System.out.println("NO MATCH FOUND: CREATEDATE|"+key1+"|"+map1_createDate.get(key1));
				}
			}**/
			
			System.out.println("=========================================================");
			java.util.Date endTime = Calendar.getInstance().getTime();
			System.out.println(">>> Started Comparison at: "+ sdf.format(startTime));
			System.out.println(">>> Stopped Comparison at: "+ sdf.format(endTime));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String extractDocumentID(String raw){
		String val = "";
		int startINDEX = raw.indexOf("<xmpMM:DocumentID>");
		int endINDEX = raw.indexOf("</xmpMM:DocumentID>");
		if(startINDEX != -1){
			val = raw.substring(startINDEX+18,endINDEX);
		}
		return val;
	}
	
	public static String extractInstanceID(String raw){
		String val = "";
		int startINDEX = raw.indexOf("<xmpMM:InstanceID>");
		int endINDEX = raw.indexOf("</xmpMM:InstanceID>");
		if(startINDEX != -1){
			val = raw.substring(startINDEX+18,endINDEX);
		}
		return val;
	}
	
	public static String extractCreateDate(String raw){
		String val = "";
		int startINDEX = raw.indexOf("<xmp:CreateDate>");
		int endINDEX = raw.indexOf("</xmp:CreateDate>");
		if(startINDEX != -1){
			val = raw.substring(startINDEX+16,endINDEX);
		}
		return val;
	}
	
	
	
}
