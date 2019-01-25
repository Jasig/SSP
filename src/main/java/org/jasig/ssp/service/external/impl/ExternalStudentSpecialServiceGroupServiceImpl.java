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
package org.jasig.ssp.service.external.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentSpecialServiceGroupDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.external.ExternalStudentSpecialServiceGroup;
import org.jasig.ssp.model.reference.NotificationCategory;
import org.jasig.ssp.model.reference.NotificationPriority;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.model.reference.SspRole;
import org.jasig.ssp.service.NotificationService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSpecialServiceGroupService;
import org.jasig.ssp.service.external.ExternalStudentSpecialServiceGroupService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ExternalStudentSpecialServiceGroupServiceImpl extends
		AbstractExternalDataService<ExternalStudentSpecialServiceGroup>
            implements ExternalStudentSpecialServiceGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalStudentSpecialServiceGroupServiceImpl.class);
	private static Boolean SSG_SET_FROM_EXTERNAL = null;
	private static Boolean SSG_UNSET_FROM_EXTERNAL = null;
	private static Map<String, SpecialServiceGroup> SSG_BY_CODE = Maps.newHashMap();

    @Autowired
    private transient ConfigService configService;

    @Autowired
    private transient PersonSpecialServiceGroupService personSpecialServiceGroupService;

    @Autowired
    private transient SpecialServiceGroupService specialServiceGroupService;

	@Autowired
	private transient ExternalStudentSpecialServiceGroupDao dao;

    @Autowired
    private transient NotificationService notificationService;

	@Override
	protected ExternalDataDao<ExternalStudentSpecialServiceGroup> getDao() {
		return dao;
	}

	@Override
	public List<ExternalStudentSpecialServiceGroup> getStudentExternalSpecialServiceGroups(String schoolId) {
		return dao.getStudentSpecialServiceGroups(schoolId);
	}

	@Override
    public Set<PersonSpecialServiceGroup> getStudentsExternalSSGsSyncedAsInternalSSGs(final Person studentPerson) {
        final Set<PersonSpecialServiceGroup> internalSSGsForStudent = Sets.newHashSet();

        if (studentPerson != null && StringUtils.isNotBlank(studentPerson.getSchoolId())) {
            final List<ExternalStudentSpecialServiceGroup> essgsForStudent =
                            dao.getStudentSpecialServiceGroups(studentPerson.getSchoolId());

            if (CollectionUtils.isNotEmpty(essgsForStudent)) {
                for (ExternalStudentSpecialServiceGroup essg : essgsForStudent) {
                    final SpecialServiceGroup ssg = getInternalSpecialServiceGroupCode(essg.getCode().trim());
                    if (ssg != null) {
                        internalSSGsForStudent.add(new PersonSpecialServiceGroup(studentPerson, ssg));
                    }
                }
            }
        }

        return internalSSGsForStudent;
    }

    @Override
    public Map<String, Set<SpecialServiceGroup>> getMultipleStudentsExternalSSGsSyncedAsInternalSSGs(
            final List<String> schoolIds) {

        final Map<String, Set<SpecialServiceGroup>> results = Maps.newHashMap();

        if (CollectionUtils.isNotEmpty(schoolIds)) {
            final List<ExternalStudentSpecialServiceGroup> essgsForStudents =
                    dao.getStudentSpecialServiceGroupsBySchoolIds(schoolIds);

            if (CollectionUtils.isNotEmpty(essgsForStudents)) {
                for (ExternalStudentSpecialServiceGroup essg : essgsForStudents) {
                    final SpecialServiceGroup ssg = getInternalSpecialServiceGroupCode(essg.getCode().trim());
                    if (ssg != null) {
                        if (!results.containsKey(essg.getSchoolId())) {
                            results.put(essg.getSchoolId(), Sets.newHashSet(SSG_BY_CODE.get(essg.getCode())));
                        } else {
                            results.get(essg.getSchoolId()).add(SSG_BY_CODE.get(essg.getCode()));
                        }
                    }
                }
            }
        }

        return results;
    }

    @Override
    public List<String> getAllSchoolIdsWithSpecifiedSSGs(final List<SpecialServiceGroup> ssgParams) {
        final List<String> ssgCodes = Lists.newArrayList();
        for (SpecialServiceGroup ssg : ssgParams) {
            ssgCodes.add(ssg.getCode());
        }

        return dao.getAllSchoolIdsWithSpecifiedSSGs(ssgCodes);
    }

    @Override
	public void updatePersonSSGsFromExternalPerson(final Person studentPersonToUpdate) {
        if (studentPersonToUpdate != null) {

            if (SSG_SET_FROM_EXTERNAL == null && SSG_UNSET_FROM_EXTERNAL == null) {
                SSG_SET_FROM_EXTERNAL = configService.getByNameNullOrDefaultValue("special_service_group_set_from_external_data").equalsIgnoreCase("false");
                SSG_UNSET_FROM_EXTERNAL = configService.getByNameNullOrDefaultValue("special_service_group_unset_from_external_data").equalsIgnoreCase("true");
            }

            if (SSG_SET_FROM_EXTERNAL) {
                LOGGER.debug("SSG_SYNC: Skipping all Special Service Group processing for person "
                        + "schoolId '{}' because that operation has been disabled "
                        + "via configuration.", studentPersonToUpdate.getSchoolId());
                return;
            }

            final List<ExternalStudentSpecialServiceGroup> externalSSGsForStudent =
                    dao.getStudentSpecialServiceGroups(studentPersonToUpdate.getSchoolId());
            final List<String> internalSSGCodes =
                    personSpecialServiceGroupService.getAllSSGCodesForPerson(studentPersonToUpdate, null);

            if (CollectionUtils.isEmpty(internalSSGCodes)) {
                if (CollectionUtils.isNotEmpty(externalSSGsForStudent)) {
                    LOGGER.debug("SSG_SYNC: Assigning external SSGs to person '{}'", studentPersonToUpdate.getSchoolId());
                    for (ExternalStudentSpecialServiceGroup externalSSG : externalSSGsForStudent) {
                        createSaveSpecialServiceGroupFromExternal(studentPersonToUpdate, externalSSG); //add all external to empty internal
                    }
                }// else ignore
            } else {
                if (CollectionUtils.isEmpty(externalSSGsForStudent)) {
                    if (SSG_UNSET_FROM_EXTERNAL) {
                        LOGGER.debug("SSG_SYNC: Deleting internal SSGs for person schoolId '{}' because unset is true",
                                studentPersonToUpdate.getSchoolId());
                        personSpecialServiceGroupService.deleteAllForPerson(studentPersonToUpdate); //delete all existing non external
                    } else {
                        LOGGER.trace("SSG_SYNC: Skipping Special Service Group assignment deletion for person " +
                                "schoolId '{}' because that operation has been disabled via configuration.",
                                studentPersonToUpdate.getSchoolId());
                    }
                } else {
                    final List<String> externalCodes = Lists.newArrayList();

                    for (ExternalStudentSpecialServiceGroup externalSSG : externalSSGsForStudent) {
                        externalCodes.add(externalSSG.getCode());
                        if (!internalSSGCodes.contains(externalSSG.getCode().trim())) {
                            LOGGER.debug("SSG_SYNC: Assigning external SSG '{}' to person '{}'", externalSSG.getCode(),
                                    studentPersonToUpdate.getSchoolId());
                           createSaveSpecialServiceGroupFromExternal(studentPersonToUpdate, externalSSG); //external not in internal save
                        }// else equals, so ignore
                    }

                    if (SSG_UNSET_FROM_EXTERNAL) {
                        for (String code : internalSSGCodes) {
                            if (!externalCodes.contains(code)) {
                                LOGGER.debug("SSG_SYNC: Deleting internal SSG '{}' for person schoolId '{}' " +
                                        "because unset is true", code, studentPersonToUpdate.getSchoolId());
                                personSpecialServiceGroupService.deleteByCode(code, studentPersonToUpdate);
                            }
                        }
                    }
                }
            }

        } else {
			LOGGER.error("SSG_SYNC: Can't sync Special Service Groups no person given!");
		}
	}

	private PersonSpecialServiceGroup createSaveSpecialServiceGroupFromExternal(
	        final Person person, final ExternalStudentSpecialServiceGroup externalSSG) {

        final SpecialServiceGroup ssg = getInternalSpecialServiceGroupCode(externalSSG.getCode().trim());

        if (ssg == null) {
            LOGGER.debug("SSG_SYNC: Special Service Group with code '{}' does not exist so skipping" +
                            " SSG sync for person schoolId '{}'", externalSSG.getCode(), person.getSchoolId());
            return null;
        } else {
            final PersonSpecialServiceGroup newSSG = new PersonSpecialServiceGroup(person, ssg);
            try {
                personSpecialServiceGroupService.save(newSSG);
            } catch (ObjectNotFoundException | ValidationException onfve) {
                String message = String.format("SSG_SYNC: Error saving Special Service Group for %s of %s!",
                        person.getSchoolId(), ssg.getCode());
                LOGGER.error(message, onfve);
                notificationService.create("Error saving Special Service Group", message,null,
                        NotificationPriority.H, NotificationCategory.S, SspRole.Administrator);
            }
            return newSSG;
        }
    }

    private SpecialServiceGroup getInternalSpecialServiceGroupCode(final String specialServiceGroupCode) {
        if (SSG_BY_CODE.containsKey(specialServiceGroupCode)) {
            return SSG_BY_CODE.get(specialServiceGroupCode);
        } else {
            try {
                final SpecialServiceGroup ssg = specialServiceGroupService.getByCode(specialServiceGroupCode);
                SSG_BY_CODE.put(ssg.getCode(), ssg);
                return ssg;

            } catch (final ObjectNotFoundException e) {
                LOGGER.debug("Special Service Group " + specialServiceGroupCode + " referenced in external table not "
                        + "available internally. ", e);
                return null;
            }
        }
    }
}