package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;

public interface ReferenceDao<T>{
	public List<T> getAll(ObjectStatus status);
	
	public T get(UUID id);
	
	public T save(T obj);
	
	public void delete(T id);
}
