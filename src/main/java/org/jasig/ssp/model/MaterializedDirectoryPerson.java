package org.jasig.ssp.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
@Table(name = "mv_directory_person")
public class MaterializedDirectoryPerson extends DirectoryPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9167325639152365254L;

	public MaterializedDirectoryPerson() {
		// TODO Auto-generated constructor stub
	}

	public MaterializedDirectoryPerson(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

}
