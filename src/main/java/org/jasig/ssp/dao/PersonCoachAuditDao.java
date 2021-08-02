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


import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.envers.RevisionType;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonCoachRevisionEntity;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.PersonCoachAuditTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
            //this HQL uses specific mappings created by Envers which does differ from names granted in src and Liquibase

    private static final String AUDIT_PERSON_HQL_QUERY =
            "SELECT auditPerson FROM AuditPerson as auditPerson WHERE auditPerson.id IN :personIds";

    private static final String UUIDS_FROM_SCHOOLIDS_HQL_QUERY = "SELECT id FROM Person p WHERE p.schoolId IN (:schoolIds)";

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCoachAuditDao.class);


    public PersonCoachAuditDao () {
        super(PersonCoachAuditTO.class);
    }

    /**
     * Retrives the last two revisions showing the current coach and previous coach using Envers
     * @param personId the person UUID
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

                    if ( !CollectionUtils.isEmpty(currentAndPreviousCoachAuditPersons) ) {
                        if (currentAndPreviousCoachAuditPersons.size() < 2) {
                            personCoachAuditTOReturn.setCurrentCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(0))); //"generally" guaranteed at least the current coach was recorded

                        } else if (currentAndPreviousCoachAuditPersons.get(0).getId().equals(previousAndCurrentCoachUUIDs[0])) {
                            personCoachAuditTOReturn.setCurrentCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(0))); //order of auditPerson query not guaranteed
                            personCoachAuditTOReturn.setPreviousCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(1)));

                        } else {
                            personCoachAuditTOReturn.setCurrentCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(1)));
                            personCoachAuditTOReturn.setPreviousCoach(new PersonLiteTO(currentAndPreviousCoachAuditPersons.get(0)));
                        }
                    }

                    if ( lastCoachRevision[2] != null ) {
                        personCoachAuditTOReturn.setModifiedBy(new PersonLiteTO((AuditPerson) lastCoachRevision[2]));
                    }

                    if ( lastCoachRevision[3] != null ) {
                        personCoachAuditTOReturn.setModifiedDate((Date) lastCoachRevision[ 3 ]);
                    }

                    personCoachAuditTOReturn.setStudentId(personId);
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
     *
     * @param coachToSet the coach to set Person object
     * @param batchOfStudentSchoolIds the list of batched student school ids
     *
     * @return int saved result count (-1) on error/null parameter or (0) on empty parameter
     */
    public int auditBatchCoachAssignment (Person coachToSet, List<String> batchOfStudentSchoolIds) {
        if ( coachToSet != null && batchOfStudentSchoolIds != null ) {
            if ( coachToSet.getId() != null && batchOfStudentSchoolIds.size() > 0 ) {

                try {
                    final Session session = sessionFactory.getCurrentSession();
                    final UUID currentUserUUID = securityService.currentlyAuthenticatedUser().getPerson().getId();
                    final PersonCoachRevisionEntity revInfoEntity = new PersonCoachRevisionEntity(new AuditPerson(currentUserUUID)); //create new revinfo

                    session.save(revInfoEntity);  //enter new row into revision info table and return auto-generated id

                    if ( revInfoEntity != null && revInfoEntity.getId() > -1 ) {
                        final List<UUID> studentUUIDs = createHqlQuery(UUIDS_FROM_SCHOOLIDS_HQL_QUERY)
                                .setParameterList("schoolIds", batchOfStudentSchoolIds).list();          //get UUIDS from SchoolIds for the batch

                        for ( UUID studentId : studentUUIDs ) {
                            session.save("org.jasig.ssp.model.Person_AUD", createPersonAudObject(revInfoEntity,
                                    studentId, coachToSet.getId()));
                        }

                        session.flush();
                        session.clear();

                        return studentUUIDs.size();
                    }
                } catch (ClassCastException | NullPointerException cne) {
                    LOGGER.error("Error inserting batched Coach Audit records in bulk Caseload Assign!" + cne);
                }
            } else {
                return 0;
            }
        }
        return -1;
    }

    /**
     * Normally Coach changes are automatically logged by Envers, but in certain cases,
     *  a different modifier (AuditPerson) than the current user must be set. This method
     *   uses HQL, which cannot be intercepted by Envers (PersonCoachRevisionListener) and
     *   records the audit manually.
     *
     * @param personId the person UUID
     * @param coachId the coach UUID
     * @param auditPerson the AuditPerson object
     *
     * @return int saved result count, (-1) on error/null parameter
     */
    public int auditCoachAssignment (final UUID personId, final UUID coachId, final AuditPerson auditPerson) {
        if (personId != null && coachId != null && auditPerson != null ) {
            try {
                final Session session = sessionFactory.getCurrentSession();
                final PersonCoachRevisionEntity revInfoEntity = new PersonCoachRevisionEntity(auditPerson); //create new revinfo

                session.save(revInfoEntity);  //enter new row into revision info table and return auto-generated id

                if ( revInfoEntity != null && revInfoEntity.getId() > -1 ) {
                    session.save("org.jasig.ssp.model.Person_AUD", createPersonAudObject(revInfoEntity, personId, coachId));
                 //   session.flush();
                }

                return 1;

            } catch (ClassCastException | NullPointerException cne) {
                LOGGER.error("Error inserting Coach Audit record in Custom Coach Assign!" + cne);
            }
        }

        return -1;
    }

    private Map<String, Object> createPersonAudObject(final PersonCoachRevisionEntity revInfoEntity,
                                                                            final UUID personId, final UUID coachId) {
        final Map<String, Object> personAuditOriginalId = Maps.newHashMap();  //creates originalId portion of Person_Coach_AUD
        personAuditOriginalId.put("REV", revInfoEntity);
        personAuditOriginalId.put("id", personId);

        final Map<String, Object> personAuditEntity = Maps.newHashMap();    //creates the Person_Coach_AUD record
        personAuditEntity.put("REVTYPE", RevisionType.MOD);
        personAuditEntity.put("coach_id", coachId);
        personAuditEntity.put("originalId", personAuditOriginalId);

        return personAuditEntity;
    }
}