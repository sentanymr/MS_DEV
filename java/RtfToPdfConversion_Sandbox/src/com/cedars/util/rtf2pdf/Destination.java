package com.cedars.util.rtf2pdf;

import java.util.Dictionary;

/** An interface (could be an entirely abstract class) describing
 *  a destination. The RTF reader always has a current destination
 *  which is where text is sent. 
 *
 *  @see RTFReader
 */
interface Destination {
	void handleBinaryBlob(byte[] data);
	void handleText(String text);
	boolean handleKeyword(String keyword);
	boolean handleKeyword(String keyword, int parameter);

	void begingroup();
	void endgroup(Dictionary oldState);

	void close();
}