package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeTO extends AbstractReferenceTO<Challenge> implements
		TransferObject<Challenge> {

	public ChallengeTO() {
		super();
	}

	public ChallengeTO(UUID id) {
		super(id);
	}

	public ChallengeTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChallengeTO(Challenge model) {
		fromModel(model);
	}

	public void fromModel(Challenge model) {
		super.fromModel(model);

		setSelfHelpGuideQuestion(model.getSelfHelpGuideQuestion());
		setSelfHelpGuideQuestions(model.getSelfHelpGuideQuestions());
		setSelfHelpGuideDescription(model.getSelfHelpGuideDescription());
		setShowInStudentIntake(model.isShowInStudentIntake());
		setShowInSelfHelpSearch(model.isShowInSelfHelpSearch());
		setTags(model.getTags());
	}

	public void addToModel(Challenge model) {
		super.addToModel(model);

		model.setSelfHelpGuideQuestion(getSelfHelpGuideQuestion());
		model.setSelfHelpGuideQuestions(getSelfHelpGuideQuestions());
		model.setSelfHelpGuideDescription(getSelfHelpGuideDescription());
		model.setShowInStudentIntake(isShowInStudentIntake());
		model.setShowInSelfHelpSearch(isShowInSelfHelpSearch());
		model.setTags(getTags());
	}

	@Override
	public void pullAttributesFromModel(Challenge model) {
		fromModel(model);
	}

	@Override
	public Challenge pushAttributesToModel(Challenge model) {
		addToModel(model);
		return model;
	}

	/**
	 * This is the text that will be used in a selfHelpGuideQuestion.
	 */
	@Column(length = 64000)
	private String selfHelpGuideQuestion;

	/**
	 * Just a reference to the questions that reference this Challenge. Think of
	 * as selfHelpQuideChallenges
	 */
	@OneToMany(mappedBy = "challenge")
	private Set<SelfHelpGuideQuestion> selfHelpGuideQuestions = new HashSet<SelfHelpGuideQuestion>(
			0);

	/**
	 * Public description of the challenge
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String selfHelpGuideDescription;

	@Column(nullable = false)
	private boolean showInStudentIntake;

	@Column(nullable = false)
	private boolean showInSelfHelpSearch;

	@Column(length = 255)
	private String tags;

	public String getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(String selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public String getSelfHelpGuideDescription() {
		return selfHelpGuideDescription;
	}

	public void setSelfHelpGuideDescription(String selfHelpGuideDescription) {
		this.selfHelpGuideDescription = selfHelpGuideDescription;
	}

	public boolean isShowInStudentIntake() {
		return showInStudentIntake;
	}

	public void setShowInStudentIntake(boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	public boolean isShowInSelfHelpSearch() {
		return showInSelfHelpSearch;
	}

	public void setShowInSelfHelpSearch(boolean showInSelfHelpSearch) {
		this.showInSelfHelpSearch = showInSelfHelpSearch;
	}

	public Set<SelfHelpGuideQuestion> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			Set<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public Challenge asModel() {
		return pushAttributesToModel(new Challenge());
	}

	public static List<ChallengeTO> listToTOList(List<Challenge> models) {
		List<ChallengeTO> tos = Lists.newArrayList();
		for (Challenge model : models) {
			tos.add(new ChallengeTO(model));
		}
		return tos;
	}
}
