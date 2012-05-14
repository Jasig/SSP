package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Config service
 * 
 * @author daniel.bower
 */
public interface ConfigService extends
		AuditableCrudService<Config> {

	@Override
	PagingWrapper<Config> getAll(SortingAndPaging sAndP);

	@Override
	Config get(UUID id) throws ObjectNotFoundException;

	String getByNameEmpty(String name);

	String getByNameException(String name) throws ObjectNotFoundException;

	String getByNameNull(String name);

	@Override
	Config create(Config obj);

	@Override
	Config save(Config obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
