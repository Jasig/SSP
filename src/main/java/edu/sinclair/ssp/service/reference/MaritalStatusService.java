package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.MaritalStatus;

public interface MaritalStatusService {

	public List<MaritalStatus> getAll();

	public MaritalStatus get(UUID id);

	public MaritalStatus save(MaritalStatus obj);

	public void delete(UUID id);

}