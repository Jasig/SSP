package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class PlanDao extends AbstractPlanDao<Plan> implements AuditableCrudDao<Plan> {


	public PlanDao() {
		super(Plan.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Plan> getAllForStudent(UUID id)
	{
		List<Plan> plans = createCriteria()
		.add(Restrictions.eq("person.id", id)).list();
		return plans;
	}
	
	public Plan getActivePlanForStudent(UUID id)
	{
		Plan activePlan = (Plan) createCriteria()
		.add(Restrictions.eq("person.id", id))
		.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
		.uniqueResult();
		return activePlan;
	}

	@Override
	public Plan cloneAndSave(Plan plan,Person owner) throws CloneNotSupportedException {
		Plan clone = plan.clone();
		clone.setOwner(owner);
		clone.setObjectStatus(plan.getObjectStatus());
		return save(clone);
	}
	
	public PagingWrapper<Plan> getAllForStudent(final SortingAndPaging sAndP,UUID personId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id",personId));
		return processCriteriaWithStatusSortingAndPaging(criteria,
				sAndP);
	}

	public int markOldPlansAsInActive(Plan plan) {
		int updatedEntities = 0;
		String markOldPlansAsInActiveBaseQuery = "update Plan p set p.objectStatus = :objectStatus where p.person = :person and p != :plan";
		updatedEntities += createHqlQuery( markOldPlansAsInActiveBaseQuery )
				.setInteger("objectStatus", ObjectStatus.INACTIVE.ordinal() )
				.setEntity("person", plan.getPerson())
				.setEntity("plan", plan)
				.executeUpdate();
		return updatedEntities;
	}
}
