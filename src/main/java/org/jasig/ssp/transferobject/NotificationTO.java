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

import org.jasig.ssp.model.Notification;
import org.jasig.ssp.model.NotificationRecipient;
import java.util.Date;


public class NotificationTO extends AbstractAuditableTO<NotificationRecipient>
	implements TransferObject<NotificationRecipient> {

	private String subject;
	private String body;
	private PersonLiteTO recipient;   //TODO is there use case for separate notification and notification_recipient TOs?
    private Date expirationDate;
    private Integer duplicateCount;
    private String priority;
    private String category;
    private String sspRole;

	public NotificationTO() {
		super();
	}

	public NotificationTO(final NotificationRecipient model) {
		super();
		from(model);
	}

	@Override
	public void from(NotificationRecipient model) {
        super.from(model);
        final Notification notification = model.getNotification();

        if (notification != null) {
            this.setSubject(notification.getSubject());
            this.setBody(notification.getBody());
            this.setCategory(notification.getCategory().getTitle());
            this.setPriority(notification.getPriority().getTitle());
            this.setDuplicateCount(notification.getDuplicateCount());
            this.setExpirationDate(notification.getExpirationDate());

            if (model.getPerson() != null) {
                this.setRecipient(new PersonLiteTO(model.getPerson().getId(), model.getPerson().getFirstName(), model.getPerson().getLastName()));
            }

            if (model.getSspRole() != null) {
                this.setSspRole(model.getSspRole());
            }

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

    public PersonLiteTO getRecipient() {
        return recipient;
    }

    public void setRecipient(PersonLiteTO recipient) {
        this.recipient = recipient;
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

    public String getSspRole() {
        return sspRole;
    }

    public void setSspRole(String sspRole) {
        this.sspRole = sspRole;
    }
}
