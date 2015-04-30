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
package org.jasig.ssp.dao;


import org.jasig.ssp.model.*;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.PersonCoachAuditTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Methods for saving and retrieving Person Coach audit history
 */
@Repository
public class PersonCoachAuditDao extends AbstractDao<PersonCoachAuditTO> {

    @Autowired
    private transient SecurityService securityService;

    private static final String PERSON_COACH_AUDIT_HQL_QUERY =
            "SELECT personCoachAudit.originalId.id, personCoachAudit.coach_id, " +
            "personCoachRevisionInfo.modifiedBy, personCoachRevisionInfo.modifiedDate " +
            "FROM org.jasig.ssp.model.Person_AUD AS personCoachAudit " +
            "INNER JOIN personCoachAudit.originalId.REV AS personCoachRevisionInfo " +
            "WHERE personCoachAudit.originalId.id = :personId " +
            "ORDER BY personCoachAudit.originalId.REV DESC ";
            //this HQL is specific to mappings created by Envers which does differ from names granted in src and Liquibase

    private static final String AUDIT_PERSON_HQL_QUERY =
            "SELECT auditPerson FROM AuditPerson as auditPerson WHERE auditPerson.id IN :personIds";

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCoachAuditDao.class);


    public PersonCoachAuditDao () {
        super(PersonCoachAuditTO.class);
    }

    /**
     * Retrives the last two revisions showing the current coach and previous coach using Envers
     * @param personId
     * @return PersonCoachAuditTO
     */
    public PersonCoachAuditTO getPreviousCoach(final UUID personId) {

        final PersonCoachAuditTO personCoachAuditTOReturn = new PersonCoachAuditTO();

        if (personId != null) {

            try {

                final List<Object> queryResults = createHqlQuery(PERSON_COACH_AUDIT_HQL_QUERY)
                        .setParameter("personId", personId).setMaxResults(2).list();
                // object returned is: UUID person.id(student), UUID person.coach_id(current coach), audit_person (modified_by), date (modified_date)

                if ( queryResults != null && queryResults.size() > 0 ) {

                    final UUID[] previousAndCurrentCoachUUIDs = new UUID[2];
                    final Object[] lastCoachRevision = (Object[]) queryResults.get(0); //last coach revision

                    previousAndCurrentCoachUUIDs[0] = (UUID) lastCoachRevision[1];

                    if ( queryResults.size() > 1 ) {
                        previousAndCurrentCoachUUIDs[1] = (UUID) ((Object[]) queryResults.get(1))[1]; //had a previous coach, need only the UUID
                    }

                    final List<AuditPerson> currentAndPreviousCoachAuditPersons = createHqlQuery(AUDIT_PERSON_HQL_QUERY)
                            .setParameterList("personIds", previousAndCurrentCoachUUIDs).setMaxResults(2).list();
                    // grabs the 1 or 2 AuditPersons from UUIDS retrieved previously

                    if ( currentAndPreviousCoachAuditPersons != null ) {
                        personCoachAuditTOReturn.setCurrentCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(0))); //"generally" guaranteed at least the current coach was recorded
                    }

                    if ( lastCoachRevision[2] != null ) {
                        personCoachAuditTOReturn.setModifiedBy(new PersonLiteTO((AuditPerson) lastCoachRevision[2]));
                    }

                    if ( lastCoachRevision[3] != null ) {
                        personCoachAuditTOReturn.setModifiedDate((Date) lastCoachRevision[ 3 ]);
                    }

                    personCoachAuditTOReturn.setStudentId(personId);

                    if ( currentAndPreviousCoachAuditPersons != null && currentAndPreviousCoachAuditPersons.size() > 1 ) {
                        personCoachAuditTOReturn.setPreviousCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(1)));
                    }
                }
            } catch (ClassCastException | NullPointerException cne) {
                LOGGER.error("Error retrieving Coach Audit record(s) for a Person! Id[" + personId + "] " + cne);
            }
        }

        return personCoachAuditTOReturn;
    }

    /**
     * Normally Coach changes are automatically logged by Envers, but in the case of Bulk Coach Re-Assign,
     *  which uses HQL, it cannot be intercepted by Envers.
     * @return int saved result count (-1) on error/null parameter or (0) on empty parameter
     */
    public int auditBatchCoachAssignment (Person coachToSet, List<String> batchOfStudentSchoolIds) {
        if (coachToSet != null && batchOfStudentSchoolIds != null) {
            if (coachToSet.getId() != null && batchOfStudentSchoolIds.size() > 0) {
                UUID currentUserUUID = securityService.currentlyAuthenticatedUser().getPerson().getId();

            //TODO Bulk Insert to person_coach_audit and person_coach_revision_info


            }
            return 0;
        }
        return -1;
    }
}
