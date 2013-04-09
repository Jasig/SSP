package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractPlan extends AbstractAuditable implements Cloneable {

	@Column(length = 200)
	@Size(max = 200)
	private String name;
	
	@Immutable
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "owner_id", updatable = false, nullable = false)	
	private Person owner;
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
}
