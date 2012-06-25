package org.jasig.ssp.transferobject.jsonserializer;

/**
 * Ensures a read-only code and title property.
 */
public interface CodeAndProperty {

	/**
	 * Technical code (or key).
	 * 
	 * @return the code
	 */
	String getCode();

	/**
	 * User title (or value).
	 * 
	 * @return the title
	 */
	String getTitle();
}