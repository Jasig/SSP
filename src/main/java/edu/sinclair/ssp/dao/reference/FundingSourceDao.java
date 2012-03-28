package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.FundingSource;

/**
 * Data access class for the FundingSource reference entity.
 */
@Repository
public class FundingSourceDao extends ReferenceAuditableCrudDao<FundingSource>
		implements AuditableCrudDao<FundingSource> {

	public FundingSourceDao() {
		super(FundingSource.class);
	}
}
