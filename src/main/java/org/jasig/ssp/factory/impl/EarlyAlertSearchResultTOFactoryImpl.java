package org.jasig.ssp.factory.impl;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.factory.EarlyAlertSearchResultTOFactory;
import org.jasig.ssp.model.EarlyAlertSearchResult;
import org.jasig.ssp.transferobject.EarlyAlertSearchResultTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class EarlyAlertSearchResultTOFactoryImpl implements
		EarlyAlertSearchResultTOFactory {


	@Override
	public EarlyAlertSearchResultTO from(EarlyAlertSearchResult model) {
		return new EarlyAlertSearchResultTO(model);
	}

	@Override
	public List<EarlyAlertSearchResultTO> asTOList(
			Collection<EarlyAlertSearchResult> models) {
		final List<EarlyAlertSearchResultTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (EarlyAlertSearchResult model : models) {
				tos.add(from(model));
			}
		}
		return tos;
	}
}
