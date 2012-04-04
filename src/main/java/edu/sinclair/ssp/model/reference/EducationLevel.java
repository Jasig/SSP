package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * EducationLevel reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationLevel extends AbstractReference implements Serializable {

	// :TODO Match these to actual values used in ssp
	// Education Level
	public static final UUID NO_DIPLOMA_NO_GED_ID = UUID
			.fromString("B2D05BB9-5056-A51A-80FD-FE0D53E6EB07");
	public static final UUID GED_ID = UUID
			.fromString("B2D05BF8-5056-A51A-8053-E140B84D65A4");
	public static final UUID HIGH_SCHOOL_GRADUATION_ID = UUID
			.fromString("B2D05C27-5056-A51A-80D2-6A4742E0AB64");
	public static final UUID SOME_COLLEGE_CREDITS_ID = UUID
			.fromString("B2D05C36-5056-A51A-80E7-C017F4882593");
	public static final UUID OTHER_ID = UUID
			.fromString("B2D05C65-5056-A51A-8024-DC8A118A585C");

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public EducationLevel() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public EducationLevel(UUID id) {
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

	public EducationLevel(UUID id, String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public EducationLevel(UUID id, String name, String description) {
		super(id, name, description);
	}
}
