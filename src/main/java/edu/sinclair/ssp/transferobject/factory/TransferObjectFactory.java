package edu.sinclair.ssp.transferobject.factory;

import java.util.List;

public interface TransferObjectFactory <T,U> {

	public T toTO(U from);
	
	public U toModel(T from);
	
	public List<T> toTOList(List<U> from);
	
	public List<U> toModelList(List<T> from);
}
