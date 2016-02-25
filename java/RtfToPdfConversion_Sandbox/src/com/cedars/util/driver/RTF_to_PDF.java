package com.cedars.util.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import com.cedars.util.rtf2pdf.RTF2PDFReader;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class RTF_to_PDF {
	
	public void convert(String rtfFilePath, StyledDocument doc, String pdfFilePath) throws DocumentException, IOException
	{
		
		//open the source rtf file
		FileInputStream fis = new FileInputStream(rtfFilePath);
		
		//create a document for the pdf
		com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
		
		//create a pdf writer with the pdf file
		PdfWriter pdfWriter = PdfWriter.getInstance(document,	new FileOutputStream(pdfFilePath));
		
		//set the char space ratio
		//pdfWriter.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
		
		document.open();
		
		//create the editor to write from rtf to pdf
		RTF2PDFReader rdr = new RTF2PDFReader(doc,document);
	    rdr.readFromStream(fis);
	    rdr.close();
		
		document.close();
	}
	
	public void convertSingle(String srcPath, String srcFile) {
	
		JTextPane tempTextPane = new JTextPane();
		
		try {
			
		    if(srcFile.contains(".rtf")){
			    String sDestPDF = srcFile.replaceAll(".rtf", ".pdf");

			    System.out.println("\nProceeding to convert >> "+srcFile);
			    
			    //create conv directory if does not exist
			    String sDestOutFile = srcPath + sDestPDF;
			    convert(srcPath + "/" + srcFile, (StyledDocument) tempTextPane.getStyledDocument(), sDestOutFile);
	
			    System.out.println("File Successfully converted.!!");
				System.out.println("Saved as >> "+sDestOutFile);
		    }
										
		} catch (DocumentException e) {
			System.out.println(e);
		} catch (IOException ie) {
			System.out.println(ie);
		}
		
	}
	
	public void convertBatch(String srcDir, String destDir) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		java.util.Date startTime = Calendar.getInstance().getTime();
		System.out.println(">>> Starting Conversion at: "+ sdf.format(startTime));
		int conversionCount = 0;
		
		
		JTextPane tempTextPane = new JTextPane();
		try {
			
			Path sourceDir = FileSystems.getDefault().getPath(srcDir);
			DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir);
			Iterator<Path> iter = stream.iterator();
			while (iter.hasNext()) {
			    Path srcFile = iter.next();
			 
			    String sSrcRTF = srcFile.getFileName().toString();
		        
			    if(sSrcRTF.contains(".rtf")){
				    String sDestPDF = sSrcRTF.replaceAll(".rtf", ".pdf");
	
				    System.out.println("\nProceeding to convert >> "+srcFile.getFileName());
				    
				    String sDestOutFile = destDir + sDestPDF;
				    
				    //create conv directory if does not exist
				    createDirectory(destDir);
				    convert(srcDir + sSrcRTF, (StyledDocument) tempTextPane.getStyledDocument(), sDestOutFile);
							
					System.out.println("File Successfully converted.!!");
					System.out.println("Saved as >> "+sDestOutFile);
					conversionCount++;
			    }
			}
										
		} catch (DocumentException e) {
			System.out.println(e);
		} catch (IOException ie) {
			System.out.println(ie);
		}
		
		System.out.println("=========================================================");
		java.util.Date endTime = Calendar.getInstance().getTime();
		System.out.println(">>> Started Conversion at: "+ sdf.format(startTime));
		System.out.println(">>> Stopped Conversion at: "+ sdf.format(endTime));
		
		System.out.println(">>> Number of Files Converted Successfully = " + conversionCount);

		long difference = endTime.getTime() - startTime.getTime(); 
		System.out.println(">>> Total Conversion Time (seconds) = "+ difference/1000);
		
	}
	
	private void createDirectory(String dir){
		
		File theDir = new File(dir);
		
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		        se.printStackTrace();
		    }     
		}
	}
	
	public static void main(String[] args){

		RTF_to_PDF instance = new RTF_to_PDF();
		
		instance.convertSingle("C:/temp/RTFConvTest","Example2-Logo-simple.rtf");
		//instance.convertSingle("C:/temp/RTFConvTest","Example2-Logo.rtf");
		
		//instance.convertSingle("C:/temp/EHS_LettersConvToOnbase/","000000888_1416492532550650.rtf");
		//instance.convertBatch("C:/temp/EHS_LettersConvToOnbase/NewBatch1", "C:/temp/EHS_LettersConvToOnbase/NewBatch1_PDF" );
		
	}
	
}
