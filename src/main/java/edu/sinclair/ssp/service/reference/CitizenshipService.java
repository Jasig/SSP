package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.Citizenship;

public interface CitizenshipService {

	public List<Citizenship> getAll();

	public Citizenship get(UUID id);

	public Citizenship save(Citizenship obj);

	public void delete(UUID id);

}