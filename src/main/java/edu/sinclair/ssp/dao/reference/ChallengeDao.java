package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.reference.Challenge;

@Repository
public class ChallengeDao implements ReferenceDao<Challenge>{

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ChallengeDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Challenge> getAll() {
		return (List<Challenge>) this.sessionFactory.getCurrentSession()
				.createQuery("from Challenge ")
				.list();
	}

	@Override
	public Challenge get(UUID id) {
		return (Challenge) this.sessionFactory.getCurrentSession()
				.createQuery("from Challenge " +
							"where id = ? ")
				.setParameter(0, id)
				.list().get(0);
	}

	@Override
	public Challenge save(Challenge obj) {
		this.sessionFactory.getCurrentSession().save(obj);
		return obj;
	}

	@Override
	public void delete(Challenge obj) {
		this.sessionFactory.getCurrentSession().delete(obj);
	}

}
