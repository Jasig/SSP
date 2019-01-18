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
package org.jasig.ssp.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.NotificationCategory;
import org.jasig.ssp.model.reference.NotificationPriority;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


/**
 * Notification (Message) model
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
final public class Notification extends AbstractAuditable implements Auditable {

    private static final long serialVersionUID = -7643911408668209143L;

    private static final String DATABASE_TABLE_NAME = "notification";

    @Column(nullable = false, length = 250)
    private String subject;

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date expirationDate;

    private Integer duplicateCount;

    @Enumerated(EnumType.STRING)
    private NotificationPriority priority;

    @Enumerated(EnumType.STRING)
    private NotificationCategory category;

    @NotNull
    @OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval=true)
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
    private Set<NotificationRecipient> notificationRecipients;

    @Nullable
    @OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval=true)
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
    private Set<NotificationRead> notificationReads;

    /**
     * Empty constructor
     */
    public Notification() {
        super();
    }

    /**
     * Construct a new notification with the required attributes.
     * @param subject
     * @param body
     * @param expirationDate
     * @param duplicateCount
     * @param priority
     * @param category
     */
    public Notification(final String subject, final String body, final Date expirationDate, final Integer duplicateCount,
                   final NotificationPriority priority, final NotificationCategory category) {
        super();
        setObjectStatus(ObjectStatus.ACTIVE);
        this.setSubject(subject);
        this.setBody(body);
        this.setExpirationDate(expirationDate);
        this.setDuplicateCount(duplicateCount);
        this.setPriority(priority);
        this.setCategory(category);
    }

    @Override
    protected int hashPrime() {
        return 181;
    }

    @Override
    public int hashCode() {
        int result = hashPrime();

        // AbstractAuditable properties
        result *= hashField("id", getId());
        result *= hashField("objectStatus", getObjectStatus());

        // Notification
        result *= hashField("subject", getSubject());
        result *= hashField("body", getBody());
        result *= hashField("expirationDate", expirationDate);
        result *= hashField("duplicateCount", duplicateCount);
        result *= hashField("priority", priority.getCode());
        result *= hashField("category", category.getCode());

        return result;
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

    public NotificationPriority getPriority() {
        return priority;
    }

    public void setPriority(NotificationPriority priority) {
        this.priority = priority;
    }

    public NotificationCategory getCategory() {
        return category;
    }

    public void setCategory(NotificationCategory category) {
        this.category = category;
    }

    @Nullable
    public Set<NotificationRecipient> getNotificationRecipients() {
        return notificationRecipients;
    }

    public void setNotificationRecipients(@Nullable Set<NotificationRecipient> notificationRecipients) {
        this.notificationRecipients = notificationRecipients;
    }

    @Nullable
    public Set<NotificationRead> getNotificationReads() {
        return notificationReads;
    }

    public void setNotificationReads(@Nullable Set<NotificationRead> notificationReads) {
        this.notificationReads = notificationReads;
    }
}
