package org.jasig.ssp.service.external;

import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.service.ObjectNotFoundException;


public interface ExternalPersonPlanStatusService extends
		ExternalDataService<ExternalPersonPlanStatus> {

	ExternalPersonPlanStatus getBySchoolId(String schoolId) throws ObjectNotFoundException;
}
