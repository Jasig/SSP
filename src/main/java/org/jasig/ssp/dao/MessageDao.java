package org.studentsuccessplan.ssp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.Message;

/**
 * DAO for the {@link Message} model
 */
@Repository
public class MessageDao extends AbstractAuditableCrudDao<Message> implements
		AuditableCrudDao<Message> {

	public MessageDao() {
		super(Message.class);
	}

	/**
	 * Return messages that have not been sent (maximum of 25)
	 * 
	 * @return Returns up to 25 messages that have not been sent.
	 */
	@SuppressWarnings("unchecked")
	public List<Message> queued() {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Message " + "where sentDate is null "
								+ "order by createdDate").setMaxResults(25)
				.list();
	}
}
