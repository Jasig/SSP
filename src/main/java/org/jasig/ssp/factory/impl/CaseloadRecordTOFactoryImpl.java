package org.jasig.ssp.factory.impl;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.factory.CaseloadRecordTOFactory;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.transferobject.CaseloadRecordTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class CaseloadRecordTOFactoryImpl implements CaseloadRecordTOFactory {

	@Override
	public CaseloadRecordTO from(final CaseloadRecord model) {
		return new CaseloadRecordTO(model);
	}

	@Override
	public List<CaseloadRecordTO> asTOList(
			final Collection<CaseloadRecord> models) {
		final List<CaseloadRecordTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (CaseloadRecord model : models) {
				tos.add(from(model));
			}
		}
		return tos;
	}

}
