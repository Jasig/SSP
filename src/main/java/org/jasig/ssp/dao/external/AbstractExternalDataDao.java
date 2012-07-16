package org.jasig.ssp.dao.external;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Abstract ExternalData DAO
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Any <code>ExternalData</code> model.
 */
public abstract class AbstractExternalDataDao<T>
		extends AbstractDao<T>
		implements ExternalDataDao<T> {

	protected NamedParameterJdbcTemplate jdbcTemplate;

	protected AbstractExternalDataDao(@NotNull final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Autowired
	public void init(final DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * Gets the database view name
	 * 
	 * @return
	 */
	protected abstract String getDatabaseViewName();

	/**
	 * Gets the row mapper that converts database results to equivalent objects.
	 * 
	 * @return the row mapper that converts database results to equivalent
	 *         objects
	 */
	protected abstract RowMapper<T> getMapper();

	@Override
	public abstract T get(@NotNull Serializable id)
			throws ObjectNotFoundException;

	@Override
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		final List<T> rows = jdbcTemplate.getJdbcOperations().query(
				"select * from " + getDatabaseViewName(), getMapper());
		return new PagingWrapper<T>(rows);
	}

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		sql.append(getDatabaseViewName());

		if (sAndP != null) {
			if (sAndP.isSorted()) {
				sql.append(" order by ");
				int count = 0;
				for (final Pair<String, SortDirection> sort : sAndP
						.getSortFields()) {
					if (count++ > 0) {
						sql.append(", ");
					}

					sql.append(sort.getFirst());
					sql.append(" ");
					sql.append(sort.getSecond().toString());
				}

				sql.append(" ");
			} else if (sAndP.isDefaultSorted()) {
				sql.append(" order by ");
				sql.append(sAndP.getDefaultSortProperty());
				sql.append(" ");
				sql.append(sAndP.getDefaultSortDirection().toString());
				sql.append(" ");
			}

			// TODO: add paging limits (per RDBMS?)
		}

		final List<T> rows = jdbcTemplate.getJdbcOperations().query(
				sql.toString(), getMapper());
		return new PagingWrapper<T>(rows);
	}
}