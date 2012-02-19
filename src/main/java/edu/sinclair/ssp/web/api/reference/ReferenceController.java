package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

/**
 * All the Methods a Reference Controller needs to be useful.
 * @author daniel
 *
 * @param <T> The TO type this controller works with.
 */
public interface ReferenceController<T> {

	public List<T> getAll() throws Exception;
	
	public T get(UUID id) throws Exception;
	
	public T save(T obj) throws Exception;
	
	public void delete(UUID id) throws Exception;
}
