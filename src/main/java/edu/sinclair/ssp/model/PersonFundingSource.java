package edu.sinclair.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class PersonFundingSource extends Auditable implements Serializable {

	private static final long serialVersionUID = -1349765434053823165L;

	@Column(length = 255)
	@Size(max = 255)
	private String description;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "funding_source_id", nullable = false, insertable = false, updatable = false)
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

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(PersonFundingSource)
	 */
	public void overwrite(PersonFundingSource source) {
		this.setDescription(source.getDescription());
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(PersonFundingSource)
	 */
	public void overwriteWithFundingSource(PersonFundingSource source) {
		this.overwrite(source);

		this.getFundingSource().overwrite(source.getFundingSource());
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @param person
	 * @see overwriteWithCollections(PersonFundingSource)
	 */
	public void overwriteWithPerson(PersonFundingSource source, Person person) {
		this.overwrite(source);

		this.setPerson(person);
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @param person
	 * @see overwrite(PersonFundingSource)
	 */
	public void overwriteWithPersonAndFundingSource(PersonFundingSource source,
			Person person) {
		this.overwriteWithFundingSource(source);

		this.setPerson(person);
	}
}
