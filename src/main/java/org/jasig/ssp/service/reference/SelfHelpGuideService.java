package org.jasig.ssp.service.reference;

import java.util.List;

import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.ReferenceService;

/**
 * SelfHelpGuide service
 */
public interface SelfHelpGuideService extends
		ReferenceService<SelfHelpGuide> {

	List<SelfHelpGuide> getBySelfHelpGuideGroup(
			SelfHelpGuideGroup selfHelpGuideGroup);
}