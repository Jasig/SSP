package org.studentsuccessplan.ssp.web.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.studentsuccessplan.mygps.model.transferobject.FormOptionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormQuestionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormSectionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;
import org.studentsuccessplan.mygps.model.transferobject.MessageTO;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.studentsuccessplan.mygps.model.transferobject.TaskReportTO;
import org.studentsuccessplan.ssp.transferobject.PersonChallengeTO;
import org.studentsuccessplan.ssp.transferobject.PersonDemographicsTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationGoalTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationLevelTO;
import org.studentsuccessplan.ssp.transferobject.PersonEducationPlanTO;
import org.studentsuccessplan.ssp.transferobject.PersonFundingSourceTO;
import org.studentsuccessplan.ssp.transferobject.PersonTO;
import org.studentsuccessplan.ssp.transferobject.TaskTO;
import org.studentsuccessplan.ssp.transferobject.reference.CategoryTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeCategoryTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeReferralTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;
import org.studentsuccessplan.ssp.transferobject.reference.CitizenshipTO;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityLevelTO;
import org.studentsuccessplan.ssp.transferobject.reference.EducationGoalTO;
import org.studentsuccessplan.ssp.transferobject.reference.EducationLevelTO;
import org.studentsuccessplan.ssp.transferobject.reference.EthnicityTO;
import org.studentsuccessplan.ssp.transferobject.reference.FundingSourceTO;
import org.studentsuccessplan.ssp.transferobject.reference.MaritalStatusTO;
import org.studentsuccessplan.ssp.transferobject.reference.MessageTemplateTO;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideGroupTO;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideQuestionTO;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;
import org.studentsuccessplan.ssp.transferobject.reference.StudentStatusTO;
import org.studentsuccessplan.ssp.transferobject.reference.VeteranStatusTO;
import org.studentsuccessplan.ssp.transferobject.tool.IntakeFormTO;

/**
 * Test that all the transfer objects are serializable by the Json serializer.
 * <p>
 * The sample code for this test was provided by the code at the blog post
 * {@link "http://blog.cuttleworks.com/2011/12/http-media-type-not-supported-exception/"}
 * 
 * @author jon.adams
 */
public class JsonDeserialisationTest {
	MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();

	/**
	 * Test that all the SSP transfer objects can be serialized.
	 */
	@Test
	public void sspClassesUsedByOurControllersShouldBeDeserialisableByJackson() {
		assertCanBeMapped(CategoryTO.class);
		assertCanBeMapped(ChallengeCategoryTO.class);
		assertCanBeMapped(ChallengeReferralTO.class);
		assertCanBeMapped(ChallengeTO.class);
		assertCanBeMapped(ChildCareArrangementTO.class);
		assertCanBeMapped(CitizenshipTO.class);
		assertCanBeMapped(ConfidentialityDisclosureAgreementTO.class);
		assertCanBeMapped(ConfidentialityLevelTO.class);
		assertCanBeMapped(EducationGoalTO.class);
		assertCanBeMapped(EducationLevelTO.class);
		assertCanBeMapped(EthnicityTO.class);
		assertCanBeMapped(FundingSourceTO.class);
		assertCanBeMapped(MaritalStatusTO.class);
		assertCanBeMapped(MessageTemplateTO.class);
		assertCanBeMapped(SelfHelpGuideGroupTO.class);
		assertCanBeMapped(SelfHelpGuideQuestionTO.class);
		assertCanBeMapped(SelfHelpGuideTO.class);
		assertCanBeMapped(StudentStatusTO.class);
		assertCanBeMapped(VeteranStatusTO.class);
		assertCanBeMapped(IntakeFormTO.class);
		assertCanBeMapped(PersonChallengeTO.class);
		assertCanBeMapped(PersonDemographicsTO.class);
		assertCanBeMapped(PersonEducationGoalTO.class);
		assertCanBeMapped(PersonEducationLevelTO.class);
		assertCanBeMapped(PersonEducationPlanTO.class);
		assertCanBeMapped(PersonFundingSourceTO.class);
		assertCanBeMapped(PersonTO.class);
	}

	/**
	 * Test that all the MyGPS transfer objects can be serialized.
	 */
	@Test
	public void mygpsClassesUsedByOurControllersShouldBeDeserialisableByJackson() {
		assertCanBeMapped(ChallengeReferralTO.class);
		assertCanBeMapped(FormOptionTO.class);
		assertCanBeMapped(FormQuestionTO.class);
		assertCanBeMapped(FormSectionTO.class);
		assertCanBeMapped(FormTO.class);
		assertCanBeMapped(MessageTO.class);
		assertCanBeMapped(org.studentsuccessplan.mygps.model.transferobject.PersonTO.class);
		assertCanBeMapped(SelfHelpGuideDetailTO.class);
		assertCanBeMapped(SelfHelpGuideQuestionTO.class);
		assertCanBeMapped(SelfHelpGuideResponseTO.class);
		assertCanBeMapped(TaskReportTO.class);
		assertCanBeMapped(TaskTO.class);
	}

	private void assertCanBeMapped(Class<?> classToTest) {
		assertTrue(
				classToTest.getSimpleName()
						+ " is not deserialisable, check the swallowed exception in org.codehaus.jackson.map.deser.StdDeserializerProvider.hasValueDeserializerFor",
				converter.canRead(classToTest, MediaType.APPLICATION_JSON));
	}
}
