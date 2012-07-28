package org.jasig.ssp.service.external;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;

public interface TermService extends ExternalDataService<Term> {

	Term getByCode(final String code) throws ObjectNotFoundException;

	Term getCurrentTerm() throws ObjectNotFoundException;
}