package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonSelfHelpGuideResponseService
		extends AuditablePersonAssocService<SelfHelpGuideResponse> {

	SelfHelpGuideResponse initiateSelfHelpGuideResponse(
			SelfHelpGuide selfHelpGuide,
			Person person)
			throws ObjectNotFoundException;

	boolean answerSelfHelpGuideQuestion(
			final SelfHelpGuideResponse selfHelpGuideResponse,
			final SelfHelpGuideQuestion selfHelpGuideQuestion,
			final Boolean response)
			throws ObjectNotFoundException;

	boolean completeSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse)
			throws ObjectNotFoundException;

	boolean cancelSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse)
			throws ObjectNotFoundException;

	SelfHelpGuideResponseTO getSelfHelpGuideResponseFor(
			SelfHelpGuideResponse selfHelpGuideResponse,
			SortingAndPaging referralSAndP)
			throws ObjectNotFoundException;
}
