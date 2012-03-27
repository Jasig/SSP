package edu.sinclair.ssp.service.tool.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;
import edu.sinclair.ssp.model.PersonEducationGoal;
import edu.sinclair.ssp.model.PersonEducationPlan;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.reference.CitizenshipService;
import edu.sinclair.ssp.service.reference.EthnicityService;
import edu.sinclair.ssp.service.reference.MaritalStatusService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;
import edu.sinclair.ssp.service.tool.IntakeService;

@Service
public class IntakeServiceImpl implements IntakeService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(IntakeService.class);

	@Autowired
	private MaritalStatusService maritalStatusService;

	@Autowired
	private EthnicityService ethnicityService;

	@Autowired
	private CitizenshipService citizenshipService;

	@Autowired
	private VeteranStatusService veteranStatusService;

	@Autowired
	private PersonService personService;

	/**
	 * Load the specified Person.
	 * 
	 * Be careful when walking the tree to avoid performance issues that can
	 * arise if eager fetching from the database layer is not used
	 * appropriately.
	 */
	@Override
	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException {
		IntakeForm form = new IntakeForm();

		Person person = personService.get(studentId);
		form.setPerson(person);

		return form;
	}

	/**
	 * Copy non-persisted, untrusted model values for a complete Person data
	 * tree, to persisted instances.
	 */
	@Override
	public boolean save(IntakeForm form) throws ObjectNotFoundException {
		if (form.getPerson() == null) {
			throw new ObjectNotFoundException("Missing person identifier.");
		}

		// Load current Person from storage
		Person pPerson = personService.get(form.getPerson().getId());
		this.overwriteWithCollections(pPerson, form);

		// Save changes to persistent storage.
		personService.save(pPerson);

		return true;
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	@Override
	public void overwriteWithCollections(Person target, IntakeForm source)
			throws ObjectNotFoundException {
		personService.overwrite(target, source.getPerson());

		// Demographics
		if (target.getDemographics() == null
				&& source.getPerson().getDemographics() != null) {
			target.setDemographics(new PersonDemographics());
		}

		if (target.getDemographics() != null) {
			if (source.getPerson().getDemographics() == null) {
				// TODO Does the PersonDemographic instance have to be deleted
				// too? Or will Hibernate automatic orphan control catch it?
				target.setDemographics(null);
			} else {
				PersonDemographics demo = source.getPerson().getDemographics();
				target.getDemographics().overwrite(
						demo,
						demo == null || demo.getMaritalStatus() == null ? null
								: maritalStatusService.get(demo
										.getMaritalStatus().getId()),
						demo == null || demo.getEthnicity() == null ? null
								: ethnicityService.get(demo.getEthnicity()
										.getId()),
						demo == null || demo.getCitizenship() == null ? null
								: citizenshipService.get(demo.getCitizenship()
										.getId()),
						demo == null || demo.getVeteranStatus() == null ? null
								: veteranStatusService.get(demo
										.getVeteranStatus().getId()),
						demo == null || demo.getCoach() == null ? null
								: personService.get(demo.getCoach().getId()));
			}
		}

		// Education goal
		if (target.getEducationGoal() == null
				&& source.getPerson().getEducationGoal() != null) {
			target.setEducationGoal(new PersonEducationGoal());
		}

		if (target.getEducationGoal() != null) {
			if (source.getPerson().getEducationGoal() == null) {
				// TODO Does the PersonEducationGoal instance have to be deleted
				// too? Or will Hibernate automatic orphan control catch it?
				target.setEducationGoal(null);
			} else {
				target.getEducationGoal().overwrite(
						source.getPerson().getEducationGoal());
			}
		}

		// Education plan
		if (target.getEducationPlan() == null
				&& source.getPerson().getEducationPlan() != null) {
			target.setEducationPlan(new PersonEducationPlan());
		}

		if (target.getEducationPlan() != null) {
			if (source.getPerson().getEducationPlan() == null) {
				// TODO Does the PersonEducationPlan instance have to be deleted
				// too? Or will Hibernate automatic orphan control catch it?
				target.setEducationPlan(null);
			} else {
				target.getEducationPlan().overwriteWithCollections(
						source.getPerson().getEducationPlan());
			}
		}

		// various sets
		personService.overwriteWithCollectionsEducationLevels(target, source
				.getPerson().getEducationLevels());
		personService.overwriteWithCollectionsFundingSources(target, source
				.getPerson().getFundingSources());
		personService.overwriteWithCollectionsChallenges(target, source
				.getPerson().getChallenges());
	}
}
