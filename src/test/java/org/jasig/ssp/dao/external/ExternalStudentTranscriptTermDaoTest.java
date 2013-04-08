package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ExternalStudentTranscriptTermDaoTest {

	@Autowired
	private transient ExternalStudentTranscriptTermDao dao;

	@Autowired
	private transient SessionFactory sessionFactory;
	
	
	
	@SuppressWarnings("null")
	@Test
	public void getExternalStudentTranscriptTermsBySchoolIdNullTest() {
		 List<ExternalStudentTranscriptTerm> termTranscripts = null;
		 termTranscripts = dao.getExternalStudentTranscriptTermsBySchoolId(Stubs.PersonFixture.STUDENT_0.schoolId());
			assertEquals("Transcripts were not found", termTranscripts.size(), 0);

	}
	
	@SuppressWarnings("null")
	@Test
	public void getExternalStudentTranscriptTermsBySchoolIdTest() {
		 List<ExternalStudentTranscriptTerm> termTranscripts = null;
		termTranscripts = dao.getExternalStudentTranscriptTermsBySchoolId(Stubs.PersonFixture.COACH1_STUDENT0.schoolId());


		assertEquals("Transcripts were not found", termTranscripts.size(), 2);

	}


}
