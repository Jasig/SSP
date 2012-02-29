package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.BindingResult;

/**
 * All the Methods a Reference Controller needs to be useful.
 * @author daniel
 *
 * @param <T> The TO type this controller works with.
 */
public abstract class ReferenceController<T> {

	public abstract List<T> getAll() throws Exception;

	public abstract T get(UUID id) throws Exception;
	
	public abstract T save(T obj, BindingResult result) throws Exception;
	
	public abstract void delete(UUID id) throws Exception;
}
