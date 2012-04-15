package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;

public class SelfHelpGuideContentTO extends SelfHelpGuideTO implements
		Serializable {

	private static final long serialVersionUID = 4097508291481320856L;

	private String introductoryText;

	private Set<SelfHelpGuideQuestionTO> questions;

	public String getIntroductoryText() {
		return introductoryText;
	}

	public void setIntroductoryText(String introductoryText) {
		this.introductoryText = introductoryText;
	}

	public Set<SelfHelpGuideQuestionTO> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<SelfHelpGuideQuestionTO> questions) {
		this.questions = questions;
	}

	public void fromModel(SelfHelpGuide model,
			List<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		super.fromModel(model);

		setIntroductoryText(model.getIntroductoryText());
		setQuestions(SelfHelpGuideQuestionTO.setToTOSet(model
				.getSelfHelpGuideQuestions()));
	}
}
