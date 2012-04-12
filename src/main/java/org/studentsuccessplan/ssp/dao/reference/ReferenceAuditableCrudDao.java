package org.studentsuccessplan.ssp.dao.reference;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.dao.AbstractAuditableCrudDao;
import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

/**
 * Base CRUD methods for reference model objects.
 * <p>
 * Defaults to sorting by the <code>Name</code> property unless otherwise
 * specified.
 * 
 * @param <T>
 *            Any domain type that extends the Auditable class.
 */
@Repository
public abstract class ReferenceAuditableCrudDao<T extends Auditable> extends
		AbstractAuditableCrudDao<T> {

	/**
	 * Constructor that initializes the instance with the specific type.
	 * 
	 * @param persistentClass
	 */
	protected ReferenceAuditableCrudDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return getAllWithDefault(status, firstResult, maxResults, sort,
				sortDirection, "name");
	}
}
