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


import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.jasig.ssp.service.PersonCoachRevisionListenerService;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * For auditing Coach changes this adds to the default fields logged when audit field changes in Person model
 */
@Entity
@Table(name="person_coach_revision_info")
@RevisionEntity(PersonCoachRevisionListenerService.class)
public class PersonCoachRevisionEntity implements Serializable {

    private static final long serialVersionUID = 8530217842961662350L;

    @Id
    @RevisionNumber
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int revisionId;

    @RevisionTimestamp
    private long revisionTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private AuditPerson modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;


    public PersonCoachRevisionEntity() {
    }

    public PersonCoachRevisionEntity(AuditPerson auditPerson) {
        final Date currentTimestamp = new Date();
        this.setModifiedDate(currentTimestamp);
        this.setTimestamp(currentTimestamp.getTime());
        this.setModifiedBy(auditPerson);
    }

    public int getId() {
        return this.revisionId;
    }

    public void setId(int id) {
        this.revisionId = id;
    }

    public long getTimestamp() {
        return this.revisionTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.revisionTimestamp = timestamp;
    }

    public Date getModifiedDate() {
        return modifiedDate == null ? null : new Date(modifiedDate.getTime());
    }

    public void setModifiedDate(final Date modifiedDate) {
        this.modifiedDate = modifiedDate == null ? null : new Date(
                modifiedDate.getTime());
    }

    public AuditPerson getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final AuditPerson modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean equals(Object personCoachRevisionEntity) {
        if(this == personCoachRevisionEntity) {
            return true;
        } else if(!(personCoachRevisionEntity instanceof PersonCoachRevisionEntity)) {
            return false;
        } else {
            PersonCoachRevisionEntity that = (PersonCoachRevisionEntity)personCoachRevisionEntity;
            return this.revisionId != that.revisionId?false:this.revisionId == that.revisionId;
        }
    }

    public int hashCode() {
        int result = this.revisionId;
        result = 31 * result + (int)(this.revisionId ^ this.revisionId >>> 32);
        return result;
    }
}