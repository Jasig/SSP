package org.jasig.ssp.dao.external;

import org.jasig.ssp.model.external.ExternalStudentsByCourse;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentsByCourseDao extends
		AbstractExternalDataDao<ExternalStudentsByCourse> {

	public ExternalStudentsByCourseDao() {
		super(ExternalStudentsByCourse.class);
	}


}
