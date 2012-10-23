/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.List;

import org.jasig.mygps.model.transferobject.SelfHelpGuideQuestionTO;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;

import com.google.common.collect.Lists;

public class SelfHelpGuideDetailTO extends SelfHelpGuideTO implements
		Serializable {

	private static final long serialVersionUID = 4097508291481320856L;

	private String introductoryText;

	private List<SelfHelpGuideQuestionTO> questions;

	public SelfHelpGuideDetailTO() {
		super();
	}

	public SelfHelpGuideDetailTO(final SelfHelpGuide model) {
		super();
		from(model);
	}

	@Override
	public final void from(final SelfHelpGuide model) {
		super.from(model);

		introductoryText = model.getIntroductoryText();

		questions = Lists.newArrayList();
		for (SelfHelpGuideQuestion question : model.getSelfHelpGuideQuestions()) {
			questions.add(new SelfHelpGuideQuestionTO(question)); // NOPMD
		}
	}

	public String getIntroductoryText() {
		return introductoryText;
	}

	public void setIntroductoryText(final String introductoryText) {
		this.introductoryText = introductoryText;
	}

	public List<SelfHelpGuideQuestionTO> getQuestions() {
		return questions;
	}

	public void setQuestions(final List<SelfHelpGuideQuestionTO> questions) {
		this.questions = questions;
	}
}
