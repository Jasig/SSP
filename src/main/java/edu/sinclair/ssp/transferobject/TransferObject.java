package edu.sinclair.ssp.transferobject;

/**
 * An object which exposes the model to another layer
 */
public interface TransferObject<T> {

	/**
	 * Add the attributes of the model to this TransferObject
	 */
	public void pullAttributesFromModel(T model);

	/**
	 * Create a new Model object from this TransferObject
	 */
	public T asModel();

	/**
	 * Add the attributes of this TransferObject to this Model
	 */
	public T pushAttributesToModel(T model);

}
