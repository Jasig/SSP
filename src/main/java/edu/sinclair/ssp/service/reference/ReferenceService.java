package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

public interface ReferenceService<T> {

	public List<T> getAll();
	
	public T get(UUID id);
	
	public T save(T obj);
	
	public void delete(UUID id);
}
