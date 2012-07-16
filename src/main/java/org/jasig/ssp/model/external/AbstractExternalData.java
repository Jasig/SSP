package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * ExternalData model helper that includes common fields, such as the
 * Hibernate-required unique identifier (even though the identifier is never
 * used by the system otherwise).
 * 
 * @author jon.adams
 * 
 */
@MappedSuperclass
// all ExternalDatas point to an external RDBMS view, so no awareness of cache
// freshness
@Cache(usage = CacheConcurrencyStrategy.NONE)
public abstract class AbstractExternalData implements ExternalData {
	@Id
	@Type(type = "string")
	private Serializable id;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(final Serializable id) {
		this.id = id;
	}
}