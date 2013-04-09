package org.jasig.ssp.dao;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Template;
import org.springframework.stereotype.Repository;

@Repository
public  class  TemplateDao extends AbstractPlanDao<Template> implements
AuditableCrudDao<Template> {

public TemplateDao() {
	super(Template.class);
}

@Override
public Template cloneAndSave(Template plan,Person owner) throws CloneNotSupportedException {
	return null;
}
}
