package com.cedars.util.rtf2pdf;

import java.util.Dictionary;

import org.apache.commons.codec.binary.Hex;

import com.lowagie.text.Image;

class PictureDestination implements Destination
{
	long width, height, width_goal, height_goal;
	int fileType;
	Image image;
	RTF2PDFReader _instance;
	String hexBlob;
	
	public PictureDestination(RTF2PDFReader instance)
	{
		this._instance = instance;
		width = 0;
		height = 0;
		width_goal = 0;
		height_goal = 0;
		fileType = 0;
		hexBlob = "";
	}


	public void close()
	{
		_instance.warning("Done reading picture.");
		try{
			//byte[] data = hexStringToByteArray(hexBlob);
			//if(hexBlob.length()%2 == 1)
			//	hexBlob = "0x0" + hexBlob.substring(2);
			
			char[] charArr= hexBlob.toCharArray();
			byte[] byteArray = Hex.decodeHex(charArr);
			
			image = Image.getInstance(hexBlob.getBytes());
			
			
			_instance.targetPDF.add(image);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean handleKeyword(String keyword, int parameter)
	{
		if (keyword.equals("wmetafile"))
			fileType = parameter;
		else if (keyword.equals("picw"))
			width = parameter;
		else if (keyword.equals("pich"))
			height = parameter;
		else if (keyword.equals("picwgoal"))
			width_goal = parameter;
		else if (keyword.equals("pichgoal"))
			height_goal = parameter;
		else
			return false;

		return true;
	}
	
	public void handleText(String text){
		hexBlob += text;
	}
	
	private byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
	
	public void handleBinaryBlob(byte[] data) {}
	
	/*  don't understand any parameterless keywords */
	public boolean handleKeyword(String keyword) { return false; }
	
	public void begingroup() {}
	public void endgroup(Dictionary oldState) {}

}
