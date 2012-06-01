package org.jasig.ssp.model.reference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;
import org.jasig.ssp.model.Auditable;

/**
 * SelfHelpGuide reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuide
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -1522006854468084935L;

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
	@OrderBy("questionNumber")
	@Where(clause = "object_status <> 3")
	private List<SelfHelpGuideQuestion> selfHelpGuideQuestions = new ArrayList<SelfHelpGuideQuestion>(
			0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "self_help_guide_self_help_guide_group", joinColumns = { @JoinColumn(name = "self_help_guide_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "self_help_guide_group_id", nullable = false, updatable = false) })
	@Where(clause = "object_status <> 3")
	private Set<SelfHelpGuideGroup> selfHelpGuideGroups = new HashSet<SelfHelpGuideGroup>(
			0);

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

	public SelfHelpGuide(final UUID id) {
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

	public SelfHelpGuide(final UUID id, final String name) {
		super(id, name);
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(final int threshold) {
		this.threshold = threshold;
	}

	public String getIntroductoryText() {
		return introductoryText;
	}

	public void setIntroductoryText(final String introductoryText) {
		this.introductoryText = introductoryText;
	}

	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(final String summaryText) {
		this.summaryText = summaryText;
	}

	public String getSummaryTextEarlyAlert() {
		return summaryTextEarlyAlert;
	}

	public void setSummaryTextEarlyAlert(final String summaryTextEarlyAlert) {
		this.summaryTextEarlyAlert = summaryTextEarlyAlert;
	}

	public String getSummaryTextThreshold() {
		return summaryTextThreshold;
	}

	public void setSummaryTextThreshold(final String summaryTextThreshold) {
		this.summaryTextThreshold = summaryTextThreshold;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public void setAuthenticationRequired(final boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}

	public List<SelfHelpGuideQuestion> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			final List<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public Set<SelfHelpGuideGroup> getSelfHelpGuideGroups() {
		return selfHelpGuideGroups;
	}

	public void setSelfHelpGuideGroups(
			final Set<SelfHelpGuideGroup> selfHelpGuideGroups) {
		this.selfHelpGuideGroups = selfHelpGuideGroups;
	}

	@Override
	protected int hashPrime() {
		return 127;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= super.hashCode();

		result *= hashField("threshold", threshold);
		result *= hashField("introductoryText", introductoryText);
		result *= hashField("summaryText", summaryText);
		result *= hashField("summaryTextEarlyAlert", summaryTextEarlyAlert);
		result *= hashField("summaryTextThreshold", summaryTextThreshold);
		result *= authenticationRequired ? 3 : 5;

		// collections are not included here

		return result;
	}
}