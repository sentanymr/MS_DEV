package zJunk;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class FilesBySizeSearch {

	public void countFilesBySize(String srcDir, int filesize){
		
		int count = 0;
		
		try {
			System.out.println(">>> Begin Search >>>");
			Path sourceDir = FileSystems.getDefault().getPath(srcDir);
			DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir);
			Iterator<Path> iter = stream.iterator();
			while (iter.hasNext()) {
			    Path srcFile = iter.next();
			    
			    if(srcFile.toFile().length() <= filesize){
				    System.out.println("Found File|"+srcFile.getFileName()+"|"+"Filesize (bytes)|"+srcFile.toFile().length() );
				    count++;
			    }    	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(">>> Total Count for: "+srcDir+" >>> "+count);
	}
	
	
	public static void main(String[] args){
		
		FilesBySizeSearch search = new FilesBySizeSearch();
		//search.countFilesBySize("C:/temp/test-filesize/", 1263);
		
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch1", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch2", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch3", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch4", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch5", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch6", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch7", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch8", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch9", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch10", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch11", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch12", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch13", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch14", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch15", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch16", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch17", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch18", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch19", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch20", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch21", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch22", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch23", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch24", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch25", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch26", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch27", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch28", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch29", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch30", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch31", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch32", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch33", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch34", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch35", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch36", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch37", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch38", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch39", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch40", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch41", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch42", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch43", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch44", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch45", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch46", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch47", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch48", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch49", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch50", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch51", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch52", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part3/doctype_8/batch53", 1263);
		
		
		/*
	    search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch1", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch2", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch3", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch4", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch5", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch6", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch7", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch8", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch9", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch10", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch11", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch12", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch13", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch14", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch15", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch16", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch17", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch18", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch19", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch20", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch21", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch22", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch23", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch24", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch25", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch26", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch27", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch28", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch29", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch30", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch31", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch32", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch33", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch34", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch35", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch36", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch37", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch38", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch39", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch40", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch41", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch42", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch43", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch44", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch45", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch46", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch47", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch48", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch49", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch50", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch51", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch52", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch53", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch54", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch55", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch56", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch57", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch58", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch59", 1263);
		search.countFilesBySize("/procdatap/Onbase/CSMC/Part4/doctype_21/batch60", 1263);
		*/
	
		
	}
	
}
