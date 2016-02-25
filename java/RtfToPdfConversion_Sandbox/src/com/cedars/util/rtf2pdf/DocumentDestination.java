package com.cedars.util.rtf2pdf;

import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;

/** RTFReader.DocumentDestination is a concrete subclass of
 *  TextHandlingDestination which appends the text to the
 *  StyledDocument given by the <code>target</code> ivar of the
 *  containing RTFReader.
 */
public class DocumentDestination extends TextHandlingDestination implements Destination
{
	public DocumentDestination(RTF2PDFReader instance)
	{
		super(instance);
		inParagraph = false;
	}
	
	public void deliverText(String text, AttributeSet characterAttributes)
	{
		//try {
		//	    target.insertString(target.getLength(),
		//				text,
		//				currentTextAttributes());

		//write into pdf
		//		System.out.println("TEXT Length >> "+text.length());
		currentTextAttributes();

		_instance.currentFont.setColor(_instance.currentColorFG);		


		Chunk c = new Chunk(text,_instance.currentFont);
		c.setBackground(_instance.currentColorBG);
		if(_instance.isUnderlined)
			c.setUnderline(0.5F,-1F);

		if(null == _instance.currentParagraph)
		{
			_instance.currentParagraph = new Paragraph();
			_instance.currentLeading = 0;

		}

		//set the leading space
		float leadingSpace = _instance.currentFont.size();		
		if(leadingSpace > _instance.currentLeading)
		{
			_instance.currentParagraph.setLeading(leadingSpace);
			_instance.currentLeading = leadingSpace;
		}


		//add the chunk to paragraph
		_instance.currentParagraph.add(c);



		//	}catch (DocumentException de) {
		//		// TODO Auto-generated catch block
		//		throw new InternalError(de.getMessage());
		//	}
	}

	public void finishParagraph(AttributeSet pgfAttributes,
			AttributeSet chrAttributes)
	{
		//	int pgfEndPosition = target.getLength();
		//	try {
		//	    target.insertString(pgfEndPosition, "\n", chrAttributes);
		//	    target.setParagraphAttributes(pgfEndPosition, 1, pgfAttributes, true);
		//	} catch (BadLocationException ble) {
		//	    /* This shouldn't be able to happen, of course */
		//	    /* TODO is InternalError the correct error to throw? */
		//	    throw new InternalError(ble.getMessage());
		//	}

		//add paragraph to pdf
		try {


			if(null == _instance.currentParagraph)
			{
				_instance.currentParagraph = new Paragraph("\n");			
			}


			if(null != pgfAttributes.getAttribute(StyleConstants.Alignment))
				_instance.currentParagraph.setAlignment(((Integer) pgfAttributes.getAttribute(StyleConstants.Alignment)).intValue());
			_instance.targetPDF.add(_instance.currentParagraph);
			_instance.currentParagraph = null;



		} catch (DocumentException e) {

			System.out.println("Error adding to pdf >> "+e.getMessage());
		}catch(Exception e)
		{
			System.out.println("Error adding to pdf >> "+e.getMessage());	
		}

	}

	public void endSection()
	{
		/* If we implemented sections, we'd end 'em here */
	}
}    