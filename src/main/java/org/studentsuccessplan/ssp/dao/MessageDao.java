package edu.sinclair.ssp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.Message;

@Repository
public class MessageDao extends AbstractAuditableCrudDao<Message> implements
		AuditableCrudDao<Message> {

	public MessageDao() {
		super(Message.class);
	}

	/**
	 * Return messages that have not been sent (maximum of 25)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> queued() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Message " +
						"where sentDate is null " +
						"order by createdDate")
				.setMaxResults(25)
				.list();
	}

}
