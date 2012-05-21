package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.FundingSource;

/**
 * Data access class for the FundingSource reference entity.
 */
@Repository
public class FundingSourceDao extends AbstractReferenceAuditableCrudDao<FundingSource>
		implements AuditableCrudDao<FundingSource> {

	public FundingSourceDao() {
		super(FundingSource.class);
	}
}
