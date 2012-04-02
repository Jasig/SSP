package edu.sinclair.mygps.factory;

import java.util.ArrayList;
import java.util.List;

import edu.sinclair.mygps.model.transferobject.SelfHelpGuideTO;
import edu.sinclair.ssp.model.reference.SelfHelpGuide;

public class SelfHelpGuideFactory {

	public static List<SelfHelpGuideTO> selfHelpGuidesToSelfHelpGuideTOs (List<SelfHelpGuide> selfHelpGuides) {

		List<SelfHelpGuideTO> selfHelpGuideTOs = new ArrayList<SelfHelpGuideTO>();

		for (SelfHelpGuide selfHelpGuide : selfHelpGuides) {
			selfHelpGuideTOs.add(selfHelpGuideToSelfHelpGuideTO(selfHelpGuide));
		}

		return selfHelpGuideTOs;

	}

	public static SelfHelpGuideTO selfHelpGuideToSelfHelpGuideTO (SelfHelpGuide selfHelpGuide) {

		SelfHelpGuideTO selfHelpGuideTO = new SelfHelpGuideTO();

		selfHelpGuideTO.setDescription(selfHelpGuide.getDescription());
		selfHelpGuideTO.setId(selfHelpGuide.getId());
		selfHelpGuideTO.setName(selfHelpGuide.getName());

		return selfHelpGuideTO;
	}
}
