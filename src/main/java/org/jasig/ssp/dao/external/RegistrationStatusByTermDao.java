package org.jasig.ssp.dao.external;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the RegistrationStatusByTerm reference entity.
 */
@Repository
public class RegistrationStatusByTermDao extends
		AbstractExternalDataDao<RegistrationStatusByTerm> {

	private static final String DATABASE_VIEW = "v_external_registration_status_by_term";

	protected RowMapper<RegistrationStatusByTerm> mapper;

	public RegistrationStatusByTermDao() {
		super(RegistrationStatusByTerm.class);

		mapper = new RowMapper<RegistrationStatusByTerm>() {
			@Override
			public RegistrationStatusByTerm mapRow(final ResultSet rs,
					final int rowNum)
					throws SQLException {
				final RegistrationStatusByTerm registrationStatusByTerm = new RegistrationStatusByTerm();
				registrationStatusByTerm.setSchoolId(rs.getString("school_id"));
				registrationStatusByTerm.setTermCode(rs.getString("term_code"));
				registrationStatusByTerm.setRegisteredCourseCount(rs
						.getInt("registeredCourseCount"));
				return registrationStatusByTerm;
			}
		};
	}

	@Override
	protected String getDatabaseViewName() {
		return DATABASE_VIEW;
	}

	@Override
	protected RowMapper<RegistrationStatusByTerm> getMapper() {
		return mapper;
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 *            the <code>{@link RegistrationStatusByTerm}.Code</code> value,
	 *            must be a String
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	@Override
	public RegistrationStatusByTerm get(@NotNull final Serializable id)
			throws ObjectNotFoundException {
		if (!(id instanceof String) || StringUtils.isWhitespace((String) id)) {
			throw new ObjectNotFoundException(id,
					RegistrationStatusByTerm.class.getName());
		}

		final SqlParameterSource namedParameters = new MapSqlParameterSource(
				"code",
				id);

		try {
			return jdbcTemplate
					.queryForObject(
							"select * from " + getDatabaseViewName()
									+ " where code = :code",
							namedParameters, mapper);
		} catch (final EmptyResultDataAccessException exc) {
			throw new ObjectNotFoundException(id,
					RegistrationStatusByTerm.class.getName(), exc);
		}
	}

	public RegistrationStatusByTerm registeredForCurrentTerm(
			final Person person) {
		// from v_external_registered_for_current_term
		// where school_id =
		person.getSchoolId();
		// and registered_course_count > 0
		// and term_code in (select code from v_external_term where getdate()
		// between start_date and end_date)
		return null;
	}
}
