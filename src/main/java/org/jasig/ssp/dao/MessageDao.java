/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.service.impl.MessageServiceImpl;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO for the {@link Message} model
 */
@Repository
public class MessageDao extends AbstractAuditableCrudDao<Message> implements
		AuditableCrudDao<Message> {

	/**
	 * Constructor that initializes the instance with the specific class types
	 * for super class method use.
	 */
	public MessageDao() {
		super(Message.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageDao.class);
	/**
	 * Return messages that have not been sent, up to the specified maximum
	 * 
	 * @return Returns up to <code>batchSize</code> messages that have not been
	 *   sent.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<Message> queued(int batchSize) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Message where sentDate is null order by createdDate")
				.setMaxResults(batchSize)
				.list();
	}

	public PagingWrapper<Message> queued(SortingAndPaging sAndP) {
		// will let the caller decide on object status filtering, but since
		// this method is supposed to act like a queue, at least make sure
		// it acts that way by default.
		if (!(sAndP.isSorted()) && !(sAndP.isDefaultSorted())) {
			sAndP.appendSortField("createdDate", SortDirection.ASC);
		}
		// cannot use createCriteria(sAndP) b/c that applies the sort order
		// immediately, which breaks the 'select count()' that runs as part
		// of the query pagination mechanism. The sort order will be applied
		// to the criteria asˇ part of processCriteriaWithSortingAndPaging()
		// below.
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.isNull("sentDate"));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}

	public int archiveAndPruneMessages(Integer messageAgeInDays) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, messageAgeInDays * -1);
		String hql = "INSERT INTO ArchivedMessage(id,createdDate, createdBy,modifiedDate, modifiedBy, objectStatus,subject,  body,  sender, recipient,  recipientEmailAddress,  "
				+ "carbonCopy,	 sentToAddresses,  sentCcAddresses,	 sentBccAddresses,  sentFromAddress, sentReplyToAddress,  sentDate) "  + 
	             "SELECT id,createdDate, createdBy,modifiedDate, modifiedBy,objectStatus,subject,  body,  sender, recipient,  recipientEmailAddress,  "
				+ "carbonCopy,	 sentToAddresses,  sentCcAddresses,	 sentBccAddresses,  sentFromAddress, sentReplyToAddress,  sentDate FROM Message msg"
				+ " Where createdDate < :date and id not in (select message.id from TaskMessageEnqueue)";
		
		int executedInsert = createHqlQuery(hql).setDate("date", date.getTime()).executeUpdate();
		String deleteHql = "delete from Message where createdDate < :date and id not in (select message.id from TaskMessageEnqueue)";
		int executedDelete = createHqlQuery(deleteHql).setDate("date", date.getTime()).executeUpdate();
		if(executedInsert != executedDelete)
		{
			throw new RuntimeException("Number of messages being archived and deleted are not equal, so transaction is being rolled back");
		}
		return executedInsert;
	}
}