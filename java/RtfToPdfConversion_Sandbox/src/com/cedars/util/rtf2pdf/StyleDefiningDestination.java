package com.cedars.util.rtf2pdf;

import javax.swing.text.Style;

/** This subclass handles an individual style */
public class StyleDefiningDestination extends AttributeTrackingDestination implements Destination
{
	final int STYLENUMBER_NONE = 222; 
	boolean additive;
	boolean characterStyle;
	boolean sectionStyle;
	public String styleName;
	public int number;
	int basedOn;
	int nextStyle;
	boolean hidden;
	StylesheetDestination sdef;
	
	Style realizedStyle;

	public StyleDefiningDestination(RTF2PDFReader instance, StylesheetDestination sdef)
	{
		super(instance);
		this.sdef = sdef;
		additive = false;
		characterStyle = false;
		sectionStyle = false;
		styleName = null;
		number = 0;
		basedOn = STYLENUMBER_NONE;
		nextStyle = STYLENUMBER_NONE;
		hidden = false;
	}

	public void handleText(String text)
	{
		if (styleName != null)
			styleName = styleName + text;
		else
			styleName = text;
	}

	public void close() {
		int semicolon = (styleName == null) ? 0 : styleName.indexOf(';');
		if (semicolon > 0)
			styleName = styleName.substring(0, semicolon);
		sdef.definedStyles.put(Integer.valueOf(number), this);
		super.close();
	}

	public boolean handleKeyword(String keyword)
	{
		if (keyword.equals("additive")) {
			additive = true;
			return true;
		}
		if (keyword.equals("shidden")) {
			hidden = true;
			return true;
		}
		return super.handleKeyword(keyword);
	}

	public boolean handleKeyword(String keyword, int parameter)
	{
		if (keyword.equals("s")) {
			characterStyle = false;
			sectionStyle = false;
			number = parameter;
		} else if (keyword.equals("cs")) {
			characterStyle = true;
			sectionStyle = false;
			number = parameter;
		} else if (keyword.equals("ds")) {
			characterStyle = false;
			sectionStyle = true;
			number = parameter;
		} else if (keyword.equals("sbasedon")) {
			basedOn = parameter;
		} else if (keyword.equals("snext")) {
			nextStyle = parameter;
		} else {
			return super.handleKeyword(keyword, parameter);
		}
		return true;
	}

	public Style realize()
	{
		Style basis = null;
		Style next = null;

		if (realizedStyle != null)
			return realizedStyle;

		if (basedOn != STYLENUMBER_NONE) {
			StyleDefiningDestination styleDest;
			styleDest = (StyleDefiningDestination)sdef.definedStyles.get(Integer.valueOf(basedOn));
			if (styleDest != null && styleDest != this) {
				basis = styleDest.realize();
			}
		}

		/* NB: Swing StyleContext doesn't allow distinct styles with
   the same name; RTF apparently does. This may confuse the
   user. */
		realizedStyle = _instance.target.addStyle(styleName, basis);

		if (characterStyle) {
			realizedStyle.addAttributes(currentTextAttributes());
			realizedStyle.addAttribute(Constants.StyleType,
					Constants.STCharacter);
		} else if (sectionStyle) {
			realizedStyle.addAttributes(currentSectionAttributes());
			realizedStyle.addAttribute(Constants.StyleType,
					Constants.STSection);
		} else { /* must be a paragraph style */
			realizedStyle.addAttributes(currentParagraphAttributes());
			realizedStyle.addAttribute(Constants.StyleType,
					Constants.STParagraph);
		}
		
		if (nextStyle != STYLENUMBER_NONE) {
			StyleDefiningDestination styleDest;
			styleDest = (StyleDefiningDestination)sdef.definedStyles.get(Integer.valueOf(nextStyle));
			if (styleDest != null) {
				next = styleDest.realize();
			}
		}

		if (next != null)
			realizedStyle.addAttribute(Constants.StyleNext, next);
		realizedStyle.addAttribute(Constants.StyleAdditive,
				Boolean.valueOf(additive));
		realizedStyle.addAttribute(Constants.StyleHidden,
				Boolean.valueOf(hidden));

		return realizedStyle;
	}
}