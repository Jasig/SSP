package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

public interface ReferenceDao<T>{
	public List<T> getAll();
	
	public T get(UUID id);
	
	public T save(T obj);
	
	public void delete(T id);
}
