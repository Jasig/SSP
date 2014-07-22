package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.EarlyAlertSearchResult;
import org.jasig.ssp.transferobject.EarlyAlertSearchResultTO;

public interface EarlyAlertSearchResultTOFactory {

	EarlyAlertSearchResultTO from(EarlyAlertSearchResult model);

	List<EarlyAlertSearchResultTO> asTOList(Collection<EarlyAlertSearchResult> models);
}
