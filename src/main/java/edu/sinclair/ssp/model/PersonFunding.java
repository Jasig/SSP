package edu.sinclair.ssp.model;

import java.util.UUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.sinclair.ssp.model.reference.FundingSource;

public class PersonFunding {

	private UUID id;

	private String description;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id", nullable = false, insertable = false, updatable = false)
	private Person person;

	@ManyToOne()
	@JoinColumn(name = "funding_source_id", nullable = false, insertable = false, updatable = false)
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public FundingSource getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}

}
