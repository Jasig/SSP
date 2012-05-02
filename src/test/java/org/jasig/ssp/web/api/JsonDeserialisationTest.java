package org.jasig.ssp.web.api;

import static org.junit.Assert.assertTrue;

import org.jasig.mygps.model.transferobject.FormOptionTO;
import org.jasig.mygps.model.transferobject.FormQuestionTO;
import org.jasig.mygps.model.transferobject.FormSectionTO;
import org.jasig.mygps.model.transferobject.FormTO;
import org.jasig.mygps.model.transferobject.MessageTO;
import org.jasig.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.jasig.mygps.model.transferobject.TaskReportTO;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.PersonChallengeTO;
import org.jasig.ssp.transferobject.PersonDemographicsTO;
import org.jasig.ssp.transferobject.PersonEducationGoalTO;
import org.jasig.ssp.transferobject.PersonEducationLevelTO;
import org.jasig.ssp.transferobject.PersonEducationPlanTO;
import org.jasig.ssp.transferobject.PersonFundingSourceTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.reference.CategoryTO;
import org.jasig.ssp.transferobject.reference.ChallengeCategoryTO;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.reference.ChildCareArrangementTO;
import org.jasig.ssp.transferobject.reference.CitizenshipTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;
import org.jasig.ssp.transferobject.reference.EducationGoalTO;
import org.jasig.ssp.transferobject.reference.EducationLevelTO;
import org.jasig.ssp.transferobject.reference.EthnicityTO;
import org.jasig.ssp.transferobject.reference.FundingSourceTO;
import org.jasig.ssp.transferobject.reference.MaritalStatusTO;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideGroupTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.jasig.ssp.transferobject.reference.StudentStatusTO;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Test that all the transfer objects are serializable by the JSON serializer.
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

		// SSP transfer objects
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

		// MyGPS transfer objects
		assertCanBeMapped(org.jasig.mygps.model.transferobject.ChallengeTO.class);
		assertCanBeMapped(FormOptionTO.class);
		assertCanBeMapped(FormQuestionTO.class);
		assertCanBeMapped(FormSectionTO.class);
		assertCanBeMapped(FormTO.class);
		assertCanBeMapped(MessageTO.class);
		assertCanBeMapped(org.jasig.mygps.model.transferobject.PersonTO.class);
		assertCanBeMapped(org.jasig.mygps.model.transferobject.SelfHelpGuideQuestionTO.class);
		assertCanBeMapped(SelfHelpGuideResponseTO.class);
		assertCanBeMapped(TaskReportTO.class);

		// General purpose transfer objects
		assertCanBeMapped(PagingTO.class);
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
		assertCanBeMapped(org.jasig.mygps.model.transferobject.PersonTO.class);
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
