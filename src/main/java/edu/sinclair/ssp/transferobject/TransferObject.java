package edu.sinclair.ssp.transferobject;

public interface TransferObject<T> {

	public void fromModel(T model);
	
	public void addToModel(T model);
}
