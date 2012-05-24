package org.jasig.ssp.model;

/**
 * Indicates this model has an associated Person
 */
public interface PersonAssoc {

	/**
	 * Gets the associated {@link Person} reference.
	 * 
	 * @return the associated {@link Person}
	 */
	Person getPerson();

	/**
	 * Sets the associated {@link Person} reference.
	 * 
	 * @param person
	 */
	void setPerson(Person person);
}