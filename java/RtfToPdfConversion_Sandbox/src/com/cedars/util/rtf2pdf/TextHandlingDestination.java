package com.cedars.util.rtf2pdf;

import javax.swing.text.AttributeSet;

/** RTFReader.TextHandlingDestination provides basic text handling
 *  functionality. Subclasses must implement: <dl>
 *  <dt>deliverText()<dd>to handle a run of text with the same
 *                       attributes
 *  <dt>finishParagraph()<dd>to end the current paragraph and
 *                           set the paragraph's attributes
 *  <dt>endSection()<dd>to end the current section
 *  </dl>
 */
abstract class TextHandlingDestination extends AttributeTrackingDestination implements Destination
{
	/** <code>true</code> if the reader has not just finished
	 *  a paragraph; false upon startup */
	boolean inParagraph;

	public TextHandlingDestination(RTF2PDFReader instance)
	{
		super(instance);
		inParagraph = false;
	}

	public void handleText(String text)
	{
		if (! inParagraph)
			beginParagraph();

		deliverText(text, currentTextAttributes());
	}

	abstract void deliverText(String text, AttributeSet characterAttributes);

	public void close()
	{
		if (inParagraph)
			endParagraph();

		super.close();
	}

	public boolean handleKeyword(String keyword)
	{
		if (keyword.equals("\r") || keyword.equals("\n")) {
			keyword = "par";
		}

		if (keyword.equals("par")) {
			//	    warnings.println("Ending paragraph.");
			endParagraph();
			return true;
		}

		if (keyword.equals("sect")) {
			//	    warnings.println("Ending section.");
			endSection();
			return true;
		}

		return super.handleKeyword(keyword);
	}

	protected void beginParagraph()
	{
		inParagraph = true;
	}

	protected void endParagraph()
	{
		AttributeSet pgfAttributes = currentParagraphAttributes();
		AttributeSet chrAttributes = currentTextAttributes();
		finishParagraph(pgfAttributes, chrAttributes);
		inParagraph = false;
	}

	abstract void finishParagraph(AttributeSet pgfA, AttributeSet chrA);

	abstract void endSection();
}
