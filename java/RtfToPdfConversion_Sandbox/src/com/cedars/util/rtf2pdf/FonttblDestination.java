package com.cedars.util.rtf2pdf;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Vector;

/** Reads the fonttbl group, inserting fonts into the RTFReader's
 *  fontTable dictionary. */
public class FonttblDestination implements Destination
{
	int nextFontNumber;
	Object fontNumberKey = null;
	String nextFontFamily;
	RTF2PDFReader _instance;
	
	public FonttblDestination(RTF2PDFReader instance){
		this._instance = instance;
	}

	public void handleBinaryBlob(byte[] data)
	{ /* Discard binary blobs. */ }

	public void handleText(String text)
	{
		int semicolon = text.indexOf(';');
		String fontName;

		if (semicolon > -1)
			fontName = text.substring(0, semicolon);
		else
			fontName = text;


		/* TODO: do something with the font family. */

		if (nextFontNumber == -1 
				&& fontNumberKey != null) {
			//font name might be broken across multiple calls
			fontName = _instance.fontTable.get(fontNumberKey) + fontName;
		} else {
			fontNumberKey = Integer.valueOf(nextFontNumber);
		}
		_instance.fontTable.put(fontNumberKey, fontName);

		nextFontNumber = -1;
		nextFontFamily = null;
		return;
	}

	public boolean handleKeyword(String keyword)
	{
		if (keyword.charAt(0) == 'f') {
			nextFontFamily = keyword.substring(1);
			return true;
		}

		return false;
	}

	public boolean handleKeyword(String keyword, int parameter)
	{
		if (keyword.equals("f")) {
			nextFontNumber = parameter;
			return true;
		}

		return false;
	}

	/* Groups are irrelevant. */
	public void begingroup() {}
	public void endgroup(Dictionary oldState) {}

	/* currently, the only thing we do when the font table ends is
   dump its contents to the debugging log. */
	public void close()
	{
		Enumeration nums = _instance.fontTable.keys();
		_instance.warning("Done reading font table.");
		while(nums.hasMoreElements()) {
			Integer num = (Integer)nums.nextElement();
			_instance.warning("Number " + num + ": " + _instance.fontTable.get(num));
		}
	}
}

