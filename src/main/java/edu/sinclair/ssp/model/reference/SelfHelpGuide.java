package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * SelfHelpGuide reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuide extends AbstractReference implements
		Serializable {

	@Column(name = "threshold")
	private int threshold;

	@Column(name = "introductoryText", nullable = false)
	private String introductoryText;

	@Column(name = "summaryText", nullable = false)
	private String summaryText;

	@Column(name = "summaryTextEarlyAlert", nullable = false)
	private String summaryTextEarlyAlert;

	@Column(name = "summaryTextThreshold")
	private String summaryTextThreshold;

	@Column(name = "authenticationRequired", nullable = false)
	private boolean authenticationRequired;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selfHelpGuide")
	private Set<SelfHelpGuideQuestion> selfHelpGuideQuestions = new HashSet<SelfHelpGuideQuestion>(
			0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "self_help_guide_self_help_guide_group",
			joinColumns = { @JoinColumn(name = "self_help_guide_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "self_help_guide_group_id", nullable = false, updatable = false) })
	private Set<SelfHelpGuideGroup> selfHelpGuideGroups = new HashSet<SelfHelpGuideGroup>(
			0);

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SelfHelpGuide() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public SelfHelpGuide(UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public SelfHelpGuide(UUID id, String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public SelfHelpGuide(UUID id, String name, String description) {
		super(id, name, description);
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getIntroductoryText() {
		return introductoryText;
	}

	public void setIntroductoryText(String introductoryText) {
		this.introductoryText = introductoryText;
	}

	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(String summaryText) {
		this.summaryText = summaryText;
	}

	public String getSummaryTextEarlyAlert() {
		return summaryTextEarlyAlert;
	}

	public void setSummaryTextEarlyAlert(String summaryTextEarlyAlert) {
		this.summaryTextEarlyAlert = summaryTextEarlyAlert;
	}

	public String getSummaryTextThreshold() {
		return summaryTextThreshold;
	}

	public void setSummaryTextThreshold(String summaryTextThreshold) {
		this.summaryTextThreshold = summaryTextThreshold;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public void setAuthenticationRequired(boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}

	public Set<SelfHelpGuideQuestion> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			Set<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public Set<SelfHelpGuideGroup> getSelfHelpGuideGroups() {
		return selfHelpGuideGroups;
	}

	public void setSelfHelpGuideGroups(
			Set<SelfHelpGuideGroup> selfHelpGuideGroups) {
		this.selfHelpGuideGroups = selfHelpGuideGroups;
	}

}
