package org.jasig.ssp.factory.impl;

import java.util.Collection;
import java.util.List;

import org.jasig.ssp.factory.PersonSearchResultTOFactory;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class PersonSearchResultTOFactoryImpl implements
		PersonSearchResultTOFactory {

	@Override
	public PersonSearchResultTO from(final PersonSearchResult model) {
		return new PersonSearchResultTO(model);
	}

	@Override
	public List<PersonSearchResultTO> asTOList(
			final Collection<PersonSearchResult> models) {
		final List<PersonSearchResultTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (PersonSearchResult model : models) {
				tos.add(from(model));
			}
		}
		return tos;
	}

}
