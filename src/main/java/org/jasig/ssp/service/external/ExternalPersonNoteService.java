package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalPersonNote;

public interface ExternalPersonNoteService extends
		ExternalReferenceDataService<ExternalPersonNote> {
	
		List<ExternalPersonNote> getNotesBySchoolId(String schoolId);

}
