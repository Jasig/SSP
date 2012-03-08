package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface EthnicityService {

	public List<Ethnicity> getAll(ObjectStatus status);

	public Ethnicity get(UUID id) throws ObjectNotFoundException;

	public Ethnicity create(Ethnicity obj);

	public Ethnicity save(Ethnicity obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
