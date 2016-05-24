package zJunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
 
public class Txt2PDF {
 
	public void convert(String rtfFilePath, StyledDocument doc, String pdfFilePath) throws DocumentException, IOException
	{
		
		
		//create a document for the pdf
		com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
		
		//create a pdf writer with the pdf file
		PdfWriter.getInstance(document,	new FileOutputStream(pdfFilePath));
		
		//set the char space ratio
		//pdfWriter.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
		
		document.open();
		
		//BaseFont bf1 = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
        BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font font1 = new Font(bf1, 8);
        
        FileReader fr = new FileReader(rtfFilePath);
        BufferedReader br = new BufferedReader(fr);
     
        String text = "";
        while((text = br.readLine())!=null)
        {	
        	text = text + "\n";
        	document.add(new Paragraph(text,font1));
  
        }
        br.close();
        document.close();
	
	}
	
	public void convertSingle(String srcPath, String srcFile) {
		
		JTextPane tempTextPane = new JTextPane();
		
		try {
			
		    if(srcFile.contains(".txt")){
			    String sDestPDF = srcFile.replaceAll(".txt", ".pdf");

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
		int errorCount = 0;
		
		
		JTextPane tempTextPane = new JTextPane();
		try {
			
			Path sourceDir = FileSystems.getDefault().getPath(srcDir);
			DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir);
			Iterator<Path> iter = stream.iterator();
			while (iter.hasNext()) {
			    Path srcFile = iter.next();
			 
			    String sSrcFile = srcFile.getFileName().toString();
		        
			    if(sSrcFile.contains(".txt") && (!sSrcFile.contains("drv_file"))){
				    String sDestPDF = sSrcFile.replaceAll(".txt", ".pdf");
	
				    System.out.println("\nProceeding to convert >> "+srcFile.getFileName());
				    
				    String sDestOutFile = destDir + sDestPDF;
				    
				    //create conv directory if does not exist
				    createDirectory(destDir);
				    try{
				    	convert(srcDir + sSrcFile, (StyledDocument) tempTextPane.getStyledDocument(), sDestOutFile);
				    	System.out.println("File Successfully converted.!!");
						System.out.println("Saved as >> "+sDestOutFile);
						conversionCount++;
				    }catch(Exception e){
				    	System.out.println(">>>>!!!! ERROR: Exception !!!! Skipping File Name = "+srcFile.getFileName());
						errorCount++;
						
						e.printStackTrace();

				    	// copy the RTF into an Error directory for later processing
				    	System.out.println("\nCopying Errored TXT file to Error Dir >> "+srcFile.getFileName());
				    	Path srcPath = FileSystems.getDefault().getPath(srcDir+"/"+srcFile.getFileName());
				    	createDirectory(srcDir+"/error/");
				    	Path destPath = FileSystems.getDefault().getPath(srcDir+"/error/"+srcFile.getFileName());
				    	java.nio.file.Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);		
				    }
			    }else if(sSrcFile.contains("drv_file")){
			    	try{
				    	System.out.println("\nProceeding to copy Driver File >> "+srcFile.getFileName());
				    	Path srcPath = FileSystems.getDefault().getPath(srcDir+"/"+srcFile.getFileName());
				    	Path destPath = FileSystems.getDefault().getPath(destDir+"/"+srcFile.getFileName());
				    	java.nio.file.Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
			    
			    	}catch(Exception e){
			    		System.out.println(">>>>!!!! ERROR !!!! Driver File Name = "+srcFile.getFileName());
				    	errorCount++;
			    	}
			    }
			}
										
		} catch (IOException ie) {
			System.out.println(ie);
		}
		
		System.out.println("=========================================================");
		java.util.Date endTime = Calendar.getInstance().getTime();
		System.out.println(">>> Started Conversion at: "+ sdf.format(startTime));
		System.out.println(">>> Stopped Conversion at: "+ sdf.format(endTime));
		
		System.out.println(">>> Number of Files Converted Successfully = " + conversionCount);
		System.out.println(">>> Number of Files Errored Out = " + errorCount);
		
		long difference = endTime.getTime() - startTime.getTime(); 
		System.out.println(">>> Total Conversion Time (seconds) = "+ difference/1000);
		
	}
	
	private void createDirectory(String dir){
		
		File theDir = new File(dir);
		
		if (!theDir.exists()) {
		    try{
		        theDir.mkdirs();
		    } 
		    catch(SecurityException se){
		        se.printStackTrace();
		    }     
		}
	}
	
    public static void main(String[] args) throws IOException, DocumentException {
        
    	Txt2PDF instance = new Txt2PDF();
    	
    	//=================================================================
		// ConvertBatch():
		// 1. Specify Directory Path with "/" at the end
		// 2. Destination Directory will be created if it does not exist
		//=================================================================
		
		//TEST
		//instance.convertBatch("C:/temp/test/TXT/", "C:/temp/test/PDF/" );
		
		//****** 2016/5/18 - txt doc conversion
    	
    	//instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_6/batch1/","Z:/txt_docs/doctype_6/batch1/");
    	//instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_8/batch1/","Z:/txt_docs/doctype_8/batch1/");
    	//instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_8/batch2/","Z:/txt_docs/doctype_8/batch2/");
    	//instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_8/batch3/","Z:/txt_docs/doctype_8/batch3/");
    	//instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_8/batch4/","Z:/txt_docs/doctype_8/batch4/");
    	instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_21/batch1/","Z:/txt_docs/doctype_21/batch1/");
    	instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_21/batch2/","Z:/txt_docs/doctype_21/batch2/");
    	instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_21/batch3/","Z:/txt_docs/doctype_21/batch3/");
    	instance.convertBatch("C:/temp/csmc_conv/batch_txt_doc2/doctype_21/batch4/","Z:/txt_docs/doctype_21/batch4/");
		
    }
}