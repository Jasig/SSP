package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.transferobject.PersonSearchResultTO;

public interface PersonSearchResultTOFactory {
	PersonSearchResultTO from(PersonSearchResult model);

	List<PersonSearchResultTO> asTOList(Collection<PersonSearchResult> models);
}
