package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;

public interface AuditableCrudDao<T> {
	public List<T> getAll(ObjectStatus status);

	public T get(UUID id);

	public T save(T obj);

	public void delete(T id);
}
