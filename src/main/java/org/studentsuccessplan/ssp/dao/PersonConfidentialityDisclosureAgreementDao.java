package edu.sinclair.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;

@Repository
public class PersonConfidentialityDisclosureAgreementDao extends
		AbstractAuditableCrudDao<PersonConfidentialityDisclosureAgreement>
		implements
		AuditableCrudDao<PersonConfidentialityDisclosureAgreement> {

	public PersonConfidentialityDisclosureAgreementDao() {
		super(PersonConfidentialityDisclosureAgreement.class);
	}

	@SuppressWarnings("unchecked")
	public List<PersonConfidentialityDisclosureAgreement> forStudent(
			Person student) {
		Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonConfidentialityDisclosureAgreement.class)
				.add(Restrictions.eq("person", student))
				.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
				.addOrder(Order.desc("createdDate"));
		return query.list();
	}

	public PersonConfidentialityDisclosureAgreement forStudentAndAgreement(
			Person student, ConfidentialityDisclosureAgreement agreement) {
		Criteria query = sessionFactory
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
