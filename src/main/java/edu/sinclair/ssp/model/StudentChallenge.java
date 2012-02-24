package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Challenge;

public class StudentChallenge {
	
	private UUID id;
	
	private String description;
	
	private Student student;
	
	private Challenge challenge;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

}
