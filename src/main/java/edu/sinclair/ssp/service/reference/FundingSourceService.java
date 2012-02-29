package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.FundingSource;

public interface FundingSourceService {

	public List<FundingSource> getAll();

	public FundingSource get(UUID id);

	public FundingSource save(FundingSource obj);

	public void delete(UUID id);

}