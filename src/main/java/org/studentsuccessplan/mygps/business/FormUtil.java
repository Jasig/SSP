package org.studentsuccessplan.mygps.business;

import java.util.UUID;

import org.studentsuccessplan.mygps.model.transferobject.FormOptionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormQuestionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormSectionTO;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;

public class FormUtil {

	public static FormSectionTO getFormSectionById(UUID formSectionId,
			FormTO formTO) {

		for (FormSectionTO formSectionTO : formTO.getSections()) {
			if (formSectionTO.getId().equals(formSectionId)) {
				return formSectionTO;
			}
		}

		return null;
	}

	public static FormQuestionTO getFormQuestionById(UUID formQuestionId,
			FormSectionTO formSectionTO) {

		for (FormQuestionTO formQuestionTO : formSectionTO.getQuestions()) {
			if (formQuestionTO.getId().equals(formQuestionId)) {
				return formQuestionTO;
			}
		}

		return null;
	}

	public static FormOptionTO getFormOptionById(UUID formOptionId,
			FormQuestionTO formQuestionTO) {

		for (FormOptionTO formOptionTO : formQuestionTO.getOptions()) {
			if (formOptionTO.getId().equals(formOptionId)) {
				return formOptionTO;
			}
		}

		return null;
	}

	public static FormOptionTO getFormOptionByValue(String value, FormQuestionTO formQuestionTO) {

		for (FormOptionTO formOptionTO : formQuestionTO.getOptions()) {
			if (formOptionTO.getValue().toUpperCase().equals(value.toUpperCase())) {
				return formOptionTO;
			}
		}

		return null;
	}

}
