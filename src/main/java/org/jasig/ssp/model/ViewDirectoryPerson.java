package org.jasig.ssp.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "v_directory_person")
public class ViewDirectoryPerson extends DirectoryPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1723311730958118497L;

	public ViewDirectoryPerson() {
		// TODO Auto-generated constructor stub
	}

	public ViewDirectoryPerson(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

}
