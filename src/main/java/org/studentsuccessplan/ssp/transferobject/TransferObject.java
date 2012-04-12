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
	public void fromModel(T model);

	/**
	 * Create a new Model object from this TransferObject
	 * 
	 * @return A model representation from the related transfer object.
	 */
	public T asModel();

	/**
	 * Add the attributes of this TransferObject to this Model
	 * 
	 * Things which are sensitive do not get serialized back to the model.
	 * 
	 * @param model
	 *            Model from which to pull attributes
	 * @return An updated model with new values from the supplied parameter.
	 */
	public T addToModel(T model);

}
