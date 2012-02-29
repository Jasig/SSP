package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.Ethnicity;

public interface EthnicityService {

	public List<Ethnicity> getAll();

	public Ethnicity get(UUID id);

	public Ethnicity save(Ethnicity ethnicity);

	public void delete(UUID id);

}