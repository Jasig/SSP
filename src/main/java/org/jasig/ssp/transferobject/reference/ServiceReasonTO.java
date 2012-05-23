package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ServiceReasonTO
		extends AbstractReferenceTO<ServiceReason>
		implements TransferObject<ServiceReason> {

	public ServiceReasonTO() {
		super();
	}

	public ServiceReasonTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ServiceReasonTO(final ServiceReason model) {
		super();
		from(model);
	}

	public static List<ServiceReasonTO> toTOList(
			final Collection<ServiceReason> models) {
		final List<ServiceReasonTO> tObjects = Lists.newArrayList();
		for (ServiceReason model : models) {
			tObjects.add(new ServiceReasonTO(model));
		}
		return tObjects;
	}
}
