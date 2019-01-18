/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject;

import com.google.common.collect.Sets;
import org.jasig.ssp.model.Notification;
import java.util.Date;
import java.util.Set;


public class NotificationTO extends AbstractAuditableTO<Notification>
	implements TransferObject<Notification> {

	private String subject;
	private String body;
    private Date expirationDate;
    private Integer duplicateCount;
    private String priority;
    private String category;
    private Set<NotificationRecipientTO> notificationRecipients = Sets.newHashSet();
    private Set<NotificationReadTO> notificationReads = Sets.newHashSet();


    public NotificationTO() {
		super();
	}

	public NotificationTO(final Notification model) {
		super();
		from(model);
	}

	@Override
	public void from(Notification notification) {
        super.from(notification);

        if (notification != null) {
            this.setSubject(notification.getSubject());
            this.setBody(notification.getBody());
            this.setCategory(notification.getCategory().getTitle());
            this.setPriority(notification.getPriority().getTitle());
            this.setDuplicateCount(notification.getDuplicateCount());
            this.setExpirationDate(notification.getExpirationDate());
        }
        if (notification.getNotificationRecipients() != null
                && !notification.getNotificationRecipients().isEmpty()) {
            notificationRecipients = NotificationRecipientTO.toTOSet(notification.getNotificationRecipients());
        }
        if (notification.getNotificationReads() != null
                && !notification.getNotificationReads().isEmpty()) {
            notificationReads = NotificationReadTO.toTOSet(notification.getNotificationReads());
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getDuplicateCount() {
        return duplicateCount;
    }

    public void setDuplicateCount(Integer duplicateCount) {
        this.duplicateCount = duplicateCount;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<NotificationRecipientTO> getNotificationRecipients() {
        return notificationRecipients;
    }

    public void setNotificationRecipients(Set<NotificationRecipientTO> notificationRecipients) {
        this.notificationRecipients = notificationRecipients;
    }

    public Set<NotificationReadTO> getNotificationReads() {
        return notificationReads;
    }

    public void setNotificationReads(Set<NotificationReadTO> notificationReads) {
        this.notificationReads = notificationReads;
    }
}
