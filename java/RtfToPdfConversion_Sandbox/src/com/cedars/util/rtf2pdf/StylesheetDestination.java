package com.cedars.util.rtf2pdf;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.text.Style;

/** Handles the stylesheet keyword. Styles are read and sorted
 *  into the three style arrays in the RTFReader. */
public class StylesheetDestination extends DiscardingDestination implements Destination
{
	Dictionary definedStyles;
	RTF2PDFReader _instance;

	public StylesheetDestination(RTF2PDFReader instance)
	{
		_instance = instance;
		definedStyles = new Hashtable();
	}

	public void begingroup()
	{
		Destination dest = new StyleDefiningDestination(_instance, this);
		_instance.setRTFDestination(dest);
	}

	public void close() 
	{
		Vector chrStyles, pgfStyles, secStyles;
		chrStyles = new Vector();
		pgfStyles = new Vector();
		secStyles = new Vector();
		Enumeration styles = definedStyles.elements();
		while(styles.hasMoreElements()) {
			StyleDefiningDestination style;
			Style defined;
			style = (StyleDefiningDestination)styles.nextElement();
			defined = style.realize();
			_instance.warning("Style "+style.number+" ("+style.styleName+"): "+defined);
			String stype = (String)defined.getAttribute(Constants.StyleType);
			Vector toSet;
			if (stype.equals(Constants.STSection)) {
				toSet = secStyles;
			} else if (stype.equals(Constants.STCharacter)) {
				toSet = chrStyles;
			} else {
				toSet = pgfStyles;
			}
			if (toSet.size() <= style.number)
				toSet.setSize(style.number + 1);
			toSet.setElementAt(defined, style.number);
		}
		if (!(chrStyles.isEmpty())) {
			Style[] styleArray = new Style[chrStyles.size()];
			chrStyles.copyInto(styleArray);
			_instance.characterStyles = styleArray;
		}
		if (!(pgfStyles.isEmpty())) {
			Style[] styleArray = new Style[pgfStyles.size()];
			pgfStyles.copyInto(styleArray);
			_instance.paragraphStyles = styleArray;
		}
		if (!(secStyles.isEmpty())) {
			Style[] styleArray = new Style[secStyles.size()];
			secStyles.copyInto(styleArray);
			_instance.sectionStyles = styleArray;
		}

		/* (old debugging code)
int i, m;
if (characterStyles != null) {
  m = characterStyles.length;
  for(i=0;i<m;i++) 
    warnings.println("chrStyle["+i+"]="+characterStyles[i]);
} else warnings.println("No character styles.");
if (paragraphStyles != null) {
  m = paragraphStyles.length;
  for(i=0;i<m;i++) 
    warnings.println("pgfStyle["+i+"]="+paragraphStyles[i]);
} else warnings.println("No paragraph styles.");
if (sectionStyles != null) {
  m = characterStyles.length;
  for(i=0;i<m;i++) 
    warnings.println("secStyle["+i+"]="+sectionStyles[i]);
} else warnings.println("No section styles.");
		 */
	}
}