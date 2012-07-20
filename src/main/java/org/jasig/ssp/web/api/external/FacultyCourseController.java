package org.jasig.ssp.web.api.external;

import java.util.List;

import org.jasig.ssp.factory.external.ExternalFacultyCourseRosterTOFactory;
import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.factory.external.FacultyCourseTOFactory;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalPersonLiteTO;
import org.jasig.ssp.transferobject.external.FacultyCourseTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/person/{facultySchoolId}/instruction")
public class FacultyCourseController extends
		AbstractExternalController<FacultyCourseTO, FacultyCourse> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacultyCourseController.class);

	@Autowired
	protected transient FacultyCourseService service;

	@Override
	protected FacultyCourseService getService() {
		return service;
	}

	@Autowired
	protected transient FacultyCourseTOFactory factory;

	@Autowired
	protected transient ExternalFacultyCourseRosterTOFactory externalFacultyCourseRosterTOFactory;

	@Override
	protected ExternalTOFactory<FacultyCourseTO, FacultyCourse> getFactory() {
		return factory;
	}

	protected FacultyCourseController() {
		super(FacultyCourseTO.class, FacultyCourse.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_INSTRUCTION_READ)
	public @ResponseBody
	PagedResponse<FacultyCourseTO> getAllCoursesForFaculty(
			final @PathVariable String facultySchoolId)
			throws ObjectNotFoundException, ValidationException {
		final List<FacultyCourse> list = getService().getAllCoursesForFaculty(
				facultySchoolId);

		return new PagedResponse<FacultyCourseTO>(true, Long.valueOf(list
				.size()), factory.asTOList(list));
	}

	/**
	 * Gets the course roster for the specified faculty's course.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @param formattedCourse
	 *            the course
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value = "/course/{formattedCourse}/roster", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_INSTRUCTION_READ)
	public @ResponseBody
	PagedResponse<ExternalPersonLiteTO> getRoster(
			final @PathVariable String facultySchoolId,
			final @PathVariable String formattedCourse)
			throws ObjectNotFoundException, ValidationException {
		final List<ExternalFacultyCourseRoster> list = getService()
				.getRosterByFacultySchoolIdAndCourse(facultySchoolId,
						formattedCourse);
		return new PagedResponse<ExternalPersonLiteTO>(true, Long.valueOf(list
				.size()), externalFacultyCourseRosterTOFactory.asTOList(list));
	}
}