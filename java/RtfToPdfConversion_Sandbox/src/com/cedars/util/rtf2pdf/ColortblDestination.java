package com.cedars.util.rtf2pdf;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Vector;

/** Reads the colortbl group. Upon end-of-group, the RTFReader's
 *  color table is set to an array containing the read colors. */
class ColortblDestination implements Destination
{
	int red, green, blue;
	Vector proTemTable;
	RTF2PDFReader _instance;
	
	public ColortblDestination(RTF2PDFReader instance)
	{
		this._instance = instance;
		red = 0;
		green = 0;
		blue = 0;
		proTemTable = new Vector();
	}

	public void handleText(String text)
	{
		int index = 0;

		for (index = 0; index < text.length(); index ++) {
			if (text.charAt(index) == ';') {
				Color newColor;
				newColor = new Color(red, green, blue);
				proTemTable.addElement(newColor);
			}
		}
	}

	public void close()
	{
		int count = proTemTable.size();
		_instance.warning("Done reading color table, " + count + " entries.");
		_instance.colorTable = new Color[count];
		proTemTable.copyInto(_instance.colorTable);
	}

	public boolean handleKeyword(String keyword, int parameter)
	{
		if (keyword.equals("red"))
			red = parameter;
		else if (keyword.equals("green"))
			green = parameter;
		else if (keyword.equals("blue"))
			blue = parameter;
		else
			return false;

		return true;
	}

	/* Colortbls don't understand any parameterless keywords */
	public boolean handleKeyword(String keyword) { return false; }

	/* Groups are irrelevant. */
	public void begingroup() {}
	public void endgroup(Dictionary oldState) {}

	/* Shouldn't see any binary blobs ... */
	public void handleBinaryBlob(byte[] data) {}
}
