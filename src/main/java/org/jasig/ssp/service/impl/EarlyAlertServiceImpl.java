package org.jasig.ssp.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlert service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class EarlyAlertServiceImpl extends
		AbstractAuditableCrudService<EarlyAlert>
		implements EarlyAlertService {

	@Autowired
	private transient EarlyAlertDao dao;

	/*
	 * @Autowired
	 * 
	 * private transient CampusService campusService;
	 */

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

	@Autowired
	private transient EarlyAlertSuggestionService earlyAlertSuggestionService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected EarlyAlertDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlert save(@NotNull final EarlyAlert obj)
			throws ObjectNotFoundException {
		final EarlyAlert current = getDao().get(obj.getId());

		current.setCourseName(obj.getCourseName());
		current.setCourseTitle(obj.getCourseTitle());
		current.setEmailCC(obj.getEmailCC());
		current.setCampus(obj.getCampus());
		current.setEarlyAlertReasonOtherDescription(obj
				.getEarlyAlertReasonOtherDescription());
		current.setComment(obj.getComment());
		current.setClosedDate(obj.getClosedDate());
		current.setClosedById(obj.getClosedById());

		if (obj.getPerson() == null) {
			current.setPerson(null);
		} else {
			current.setPerson(personService.get(obj.getPerson().getId()));
		}

		final Set<EarlyAlertReason> earlyAlertReasons = new HashSet<EarlyAlertReason>();
		if (obj.getEarlyAlertReasonIds() != null) {
			for (EarlyAlertReason reason : obj.getEarlyAlertReasonIds()) {
				earlyAlertReasons.add(earlyAlertReasonService.load(reason
						.getId()));
			}
		}

		current.setEarlyAlertReasonIds(earlyAlertReasons);

		final Set<EarlyAlertSuggestion> earlyAlertSuggestions = new HashSet<EarlyAlertSuggestion>();
		if (obj.getEarlyAlertSuggestionIds() != null) {
			for (EarlyAlertSuggestion reason : obj.getEarlyAlertSuggestionIds()) {
				earlyAlertSuggestions.add(earlyAlertSuggestionService
						.load(reason
								.getId()));
			}
		}

		current.setEarlyAlertSuggestionIds(earlyAlertSuggestions);

		return getDao().save(current);
	}

	@Override
	public List<EarlyAlert> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}
	/*
	 * @Override public EarlyAlert create(@NotNull final EarlyAlert obj) { //
	 * Figure student advisor or early alert coordinator
	 * 
	 * final UUID assignedAdvisor = getEarlyAlertAdvisor(obj);
	 * 
	 * // TODO According to EarlyAlert.java comments, changes on this side of //
	 * the association are not persisted. Needs tested.
	 * 
	 * obj.getPerson().setCoach(personService.get(assignedAdvisor));
	 * 
	 * // TODO Create alert
	 * 
	 * // TODO Send e-mail to assigned advisor (coach)
	 * 
	 * // TODO: Send e-mail CONFIRMATION to faculty (obj.getCreatedBy, or //
	 * currentUser?)
	 * 
	 * // Send e-mail copy to any requested CC
	 * 
	 * if (StringUtils.isEmpty(obj.getEmailCC()) { // TODO }
	 * 
	 * // TODO Send-email copy to any applicable notification rule entry email
	 * address
	 * 
	 * return obj; }
	 * 
	 * /** Business logic to determine the advisor that is assigned to the
	 * student for this Early Alert.
	 * 
	 * @param obj EarlyAlert instance
	 * 
	 * @return The assigned advisor / private UUID
	 * getEarlyAlertAdvisor(EarlyAlert obj) { // Check for student already
	 * assigned to an advisor (aka coach)
	 * 
	 * if (obj.getPerson().getCoach() != null &&
	 * obj.getPerson().getCoach().getId() != null) { return
	 * obj.getPerson().getCoach().getId(); }
	 * 
	 * // Get campus Early Alert coordinator
	 * 
	 * if (obj.getCampusId() == null) { throw new
	 * IllegalArgumentException("Campus ID can not be null."); }
	 * 
	 * // Get Campus data
	 * 
	 * Campus campus = campusService.get(obj.getCampusId());
	 * 
	 * if (campus.getEarlyAlertCoordinatorId() != null) {
	 * 
	 * // Return Early Alert coordinator UUID
	 * 
	 * return campus.getEarlyAlertCoordinatorId(); }
	 * 
	 * // TODO If no campus EA Coordinator, assign to default EA Coordinator //
	 * (which is not yet implemented)
	 * 
	 * return null; // TODO getEarlyAlertAdvisor should never return null }
	 */
}
