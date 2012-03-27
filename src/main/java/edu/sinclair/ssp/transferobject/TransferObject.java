package edu.sinclair.ssp.transferobject;

/**
 * An object which exposes the model to another layer
 * 
 * @param <T>
 *            Should be any model class from the edu.sinclair.ssp.model
 *            namespace.
 */
public interface TransferObject<T> {

	/**
	 * Add the attributes of the model to this TransferObject
	 * 
	 * @param model
	 *            Model from which to pull attributes
	 */
	public void pullAttributesFromModel(T model);

	/**
	 * Create a new Model object from this TransferObject
	 * 
	 * @return A model representation from the related transfer object.
	 */
	public T asModel();

	/**
	 * Add the attributes of this TransferObject to this Model
	 * 
	 * @param model
	 *            Model from which to pull attributes
	 * @return An updated model with new values from the supplied parameter.
	 */
	public T pushAttributesToModel(T model);

}
