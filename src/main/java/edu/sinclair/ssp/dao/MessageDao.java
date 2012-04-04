package edu.sinclair.ssp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.Message;

@Repository
public class MessageDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Message message) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(message);
	}

	@SuppressWarnings("unchecked")
	public List<Message> selectQueued() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Message " +
						"where sentDate is null " +
						"order by createdDate")
						.setMaxResults(25)
						.list();
	}

}
