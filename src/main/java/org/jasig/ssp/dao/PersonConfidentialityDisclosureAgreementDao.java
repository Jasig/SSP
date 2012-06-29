package org.jasig.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.springframework.stereotype.Repository;

@Repository
public class PersonConfidentialityDisclosureAgreementDao
		extends
		AbstractPersonAssocAuditableCrudDao<PersonConfidentialityDisclosureAgreement>
		implements
		PersonAssocAuditableCrudDao<PersonConfidentialityDisclosureAgreement> {

	public PersonConfidentialityDisclosureAgreementDao() {
		super(PersonConfidentialityDisclosureAgreement.class);
	}

	@SuppressWarnings(UNCHECKED)
	public List<PersonConfidentialityDisclosureAgreement> forStudent(
			final Person student) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonConfidentialityDisclosureAgreement.class)
				.add(Restrictions.eq("person", student))
				.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
				.addOrder(Order.desc("createdDate"));
		return query.list();
	}

	public PersonConfidentialityDisclosureAgreement forStudentAndAgreement(
			final Person student,
			final ConfidentialityDisclosureAgreement agreement) {
		final Criteria query = sessionFactory
				.getCurrentSession()
				.createCriteria(PersonConfidentialityDisclosureAgreement.class)
				.add(Restrictions.eq("person", student))
				.add(Restrictions.eq("confidentialityDisclosureAgreement",
						agreement))
				.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
				.addOrder(Order.desc("createdDate"));
		return (PersonConfidentialityDisclosureAgreement) query.uniqueResult();
	}

}
