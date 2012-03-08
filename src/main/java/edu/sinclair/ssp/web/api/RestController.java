package edu.sinclair.ssp.web.api;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.transferobject.ServiceResponse;

/**
 * All the Methods a Reference Controller needs to be useful.
 * @author daniel
 *
 * @param <T> The TO type this controller works with.
 */
public abstract class RestController<T> {

	public abstract List<T> getAll(ObjectStatus status) throws Exception;

	public abstract T get(UUID id) throws Exception;
	
	public abstract T create(T obj) throws Exception;
	
	public abstract T save(UUID id, T obj) throws Exception;
	
	public abstract ServiceResponse delete(UUID id) throws Exception;
	
	public abstract ServiceResponse handle(Exception e);
}
