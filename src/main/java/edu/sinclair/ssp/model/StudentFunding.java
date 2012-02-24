package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.FundingSource;

public class StudentFunding {

	private UUID id;
	
	private String description;
	
	private Student student;
	
	private FundingSource fundingSource;

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

	public FundingSource getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}
	
}
