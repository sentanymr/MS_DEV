package com.cedars.util.rtf2pdf;

import java.util.Dictionary;

/** This data-sink class is used to implement ignored destinations
 *  (e.g. {\*\blegga blah blah blah} )
 *  It accepts all keywords and text but does nothing with them. */
public class DiscardingDestination implements Destination
{
	public void handleBinaryBlob(byte[] data)
	{
		/* Discard binary blobs. */
	}

	public void handleText(String text)
	{
		/* Discard text. */
	}

	public boolean handleKeyword(String text)
	{
		/* Accept and discard keywords. */
		return true;
	}

	public boolean handleKeyword(String text, int parameter)
	{
		/* Accept and discard parameterized keywords. */
		return true;
	}

	public void begingroup()
	{
		/* Ignore groups --- the RTFReader will keep track of the
   current group level as necessary */
	}

	public void endgroup(Dictionary oldState)
	{
		/* Ignore groups */
	}

	public void close()
	{
		/* No end-of-destination cleanup needed */
	}
}