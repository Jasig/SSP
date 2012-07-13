package org.jasig.ssp.dao.external;

import java.io.Serializable;
import java.util.Collection;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

/**
 * Data access class for the Term reference entity.
 */
@Repository
public class TermDao extends AbstractExternalDataDao<Term> {

	public TermDao() {
		super(Term.class);
	}

	@Override
	public PagingWrapper<Term> getAll(final SortingAndPaging sAndP) {
		final Collection<Term> terms = Lists.newArrayList();
		return new PagingWrapper<Term>(terms);
	}

	@Override
	public Term get(Serializable id) throws ObjectNotFoundException {
		return new Term();
	}
}
