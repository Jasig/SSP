package edu.sinclair.ssp.web.api;

import java.util.List;
import java.util.UUID;

/**
 * All the Methods a Reference Controller needs to be useful.
 * @author daniel
 *
 * @param <T> The TO type this controller works with.
 */
public abstract class RestController<T> {

	public abstract List<T> getAll() throws Exception;

	public abstract T get(UUID id) throws Exception;
	
	public abstract boolean create(T obj) throws Exception;
	
	public abstract boolean save(UUID id, T obj) throws Exception;
	
	public abstract boolean delete(UUID id) throws Exception;
}
