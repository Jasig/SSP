package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.VeteranStatus;

public interface VeteranStatusService {

	public List<VeteranStatus> getAll();

	public VeteranStatus get(UUID id);

	public VeteranStatus save(VeteranStatus obj);

	public void delete(UUID id);

}