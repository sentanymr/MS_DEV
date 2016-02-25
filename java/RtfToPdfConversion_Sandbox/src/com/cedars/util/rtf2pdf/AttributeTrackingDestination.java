package com.cedars.util.rtf2pdf;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabStop;

import com.lowagie.text.Font;

/** RTFReader.TextHandlingDestination is an abstract RTF destination
 *  which simply tracks the attributes specified by the RTF control words
 *  in internal form and can produce acceptable AttributeSets for the
 *  current character, paragraph, and section attributes. It is up
 *  to the subclasses to determine what is done with the actual text. */
abstract class AttributeTrackingDestination implements Destination
{
	/** This is the "chr" element of _instance.parserState, cached for
	 *  more efficient use */
	MutableAttributeSet characterAttributes;
	/** This is the "pgf" element of _instance.parserState, cached for
	 *  more efficient use */
	MutableAttributeSet paragraphAttributes;
	/** This is the "sec" element of _instance.parserState, cached for
	 *  more efficient use */
	MutableAttributeSet sectionAttributes;

	RTF2PDFReader _instance;
	
	public AttributeTrackingDestination(RTF2PDFReader instance)
	{
		this._instance = instance;
		characterAttributes = rootCharacterAttributes();
		_instance.parserState.put("chr", characterAttributes);
		paragraphAttributes = rootParagraphAttributes();
		_instance.parserState.put("pgf", paragraphAttributes);
		sectionAttributes = rootSectionAttributes();
		_instance.parserState.put("sec", sectionAttributes);
	}

	abstract public void handleText(String text);

	public void handleBinaryBlob(byte[] data)
	{
		/* This should really be in TextHandlingDestination, but
		 * since *nobody* does anything with binary blobs, this
		 * is more convenient. */
		_instance.warning("Unexpected binary data in RTF file.");
	}

	public void begingroup()
	{
		AttributeSet characterParent = currentTextAttributes();
		AttributeSet paragraphParent = currentParagraphAttributes();
		AttributeSet sectionParent = currentSectionAttributes();

		/* It would probably be more efficient to use the 
		 * resolver property of the attributes set for
		 * implementing rtf groups,
		 * but that's needed for styles. */

		/* update the cached attribute dictionaries */
		characterAttributes = new SimpleAttributeSet();
		characterAttributes.addAttributes(characterParent);
		_instance.parserState.put("chr", characterAttributes);

		paragraphAttributes = new SimpleAttributeSet();
		paragraphAttributes.addAttributes(paragraphParent);
		_instance.parserState.put("pgf", paragraphAttributes);

		sectionAttributes = new SimpleAttributeSet();
		sectionAttributes.addAttributes(sectionParent);
		_instance.parserState.put("sec", sectionAttributes);
	}

	public void endgroup(Dictionary oldState)
	{
		characterAttributes = (MutableAttributeSet)_instance.parserState.get("chr");
		paragraphAttributes = (MutableAttributeSet)_instance.parserState.get("pgf");
		sectionAttributes   = (MutableAttributeSet)_instance.parserState.get("sec");
	}

	public void close()
	{
	}

	public boolean handleKeyword(String keyword)
	{
		if (keyword.equals("ulnone")) {
			return handleKeyword("ul", 0);
		}

		{
			Object item = RTF2PDFReader.straightforwardAttributes.get(keyword);
			if (item != null) {
				RTFAttribute attr = (RTFAttribute)item;
				boolean ok;

				switch(attr.domain()) {
				case RTFAttribute.D_CHARACTER:
					ok = attr.set(characterAttributes);
					break;
				case RTFAttribute.D_PARAGRAPH:
					ok = attr.set(paragraphAttributes);
					break;
				case RTFAttribute.D_SECTION:
					ok = attr.set(sectionAttributes);
					break;
				case RTFAttribute.D_META:
					_instance.mockery.backing = _instance.parserState;
					ok = attr.set(_instance.mockery);
					_instance.mockery.backing = null;
					break;
				case RTFAttribute.D_DOCUMENT:
					ok = attr.set(_instance.documentAttributes);
					break;
				default:
					/* should never happen */
					ok = false;
					break;
				}
				if (ok)
					return true;
			}
		}


		if (keyword.equals("plain")) {
			resetCharacterAttributes();
			return true;
		}

		if (keyword.equals("pard")) {
			resetParagraphAttributes();
			return true;
		}

		if (keyword.equals("sectd")) {
			resetSectionAttributes();
			return true;
		}

		return false;
	}

	public boolean handleKeyword(String keyword, int parameter)
	{
		boolean booleanParameter = (parameter != 0);

		if (keyword.equals("fc"))
			keyword = "cf"; /* whatEVER, dude. */

		if (keyword.equals("f")) {
			_instance.parserState.put(keyword, Integer.valueOf(parameter));
			return true;
		}
		if (keyword.equals("cf")) {
			_instance.parserState.put(keyword, Integer.valueOf(parameter));
			return true;
		}

		{
			Object item = RTF2PDFReader.straightforwardAttributes.get(keyword);
			if (item != null) {
				RTFAttribute attr = (RTFAttribute)item;
				boolean ok;

				switch(attr.domain()) {
				case RTFAttribute.D_CHARACTER:
					ok = attr.set(characterAttributes, parameter);
					break;
				case RTFAttribute.D_PARAGRAPH:
					ok = attr.set(paragraphAttributes, parameter);
					break;
				case RTFAttribute.D_SECTION:
					ok = attr.set(sectionAttributes, parameter);
					break;
				case RTFAttribute.D_META:
					_instance.mockery.backing = _instance.parserState;
					ok = attr.set(_instance.mockery, parameter);
					_instance.mockery.backing = null;
					break;
				case RTFAttribute.D_DOCUMENT:
					ok = attr.set(_instance.documentAttributes, parameter);
					break;
				default:
					/* should never happen */
					ok = false;
					break;
				}
				if (ok)
					return true;
			}
		}

		if (keyword.equals("fs")) {
			StyleConstants.setFontSize(characterAttributes, (parameter / 2));
			return true;
		}

		/* TODO: superscript/subscript */

		if (keyword.equals("sl")) {
			if (parameter == 1000) {  /* magic value! */
				characterAttributes.removeAttribute(StyleConstants.LineSpacing);
			} else {
				/* TODO: The RTF sl attribute has special meaning if it's
	   negative. Make sure that SwingText has the same special
	   meaning, or find a way to imitate that. When SwingText
	   handles this, also recognize the slmult keyword. */
				StyleConstants.setLineSpacing(characterAttributes,
						parameter / 20f);
			}
			return true;
		}

		/* TODO: Other kinds of underlining */

		if (keyword.equals("tx") || keyword.equals("tb")) {
			float tabPosition = parameter / 20f;
			int tabAlignment, tabLeader;
			Number item;

			tabAlignment = TabStop.ALIGN_LEFT;
			item = (Number)(_instance.parserState.get("tab_alignment"));
			if (item != null)
				tabAlignment = item.intValue();
			tabLeader = TabStop.LEAD_NONE;
			item = (Number)(_instance.parserState.get("tab_leader"));
			if (item != null)
				tabLeader = item.intValue();
			if (keyword.equals("tb"))
				tabAlignment = TabStop.ALIGN_BAR;

			_instance.parserState.remove("tab_alignment");
			_instance.parserState.remove("tab_leader");

			TabStop newStop = new TabStop(tabPosition, tabAlignment, tabLeader);
			Dictionary tabs;
			Integer stopCount;

			tabs = (Dictionary)_instance.parserState.get("_tabs");
			if (tabs == null) {
				tabs = new Hashtable();
				_instance.parserState.put("_tabs", tabs);
				stopCount = Integer.valueOf(1);
			} else {
				stopCount = (Integer)tabs.get("stop count");
				stopCount = Integer.valueOf(1 + stopCount.intValue());
			}
			tabs.put(stopCount, newStop);
			tabs.put("stop count", stopCount);
			_instance.parserState.remove("_tabs_immutable");

			return true;
		}

		if (keyword.equals("s") &&
				_instance.paragraphStyles != null) {
			_instance.parserState.put("paragraphStyle", _instance.paragraphStyles[parameter]);
			return true;
		}

		if (keyword.equals("cs") &&
				_instance.characterStyles != null) {
			_instance.parserState.put("characterStyle", _instance.characterStyles[parameter]);
			return true;
		}

		if (keyword.equals("ds") &&
				_instance.sectionStyles != null) {
			_instance.parserState.put("sectionStyle", _instance.sectionStyles[parameter]);
			return true;
		}

		return false;
	}

	/** Returns a new MutableAttributeSet containing the
	 *  default character attributes */
	protected MutableAttributeSet rootCharacterAttributes()
	{
		MutableAttributeSet set = new SimpleAttributeSet();

		/* TODO: default font */

		StyleConstants.setItalic(set, false);
		StyleConstants.setBold(set, false);
		StyleConstants.setUnderline(set, false);
		StyleConstants.setForeground(set, _instance.defaultColor());

		return set;
	}

	/** Returns a new MutableAttributeSet containing the
	 *  default paragraph attributes */
	protected MutableAttributeSet rootParagraphAttributes()
	{
		MutableAttributeSet set = new SimpleAttributeSet();

		StyleConstants.setLeftIndent(set, 0f);
		StyleConstants.setRightIndent(set, 0f);
		StyleConstants.setFirstLineIndent(set, 0f);

		/* TODO: what should this be, really? */
		set.setResolveParent(_instance.target.getStyle(StyleContext.DEFAULT_STYLE));

		return set;
	}

	/** Returns a new MutableAttributeSet containing the
	 *  default section attributes */
	protected MutableAttributeSet rootSectionAttributes()
	{
		MutableAttributeSet set = new SimpleAttributeSet();

		return set;
	}

	/**
	 * Calculates the current text (character) attributes in a form suitable
	 * for SwingText from the current parser state.
	 *
	 * @returns a new MutableAttributeSet containing the text attributes.
	 */
	MutableAttributeSet currentTextAttributes()
	{
		MutableAttributeSet attributes =
				new SimpleAttributeSet(characterAttributes);
		Integer fontnum;
		Integer stateItem;

		/* figure out the font name */
		/* TODO: catch exceptions for undefined attributes,
   bad font indices, etc.? (as it stands, it is the caller's
   job to clean up after corrupt RTF) */
		fontnum = (Integer)_instance.parserState.get("f");
		/* note setFontFamily() can not handle a null font */
		String fontFamily;
		if (fontnum != null){
			fontFamily = (String)_instance.fontTable.get(fontnum);

			//handle the pdf font
			//fontfamily
			int font = (fontFamily.equals(java.awt.Font.MONOSPACED))? Font.COURIER :
				(fontFamily.equals(java.awt.Font.SANS_SERIF))? Font.HELVETICA : Font.TIMES_ROMAN;

			//font size
			float size = 12F;

			try{
				size = ((Integer)attributes.getAttribute(StyleConstants.Size)).floatValue();

			}catch(Exception e)
			{
				System.out.println("Error getting size >> "+e.getMessage());
			}

			//font style
			int style = -1;

			try{
				boolean bold = ((Boolean) attributes.getAttribute(StyleConstants.Bold)).booleanValue();
				boolean italic = ((Boolean) attributes.getAttribute(StyleConstants.Italic)).booleanValue();
				style = (bold && italic) ? Font.BOLDITALIC : (bold) ? Font.BOLD : (italic) ? Font.ITALIC : -1;
			}catch(Exception e)
			{
				System.out.println("Error getting style >> "+e.getMessage());
			}

			_instance.currentFont = (style != -1) ? new Font(font,size,style) : new Font(font,size);
		}
		else
		{
			fontFamily = null;
			_instance.currentFont = new Font(Font.TIMES_ROMAN,12F);
		}

		if (fontFamily != null)
			StyleConstants.setFontFamily(attributes, fontFamily);
		else
			attributes.removeAttribute(StyleConstants.FontFamily);


		if (_instance.colorTable != null) {
			stateItem = (Integer)_instance.parserState.get("cf");
			
			if (stateItem != null && (stateItem.intValue() < _instance.colorTable.length)) {
				
				Color fg = _instance.colorTable[stateItem.intValue()];
				StyleConstants.setForeground(attributes, fg);

				//System.out.println("Color FG >> "+fg.getRed()+"-"+fg.getGreen()+"-"+fg.getBlue()+"-");

				//set fg for pdf
				_instance.currentColorFG = fg;		

			} else {
				/* AttributeSet dies if you set a value to null */
				attributes.removeAttribute(StyleConstants.Foreground);
				//System.out.println("Setting black color");
				_instance.currentColorFG = _instance.defaultColor();
			}
		}else
		{
			//System.out.println("Setting black color");
			_instance.currentColorFG = _instance.defaultColor();
		}

		_instance.currentColorBG = Color.WHITE;
		if (_instance.colorTable != null) {
			stateItem = (Integer)_instance.parserState.get("cb");
			if (stateItem != null) {
				Color bg = _instance.colorTable[stateItem.intValue()];
				attributes.addAttribute(StyleConstants.Background,
						bg);
				//System.out.println("Color BG >> "+bg.getRed()+"-"+bg.getGreen()+"-"+bg.getBlue()+"-");

				//set bg for pdf
				_instance.currentColorBG = bg;

			} else {
				/* AttributeSet dies if you set a value to null */
				attributes.removeAttribute(StyleConstants.Background);
				_instance.currentColorBG = Color.WHITE;
			}
		}else
			_instance.currentColorBG = Color.WHITE;

		Style characterStyle = (Style)_instance.parserState.get("characterStyle");
		if (characterStyle != null){
			attributes.setResolveParent(characterStyle);
		}

		/* Other attributes are maintained directly in "attributes" */

		//    Enumeration enm = attributes.getAttributeNames();
		//    System.out.println("Attribute count >> "+attributes.getAttributeCount());
		//    while(enm.hasMoreElements())
		//    {
		//    	try{
		//    	Object obj = enm.nextElement();
		//    	System.out.println("Attribute element >> "+obj);    	
		//    	System.out.println("Attribute  >> "+attributes.getAttribute(obj));
		//    	}catch(Exception e)
		//    	{
		//    		System.out.println("Error >> "+e.getMessage());
		//    	}
		//    }

		//handle underline
		_instance.isUnderlined = false;
		try{
			_instance.isUnderlined = ((Boolean) attributes.getAttribute(StyleConstants.Underline)).booleanValue();
		}catch(Exception e)
		{
			System.out.println("Error getting underline >> "+e.getMessage());
		}


		return attributes;
	}

	/**
	 * Calculates the current paragraph attributes (with keys
	 * as given in StyleConstants) from the current parser state.
	 *
	 * @returns a newly created MutableAttributeSet. 
	 * @see StyleConstants
	 */
	MutableAttributeSet currentParagraphAttributes()
	{
		/* NB if there were a mutableCopy() method we should use it */
		MutableAttributeSet bld = new SimpleAttributeSet(paragraphAttributes);

		Integer stateItem;

		/*** Tab stops ***/
		TabStop tabs[];

		tabs = (TabStop[])_instance.parserState.get("_tabs_immutable");
		if (tabs == null) {
			Dictionary workingTabs = (Dictionary)_instance.parserState.get("_tabs");
			if (workingTabs != null) {
				int count = ((Integer)workingTabs.get("stop count")).intValue();
				tabs = new TabStop[count];
				for (int ix = 1; ix <= count; ix ++)
					tabs[ix-1] = (TabStop)workingTabs.get(Integer.valueOf(ix));
				_instance.parserState.put("_tabs_immutable", tabs);
			}
		}
		if (tabs != null)
			bld.addAttribute(Constants.Tabs, tabs);

		Style paragraphStyle = (Style)_instance.parserState.get("paragraphStyle");
		if (paragraphStyle != null)
			bld.setResolveParent(paragraphStyle);

		return bld;
	}

	/**
	 * Calculates the current section attributes
	 * from the current parser state.
	 *
	 * @returns a newly created MutableAttributeSet. 
	 */
	public AttributeSet currentSectionAttributes()
	{
		MutableAttributeSet attributes = new SimpleAttributeSet(sectionAttributes);

		Style sectionStyle = (Style)_instance.parserState.get("sectionStyle");
		if (sectionStyle != null)
			attributes.setResolveParent(sectionStyle);

		return attributes;
	}

	/** Resets the filter's internal notion of the current character
	 *  attributes to their default values. Invoked to handle the
	 *  \plain keyword. */
	protected void resetCharacterAttributes()
	{
		handleKeyword("f", 0);
		handleKeyword("cf", 0);

		handleKeyword("fs", 24);  /* 12 pt. */

		Enumeration attributes = RTF2PDFReader.straightforwardAttributes.elements();
		while(attributes.hasMoreElements()) {
			RTFAttribute attr = (RTFAttribute)attributes.nextElement();
			if (attr.domain() == RTFAttribute.D_CHARACTER) 
				attr.setDefault(characterAttributes);
		}

		handleKeyword("sl", 1000);

		_instance.parserState.remove("characterStyle");
	}

	/** Resets the filter's internal notion of the current paragraph's
	 *  attributes to their default values. Invoked to handle the
	 *  \pard keyword. */
	protected void resetParagraphAttributes()
	{
		_instance.parserState.remove("_tabs");
		_instance.parserState.remove("_tabs_immutable");
		_instance.parserState.remove("paragraphStyle");

		StyleConstants.setAlignment(paragraphAttributes, 
				StyleConstants.ALIGN_LEFT);

		Enumeration attributes = RTF2PDFReader.straightforwardAttributes.elements();
		while(attributes.hasMoreElements()) {
			RTFAttribute attr = (RTFAttribute)attributes.nextElement();
			if (attr.domain() == RTFAttribute.D_PARAGRAPH) 
				attr.setDefault(characterAttributes);
		}
	}

	/** Resets the filter's internal notion of the current section's
	 *  attributes to their default values. Invoked to handle the
	 *  \sectd keyword. */
	protected void resetSectionAttributes()
	{
		Enumeration attributes = RTF2PDFReader.straightforwardAttributes.elements();
		while(attributes.hasMoreElements()) {
			RTFAttribute attr = (RTFAttribute)attributes.nextElement();
			if (attr.domain() == RTFAttribute.D_SECTION) 
				attr.setDefault(characterAttributes);
		}

		_instance.parserState.remove("sectionStyle");
	}
}


