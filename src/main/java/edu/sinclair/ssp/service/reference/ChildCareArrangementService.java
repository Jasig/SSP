package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.ChildCareArrangement;

public interface ChildCareArrangementService {

	public List<ChildCareArrangement> getAll();

	public ChildCareArrangement get(UUID id);

	public ChildCareArrangement save(ChildCareArrangement obj);

	public void delete(UUID id);

}