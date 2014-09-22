package org.jasig.ssp.dao;

import org.jasig.ssp.model.AbstractPlan;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public class MapAbstractPlanDao extends AbstractDao<AbstractPlan> {

	public MapAbstractPlanDao() {
		super(AbstractPlan.class);
	}


}
