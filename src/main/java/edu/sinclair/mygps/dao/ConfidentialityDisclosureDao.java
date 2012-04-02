package edu.sinclair.mygps.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.mygps.util.MyGpsStringUtils;
import edu.sinclair.ssp.model.Person;

@Repository
public class ConfidentialityDisclosureDao {

	private static final Logger logger = LoggerFactory.getLogger(ConfidentialityDisclosureDao.class);

	@Autowired
	private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	private Object[] getConfidentialityDisclosureByStudent(Person student) {
		List<Object[]> results = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT confidentialityDisclosureID, studentID, agreeToTerms FROM ConfidentialityDisclosureByStudent where studentID = ? ")
				.addScalar("confidentialityDisclosureID")
				.addScalar("studentID")
				.addScalar("agreeToTerms")
				.setParameter(0, student.getId())
				.list();

		if(1==results.size()){
			return results.get(0);
		}else if(results.size()>1){
			//very unexpected...
			logger.error("ConfidentialityDisclosure Agreement problem - more than one entry exists for student: " + student.getId());
			return null;
		}else{
			return null;
		}
	}

	public boolean getAgreementByStudent(Person student) {
		Object[] results = getConfidentialityDisclosureByStudent(student);

		if((null!=results) && ((Boolean) results[2]==true)){
			return true;
		}else{
			return false;
		}
	}

	protected void saveAgreementByExistingStudentExplicitFalse(Person student,
			String modifiedBy) {
		//look for existing
		Object[] results = getConfidentialityDisclosureByStudent(student);
		if(null!=results){
			String confidentialityDisclosureId = (String) results[0];

			if((Boolean) results[2]){
				this.sessionFactory.getCurrentSession()
				.createSQLQuery("update ConfidentialityDisclosureByStudent "
						+ "set agreeToTerms=0 "
						+ "where confidentialityDisclosureID = ? and studentID = ?")
						.setParameter(0, confidentialityDisclosureId)
						.setParameter(1, student.getId())
						.executeUpdate();
			}
		}
	}

	public void saveAgreementByStudent(Person student, boolean agreement,
			String modifiedBy) {
		//look for existing
		Object[] results = getConfidentialityDisclosureByStudent(student);

		String confidentialityDisclosureId = null;

		//existing?
		if(null!=results){
			confidentialityDisclosureId = (String) results[0];
			//Need to update it.
			this.sessionFactory.getCurrentSession()
			.createSQLQuery("update ConfidentialityDisclosureByStudent "
					+ "set agreeToTerms=? "
					+ "where confidentialityDisclosureID = ? and studentID = ?")
					.setParameter(0, (agreement?1:0))
					.setParameter(1, confidentialityDisclosureId)
					.setParameter(2, student.getId())
					.executeUpdate();

			//not existing
		}else{
			confidentialityDisclosureId = MyGpsStringUtils.coldfusionStringFromUUID(UUID.randomUUID());
			this.sessionFactory.getCurrentSession()
			.createSQLQuery("insert into ConfidentialityDisclosureByStudent("
					+ "confidentialityDisclosureID, studentID, agreeToTerms) values(?, ?, ?)")
					.setParameter(0, confidentialityDisclosureId)
					.setParameter(1, student.getId())
					.setParameter(2, 1)
					.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	public String getDisclosureAgreementText(){
		List<Object> results = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT confidentialityDisclosure FROM ConfidentialityDisclosureLU "
						+ "where confidentialitydisclosureLUID = ?")
						.addScalar("confidentialityDisclosure")
						.list();
		// :TODO - return first in descending order

		return (String) results.get(0);
	}
}
