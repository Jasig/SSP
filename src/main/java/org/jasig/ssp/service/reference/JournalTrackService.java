package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface JournalTrackService extends
		AuditableCrudService<JournalTrack> {

	@Override
	public PagingWrapper<JournalTrack> getAll(SortingAndPaging sAndP);

	@Override
	public JournalTrack get(UUID id) throws ObjectNotFoundException;

	@Override
	public JournalTrack create(JournalTrack obj);

	@Override
	public JournalTrack save(JournalTrack obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
