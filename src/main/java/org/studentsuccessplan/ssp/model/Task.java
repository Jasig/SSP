package org.studentsuccessplan.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.TaskGroup;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends AbstractTask implements Serializable {

	private static final long serialVersionUID = 1477217415946557983L;

	@ManyToOne
	@JoinColumn(name = "challenge_id", updatable = false)
	private Challenge challenge;

	@ManyToOne
	@JoinColumn(name = "challenge_referral_id", updatable = false)
	private ChallengeReferral challengeReferral;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "task_group_task",
			joinColumns = { @JoinColumn(name = "task_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "task_group_id", nullable = false, updatable = false) })
	private Set<TaskGroup> taskGroups = new HashSet<TaskGroup>(0);

	/**
	 * Constructor that only calls {@link AbstractTask#AbstractTask()}.
	 */
	public Task() {
		super();
	}

	public Task(String description, Date dueDate, Person person,
			Challenge challenge, ChallengeReferral challengeReferral) {
		super(description, dueDate, person);
		this.challenge = challenge;
		this.challengeReferral = challengeReferral;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public ChallengeReferral getChallengeReferral() {
		return challengeReferral;
	}

	public void setChallengeReferral(ChallengeReferral challengeReferral) {
		this.challengeReferral = challengeReferral;
	}

	public Set<TaskGroup> getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(Set<TaskGroup> taskGroups) {
		this.taskGroups = taskGroups;
	}
}
