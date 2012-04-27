package org.studentsuccessplan.ssp.transferobject;

/**
 * An object which exposes the model to another layer
 * 
 * @param <T>
 *            Should be any model class from the
 *            org.studentsuccessplan.ssp.model
 *            namespace.
 */
public interface TransferObject<T> {

	/**
	 * Add the attributes of the model to this TransferObject
	 * 
	 * @param model
	 *            Model from which to pull attributes
	 */
	void from(T model);

}
