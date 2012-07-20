package org.jasig.ssp.dao.external;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the FacultyCourse reference entity.
 */
@Repository
public class FacultyCourseDao extends AbstractExternalDataDao<FacultyCourse> {

	public FacultyCourseDao() {
		super(FacultyCourse.class);
	}

	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@SuppressWarnings("unchecked")
	public List<FacultyCourse> getAllCoursesForFaculty(
			final String facultySchoolId)
			throws ObjectNotFoundException {
		if (!StringUtils.isNotBlank(facultySchoolId)) {
			throw new ObjectNotFoundException(facultySchoolId,
					FacultyCourse.class.getName());
		}

		return createCriteria().add(
				Restrictions.eq("facultySchoolId", facultySchoolId)).list();
	}
	
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException {
		if (!StringUtils.isNotBlank(formattedCourse)) {
			throw new ObjectNotFoundException(formattedCourse,
					FacultyCourse.class.getName());
		}
		
		return (FacultyCourse) createCriteria().add(
				Restrictions.eq("facultySchoolId", facultySchoolId)).add(
				Restrictions.eq("formattedCourse", formattedCourse))
				.uniqueResult();
	}
}