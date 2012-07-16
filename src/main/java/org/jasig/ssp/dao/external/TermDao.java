package org.jasig.ssp.dao.external;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Term reference entity.
 */
@Repository
public class TermDao extends AbstractExternalDataDao<Term> {

	private static final String DATABASE_VIEW = "v_external_term";

	protected RowMapper<Term> mapper;

	public TermDao() {
		super(Term.class);

		mapper = new RowMapper<Term>() {
			@Override
			public Term mapRow(final ResultSet rs, final int rowNum)
					throws SQLException {
				final Term term = new Term();
				term.setCode(rs.getString("code"));
				term.setName(rs.getString("name"));
				term.setStartDate(rs.getDate("start_date"));
				term.setEndDate(rs.getDate("end_date"));
				term.setReportYear(rs.getInt("report_year"));
				return term;
			}
		};
	}

	@Override
	protected String getDatabaseViewName() {
		return DATABASE_VIEW;
	}

	@Override
	protected RowMapper<Term> getMapper() {
		return mapper;
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 *            the <code>{@link Term}.Code</code> value, must be a String
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	@Override
	public Term get(@NotNull final Serializable id)
			throws ObjectNotFoundException {
		if (!(id instanceof String) || StringUtils.isWhitespace((String) id)) {
			throw new ObjectNotFoundException(id, Term.class.getName());
		}

		final SqlParameterSource namedParameters = new MapSqlParameterSource(
				"code",
				id);

		try {
			return jdbcTemplate.queryForObject(
					"select * from v_external_term  where code = :code",
					namedParameters, mapper);
		} catch (final EmptyResultDataAccessException exc) {
			throw new ObjectNotFoundException(id, Term.class.getName(), exc);
		}
	}
}