package org.jasig.ssp.web.api.external;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.external.ExternalFacultyCourseRosterTOFactory;
import org.jasig.ssp.factory.external.FacultyCourseTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalFacultyCourseRosterTO;
//import org.jasig.ssp.transferobject.external.SearchStudentCourseTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/person/roster")
public class ExternalCourseRosterController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacultyCourseController.class);

	@Autowired
	protected transient FacultyCourseService service;

	@Autowired
	private PersonService personService;

	protected FacultyCourseService getService() {
		return service;
	}

	@Autowired
	protected transient FacultyCourseTOFactory factory;

	@Autowired
	protected transient ExternalFacultyCourseRosterTOFactory externalFacultyCourseRosterTOFactory;


	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return LOGGER;
	}

	/**
	 * Gets the course roster for the specified faculty's course and student.
	 *
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @param facultyUsername
	 *            The faculty username to use to lookup the associated data.
	 * @param formattedCourse
	 *            the courses enrollments are wanted.
	 * @param studentSchoolId
	 *            The student school ids to use to lookup the associated data.
	 * @param studentUsername
	 *            The student usernames to use to lookup the associated data.
	 * @param sectionCode
	 *            the courses
	 * @param termCode
	 *            term code filter (optional)
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_INSTRUCTION_READ)
	public @ResponseBody
	PagedResponse<ExternalFacultyCourseRosterTO> rosterSearch(
			final @RequestParam(required = false) String facultySchoolId,
			final @RequestParam(required = false) String facultyUsername,
			final @RequestParam(required = false) List<String> studentSchoolId,
			final @RequestParam(required = false) List<String> studentUsername,
			final @RequestParam(required = false) List<String> formattedCourse,
			final @RequestParam(required = false) List<String> sectionCode,
			final @RequestParam(required = false) String termCode)
			throws ObjectNotFoundException, MethodArgumentNotValidException {
/*
		String scrubbedTermCode = StringUtils.trimToNull(termCode);

		Person faculty = getPerson(facultySchoolId, facultyUsername);
		List<String> students = new ArrayList<String>();

		if(studentUsername != null)
			for(String userName:studentUsername){
				Person student = getPerson(null, userName);
				if(student != null){
					students.add(student.getSchoolId());
				}
			}

		if(studentSchoolId != null)
			for(String id:studentSchoolId){
				Person student = getPerson(id, null);
				if(student != null){
					students.add(student.getSchoolId());
				}
			}


		List<ExternalFacultyCourseRoster> list = null;

		SearchStudentCourseTO searchStudentCourse = new SearchStudentCourseTO(faculty != null ? faculty.getSchoolId() : null, scrubbedTermCode,
				 sectionCode,  formattedCourse,  students);

		list = (List<ExternalFacultyCourseRoster>)getService().getEnrollments(searchStudentCourse);
		return new PagedResponse<ExternalFacultyCourseRosterTO>(true, Long.valueOf(list
				.size()), externalFacultyCourseRosterTOFactory.asTOList(list));
	}

	private Person getPerson(String schoolId, String username) throws ObjectNotFoundException{
		Person person = null;
		try{
			if(StringUtils.isNotBlank(schoolId))
				person = personService.getBySchoolId(schoolId, false);
		}catch(ObjectNotFoundException e){
			getLogger().error("Unable to find person with school id: " + schoolId, e);
		}

		try{
		if(person == null && StringUtils.isNotBlank(username))
			person = personService.getByUsername(username, false);
		}catch(ObjectNotFoundException e){
			getLogger().error("Unable to find person with username: " + username, e);
			throw e;
		}

		return person;
		*/
		return null;
	}

}
