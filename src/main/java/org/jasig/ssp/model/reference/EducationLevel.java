package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * Education Level reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationLevel
		extends AbstractReference
		implements Auditable {

	public static final UUID NO_DIPLOMA_NO_GED_ID = UUID
			.fromString("5d967ba0-e086-4426-85d5-29bc86da9295");
	public static final UUID GED_ID = UUID
			.fromString("710add1c-7b53-4cbe-86cb-8d7c5837d68b");
	public static final UUID HIGH_SCHOOL_GRADUATION_ID = UUID
			.fromString("f4780d23-fd8a-4758-b772-18606dca32f0");
	public static final UUID SOME_COLLEGE_CREDITS_ID = UUID
			.fromString("c5111182-9e2f-4252-bb61-d2cfa9700af7");
	public static final UUID OTHER_ID = UUID
			.fromString("247165ae-3db4-4679-ac95-ca96488c3b27");

	// More Education Levels exist in the database that are not included above

	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor
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

	public EducationLevel(final UUID id) {
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
	public EducationLevel(final UUID id, final String name) {
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
	public EducationLevel(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 97;
	}
}
