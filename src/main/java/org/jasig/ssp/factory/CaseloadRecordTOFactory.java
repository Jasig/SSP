package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.transferobject.CaseloadRecordTO;

public interface CaseloadRecordTOFactory {
	CaseloadRecordTO from(CaseloadRecord model);

	List<CaseloadRecordTO> asTOList(Collection<CaseloadRecord> models);
}
