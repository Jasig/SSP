package org.jasig.ssp.dao.external;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalPersonNoteDao extends
		AbstractExternalDataDao<ExternalPersonNote> {

	public ExternalPersonNoteDao() {
		super(ExternalPersonNote.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalPersonNote> getNoteBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		return (List<ExternalPersonNote>)criteria.list();
	}
}
