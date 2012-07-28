package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;

/**
 * FacultyCourse service
 * 
 * @author jon.adams
 */
public interface FacultyCourseService extends
		ExternalDataService<FacultyCourse> {

	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	List<FacultyCourse> getAllCoursesForFaculty(final String facultySchoolId)
			throws ObjectNotFoundException;

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
	 */
	List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException;
	
	/**
	 * Gets the course with the specified id for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @param formattedCourse
	 *            the course
	 * @return The specified course if present in the system
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			String facultySchoolId, final String formattedCourse) 
			throws ObjectNotFoundException;
}