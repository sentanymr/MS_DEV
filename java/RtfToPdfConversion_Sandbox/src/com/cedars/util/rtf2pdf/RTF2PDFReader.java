/*
 * Project    : vPDF
 * Package    : vPDF.rtf2pdf.rw
 * Description: RTF2PDFReader is the modified version of Sun'n RTFReader
 * 				The files have been placed in a new package to respect the 
 * 				SUN Licence terms.
 *
 * Known Bugs :     None
 *
 * Modification Log
 * Date                 Author                          Description
 * --------------------------------------------------------------------------
 * May 2nd, 2007  Varun Kumar Reddy Dodla	              Created
 *
 */

package com.cedars.util.rtf2pdf;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

/**
 * An extension of RTFReader
 */
public class RTF2PDFReader extends RTFParser
{
	/** The object to which the parsed text is sent. */
	StyledDocument target;

	/** The object to which the pdf is sent. */
	com.lowagie.text.Document targetPDF;

	/**
	 * Stores current font for pdf
	 */
	com.lowagie.text.Font currentFont = new Font(Font.TIMES_ROMAN,12F);

	/**
	 * Stores current color for pdf
	 */
	Color currentColorFG = defaultColor();
	Color currentColorBG = Color.WHITE; 

	/**
	 * stores current para
	 */
	Paragraph currentParagraph;

	/**
	 * stores current Leading
	 */
	float currentLeading;

	/**
	 * Stores underlined info for pdf
	 */
	boolean isUnderlined = false;

	/** Miscellaneous information about the parser's state. This
	 *  dictionary is saved and restored when an RTF group begins
	 *  or ends. */
	Dictionary parserState;   /* Current parser state */
	/** This is the "dst" item from parserState. rtfDestination
	 *  is the current rtf destination. It is cached in an instance
	 *  variable for speed. */
	Destination rtfDestination;
	/** This holds the current document attributes. */
    MutableAttributeSet documentAttributes;

	/** This Dictionary maps Integer font numbers to String font names. */
	Dictionary fontTable;
	/** This array maps color indices to Color objects. */
	Color[] colorTable;
	/** This array maps character style numbers to Style objects. */
	Style[] characterStyles;
	/** This array maps paragraph style numbers to Style objects. */
	Style[] paragraphStyles;
	/** This array maps section style numbers to Style objects. */
	Style[] sectionStyles;

	/** This is the RTF version number, extracted from the \rtf keyword.
	 *  The version information is currently not used. */
	int rtfversion;

	/** <code>true</code> to indicate that if the next keyword is unknown,
	 *  the containing group should be ignored. */
	boolean ignoreGroupIfUnknownKeyword;

	/** The parameter of the most recently parsed \\ucN keyword,
	 *  used for skipping alternative representations after a
	 *  Unicode character. */
	int skippingCharacters;  

	static Dictionary straightforwardAttributes;
	static {
		straightforwardAttributes = RTFAttributes.attributesByKeyword();
	}

	public MockAttributeSet mockery;

	/* this should be final, but there's a bug in javac... */
	/** textKeywords maps RTF keywords to single-character strings,
	 *  for those keywords which simply insert some text. */
	static Dictionary textKeywords = null;
	static {
		textKeywords = new Hashtable();
		textKeywords.put("\\",         "\\");
		textKeywords.put("{",          "{");
		textKeywords.put("}",          "}");
		textKeywords.put(" ",          "\u00A0");  /* not in the spec... */
		textKeywords.put("~",          "\u00A0");  /* nonbreaking space */
		textKeywords.put("_",          "\u2011");  /* nonbreaking hyphen */
		textKeywords.put("bullet",     "\u2022");
		textKeywords.put("emdash",     "\u2014");
		textKeywords.put("emspace",    "\u2003");
		textKeywords.put("endash",     "\u2013");
		textKeywords.put("enspace",    "\u2002");
		textKeywords.put("ldblquote",  "\u201C");
		textKeywords.put("lquote",     "\u2018");
		textKeywords.put("ltrmark",    "\u200E");
		textKeywords.put("rdblquote",  "\u201D");
		textKeywords.put("rquote",     "\u2019");
		textKeywords.put("rtlmark",    "\u200F");
		textKeywords.put("tab",        "        "); //changed from \u0009
		textKeywords.put("zwj",        "\u200D");
		textKeywords.put("zwnj",       "\u200C");

		/* There is no Unicode equivalent to an optional hyphen, as far as
	 I can tell. */
		textKeywords.put("-",          "\u2027");  /* TODO: optional hyphen */
	}

	/* some entries in parserState */
	static final String TabAlignmentKey = "tab_alignment";
	static final String TabLeaderKey = "tab_leader";

	static Dictionary characterSets;
	static boolean useNeXTForAnsi = false;
	static {
		characterSets = new Hashtable();
	}

	/* TODO: per-font font encodings ( \fcharset control word ) ? */

	/**
	 * Creates a new RTFReader instance. Text will be sent to
	 * the specified TextAcceptor.
	 *
	 * @param destination The TextAcceptor which is to receive the text.
	 */
	public RTF2PDFReader(StyledDocument destination,com.lowagie.text.Document destinationPDF)
	{
		int i;

		target = destination;
		targetPDF = destinationPDF;
		parserState = new Hashtable();
		fontTable = new Hashtable();

		rtfversion = -1;

		mockery = new MockAttributeSet();
		documentAttributes = new SimpleAttributeSet();
	}

	/** Called when the RTFParser encounters a bin keyword in the
	 *  RTF stream.
	 *
	 *  @see RTFParser
	 */
	public void handleBinaryBlob(byte[] data)
	{
		//if (skippingCharacters > 0) {
			/* a blob only counts as one character for skipping purposes */
		//	skippingCharacters --;
		//	return;
		//}

		if (rtfDestination != null) {
			rtfDestination.handleBinaryBlob(data);
		}
	}


	/**
	 * Handles any pure text (containing no control characters) in the input
	 * stream. Called by the superclass. */
	public void handleText(String text)
	{
		if (skippingCharacters > 0) {
			if (skippingCharacters >= text.length()) {
				skippingCharacters -= text.length();
				return;
			} else {
				text = text.substring(skippingCharacters);
				skippingCharacters = 0;
			}
		}

		if (rtfDestination != null) {
			rtfDestination.handleText(text);
			return;
		}

		warning("Text with no destination. oops.");
	}

	/** The default color for text which has no specified color. */
	Color defaultColor()
	{
		return Color.BLACK;
	}

	/** Called by the superclass when a new RTF group is begun.
	 *  This implementation saves the current <code>parserState</code>, and gives
	 *  the current destination a chance to save its own state.
	 * @see RTFParser#begingroup
	 */
	public void begingroup()
	{
		if (skippingCharacters > 0) {
			/* TODO this indicates an error in the RTF. Log it? */
			skippingCharacters = 0;
		}

		/* we do this little dance to avoid cloning the entire state stack and
       immediately throwing it away. */
		Object oldSaveState = parserState.get("_savedState");
		if (oldSaveState != null)
			parserState.remove("_savedState");
		Dictionary saveState = (Dictionary)((Hashtable)parserState).clone();
		if (oldSaveState != null)
			saveState.put("_savedState", oldSaveState);
		parserState.put("_savedState", saveState);

		if (rtfDestination != null)
			rtfDestination.begingroup();
	}

	/** Called by the superclass when the current RTF group is closed.
	 *  This restores the parserState saved by <code>begingroup()</code>
	 *  as well as invoking the endgroup method of the current
	 *  destination.
	 * @see RTFParser#endgroup
	 */
	public void endgroup()
	{
		if (skippingCharacters > 0) {
			/* NB this indicates an error in the RTF. Log it? */
			skippingCharacters = 0;
		}

		Dictionary restoredState = (Dictionary)parserState.get("_savedState");
		Destination restoredDestination = (Destination)restoredState.get("dst");
		if (restoredDestination != rtfDestination) {
			rtfDestination.close(); /* allow the destination to clean up */
			rtfDestination = restoredDestination;
		}
		Dictionary oldParserState = parserState;
		parserState = restoredState;
		if (rtfDestination != null)
			rtfDestination.endgroup(oldParserState);
	}

	public void setRTFDestination(Destination newDestination)
	{
		/* Check that setting the destination won't close the
       current destination (should never happen) */
		Dictionary previousState = (Dictionary)parserState.get("_savedState");
		if (previousState != null) {
			if (rtfDestination != previousState.get("dst")) {
				warning("Warning, RTF destination overridden, invalid RTF.");
				rtfDestination.close();
			}
		}
		rtfDestination = newDestination;
		parserState.put("dst", rtfDestination);
	}

	/** Called by the user when there is no more input (<i>i.e.</i>,
	 * at the end of the RTF file.)
	 *
	 * @see OutputStream#close
	 */
	public void close()
			throws IOException
	{
		Enumeration docProps = documentAttributes.getAttributeNames();
		while(docProps.hasMoreElements()) {
			Object propName = docProps.nextElement();
			target.putProperty(propName,
					documentAttributes.getAttribute((String)propName));
		}

		/* RTFParser should have ensured that all our groups are closed */

		warning("RTF filter done.");

		super.close();
	}

	/**
	 * Handles a parameterless RTF keyword. This is called by the superclass
	 * (RTFParser) when a keyword is found in the input stream.
	 *
	 * @returns <code>true</code> if the keyword is recognized and handled;
	 *          <code>false</code> otherwise
	 * @see RTFParser#handleKeyword
	 */
	public boolean handleKeyword(String keyword)
	{
		Object item;
		boolean ignoreGroupIfUnknownKeywordSave = ignoreGroupIfUnknownKeyword;

		if (skippingCharacters > 0) {
			skippingCharacters --;
			return true;
		}

		ignoreGroupIfUnknownKeyword = false;

		if ((item = textKeywords.get(keyword)) != null) {
			handleText((String)item);
			return true;
		}

		if (keyword.equals("fonttbl")) {
			setRTFDestination(new FonttblDestination(this));
			return true;
		}

		if (keyword.equals("colortbl")) {
			setRTFDestination(new ColortblDestination(this));
			return true;
		}

		if (keyword.equals("stylesheet")) {
			setRTFDestination(new StylesheetDestination(this));
			return true;
		}

		if (keyword.equals("info")) {
			setRTFDestination(new InfoDestination());
			return false; 
		}

		if (keyword.equals("mac")) {
			setCharacterSet("mac");
			return true;
		}

		if (keyword.equals("ansi")) {
			if (useNeXTForAnsi)
				setCharacterSet("NeXT");
			else
				setCharacterSet("ansi");
			return true;
		}

		if (keyword.equals("next")) {
			setCharacterSet("NeXT");
			return true;
		}

		if (keyword.equals("pc")) {
			setCharacterSet("cpg437"); /* IBM Code Page 437 */
			return true;
		}

		if (keyword.equals("pca")) {
			setCharacterSet("cpg850"); /* IBM Code Page 850 */
			return true;
		}

		if (keyword.equals("*")) {
			ignoreGroupIfUnknownKeyword = true;
			return true;
		}
		
		if (keyword.equals("pict")){
			System.out.println(">>>> FOUND IMAGE >>>>");
			setRTFDestination(new PictureDestination(this));
			return true;
		}

		if (rtfDestination != null) {
			if(rtfDestination.handleKeyword(keyword))
				return true;
		}

		/* this point is reached only if the keyword is unrecognized */

		/* other destinations we don't understand and therefore ignore */
		if (keyword.equals("aftncn") ||
				keyword.equals("aftnsep") ||
				keyword.equals("aftnsepc") ||
				keyword.equals("annotation") ||
				keyword.equals("atnauthor") ||
				keyword.equals("atnicn") ||
				keyword.equals("atnid") ||
				keyword.equals("atnref") ||
				keyword.equals("atntime") ||
				keyword.equals("atrfend") ||
				keyword.equals("atrfstart") ||
				keyword.equals("bkmkend") ||
				keyword.equals("bkmkstart") ||
				keyword.equals("datafield") ||
				keyword.equals("do") ||
				keyword.equals("dptxbxtext") ||
				keyword.equals("falt") ||
				keyword.equals("field") ||
				keyword.equals("file") ||
				keyword.equals("filetbl") ||
				keyword.equals("fname") ||
				keyword.equals("fontemb") ||
				keyword.equals("fontfile") ||
				keyword.equals("footer") ||
				keyword.equals("footerf") ||
				keyword.equals("footerl") ||
				keyword.equals("footerr") ||
				keyword.equals("footnote") ||
				keyword.equals("ftncn") ||
				keyword.equals("ftnsep") ||
				keyword.equals("ftnsepc") ||
				keyword.equals("header") ||
				keyword.equals("headerf") ||
				keyword.equals("headerl") ||
				keyword.equals("headerr") ||
				keyword.equals("keycode") ||
				keyword.equals("nextfile") ||
				keyword.equals("object") ||
				keyword.equals("pn") ||
				keyword.equals("pnseclvl") ||
				keyword.equals("pntxtb") ||
				keyword.equals("pntxta") ||
				keyword.equals("revtbl") ||
				keyword.equals("rxe") ||
				keyword.equals("tc") ||
				keyword.equals("template") ||
				keyword.equals("txe") ||
				keyword.equals("xe")) {
			ignoreGroupIfUnknownKeywordSave = true;
		}

		if (ignoreGroupIfUnknownKeywordSave) {
			setRTFDestination(new DiscardingDestination());
		}

		return false;
	}

	/**
	 * Handles an RTF keyword and its integer parameter. 
	 * This is called by the superclass
	 * (RTFParser) when a keyword is found in the input stream.
	 *
	 * @returns <code>true</code> if the keyword is recognized and handled;
	 *          <code>false</code> otherwise
	 * @see RTFParser#handleKeyword
	 */
	public boolean handleKeyword(String keyword, int parameter)
	{
		boolean ignoreGroupIfUnknownKeywordSave = ignoreGroupIfUnknownKeyword;

		if (skippingCharacters > 0) {
			skippingCharacters --;
			return true;
		}

		ignoreGroupIfUnknownKeyword = false;

		if (keyword.equals("uc")) {
			/* count of characters to skip after a unicode character */
			parserState.put("UnicodeSkip", Integer.valueOf(parameter));
			return true;
		}
		if (keyword.equals("u")) {
			if (parameter < 0)
				parameter = parameter + 65536;
			handleText((char)parameter);
			Number skip = (Number)(parserState.get("UnicodeSkip"));
			if (skip != null) {
				skippingCharacters = skip.intValue();
			} else {
				skippingCharacters = 1;
			}
			return true;
		}

		if (keyword.equals("rtf")) {
			rtfversion = parameter;
			setRTFDestination(new DocumentDestination(this));
			return true;
		}

		if (keyword.startsWith("NeXT") ||
				keyword.equals("private"))
			ignoreGroupIfUnknownKeywordSave = true;

		if (rtfDestination != null) {
			if(rtfDestination.handleKeyword(keyword, parameter))
				return true;
		}

		/* this point is reached only if the keyword is unrecognized */

		if (ignoreGroupIfUnknownKeywordSave) {
			setRTFDestination(new DiscardingDestination());
		}

		return false;
	}

	private void setTargetAttribute(String name, Object value)
	{
		//    target.changeAttributes(new LFDictionary(LFArray.arrayWithObject(value), LFArray.arrayWithObject(name)));
	}

	/**
	 * setCharacterSet sets the current translation table to correspond with
	 * the named character set. The character set is loaded if necessary.
	 *
	 * @see AbstractFilter
	 */
	public void setCharacterSet(String name)
	{
		Object set;

		try {
			set = getCharacterSet(name);
		} catch (Exception e) {
			warning("Exception loading RTF character set \"" + name + "\": " + e);
			set = null;
		}

		if (set != null) {
			translationTable = (char[])set;
		} else {
			warning("Unknown RTF character set \"" + name + "\"");
			if (!name.equals("ansi")) {
				try {
					translationTable = (char[])getCharacterSet("ansi");
				} catch (IOException e) {
					throw new InternalError("RTFReader: Unable to find character set resources (" + e + ")");
				}
			}
		}

		setTargetAttribute(Constants.RTFCharacterSet, name);
	}

	/** Adds a character set to the RTFReader's list
	 *  of known character sets */
	public static void 
	defineCharacterSet(String name, char[] table)
	{
		if (table.length < 256)
			throw new IllegalArgumentException("Translation table must have 256 entries.");
		characterSets.put(name, table);
	}

	/** Looks up a named character set. A character set is a 256-entry
	 *  array of characters, mapping unsigned byte values to their Unicode
	 *  equivalents. The character set is loaded if necessary.
	 *
	 *  @returns the character set
	 */
	public static Object
	getCharacterSet(final String name)
			throws IOException
	{
		char[] set;

		set = (char [])characterSets.get(name);
		if (set == null) {
			InputStream charsetStream;
			charsetStream = (InputStream)java.security.AccessController.
					doPrivileged(new java.security.PrivilegedAction() {
						public Object run() {
							return RTFReader.class.getResourceAsStream
									("charsets/" + name + ".txt");
						}
					});
			set = readCharset(charsetStream);
			defineCharacterSet(name, set);
		}
		return set;
	}

	/** Parses a character set from an InputStream. The character set
	 * must contain 256 decimal integers, separated by whitespace, with
	 * no punctuation. B- and C- style comments are allowed.
	 *
	 * @returns the newly read character set
	 */
	static char[] readCharset(InputStream strm)
			throws IOException
	{
		char[] values = new char[256];
		int i;
		StreamTokenizer in = new StreamTokenizer(new BufferedReader(
				new InputStreamReader(strm, "ISO-8859-1")));

		in.eolIsSignificant(false);
		in.commentChar('#');
		in.slashSlashComments(true);
		in.slashStarComments(true);

		i = 0;
		while (i < 256) {
			int ttype;
			try {
				ttype = in.nextToken();
			} catch (Exception e) {
				throw new IOException("Unable to read from character set file (" + e + ")");
			}
			if (ttype != in.TT_NUMBER) {
				//	    System.out.println("Bad token: type=" + ttype + " tok=" + in.sval);
				throw new IOException("Unexpected token in character set file");
				//	    continue;
			}
			values[i] = (char)(in.nval);
			i++;
		}

		return values;
	}

	static char[] readCharset(java.net.URL href)
			throws IOException
	{
		return readCharset(href.openStream());
	}

}


