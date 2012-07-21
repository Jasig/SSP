package org.jasig.ssp.dao.external;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Term reference entity.
 */
@Repository
public class TermDao extends AbstractExternalDataDao<Term> {

	public TermDao() {
		super(Term.class);
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param code
	 *            the <code>{@link Term}.Code</code> value, must be a String
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public Term getByCode(@NotNull final String code)
			throws ObjectNotFoundException {
		if (!StringUtils.isNotBlank(code)) {
			throw new ObjectNotFoundException(code, Term.class.getName());
		}

		final Term obj = (Term) createCriteria().add(
				Restrictions.eq("code", code)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(code, Term.class.getName());
		}

		return obj;
	}

	public Term getCurrentTerm() throws ObjectNotFoundException {
		final Date now = new Date();

		final Criteria query = createCriteria();
		query.add(Restrictions.gt("endDate", now));
		query.add(Restrictions.lt("startDate", now));

		final Term term = (Term) query.uniqueResult();

		if (term == null) {
			throw new ObjectNotFoundException("Current Term not Defined",
					"Term");
		} else {
			return term;
		}
	}
}