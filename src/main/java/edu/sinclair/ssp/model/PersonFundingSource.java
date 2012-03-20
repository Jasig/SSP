package edu.sinclair.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.FundingSource;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonFundingSource extends Auditable {

	@Column(length = 255)
	@Size(max = 255)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false, updatable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funding_source_id", nullable = false, updatable = false)
	private FundingSource fundingSource;

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
