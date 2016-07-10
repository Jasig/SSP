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
import org.apache.commons.collections.CollectionUtils;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentSpecialServiceGroupDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.external.ExternalStudentSpecialServiceGroup;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
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

@Service
@Transactional
public class ExternalStudentSpecialServcieGroupServiceImpl extends
		AbstractExternalDataService<ExternalStudentSpecialServiceGroup>
            implements ExternalStudentSpecialServiceGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalStudentSpecialServcieGroupServiceImpl.class);

    @Autowired
    private transient ConfigService configService;

    @Autowired
    private transient PersonSpecialServiceGroupService personSpecialServiceGroupService;

    @Autowired
    private transient SpecialServiceGroupService specialServiceGroupService;

	@Autowired
	private transient ExternalStudentSpecialServiceGroupDao dao;

	@Override
	protected ExternalDataDao<ExternalStudentSpecialServiceGroup> getDao() {
		return dao;
	}
	
	@Override
	public List<ExternalStudentSpecialServiceGroup> getStudentSpecialServiceGroups(String schoolId) {
		return dao.getStudentSpecialServiceGroups(schoolId);
	}

	@Override
	public void updatePersonSSGsFromExternalPerson(final Person personToUpdate) {
        if (personToUpdate != null) {

            if (configService.getByNameNullOrDefaultValue("special_service_group_set_from_external_data")
                    .equalsIgnoreCase("false")) {
                LOGGER.debug("SSG_SYNC: Skipping all Special Service Group processing for person "
                        + "schoolId '{}' because that operation has been disabled "
                        + "via configuration.", personToUpdate.getSchoolId());
                return;
            }

            final List<ExternalStudentSpecialServiceGroup> externalSSGsForStudent = dao.getStudentSpecialServiceGroups(personToUpdate.getSchoolId());
            final List<String> internalSSGCodes = personSpecialServiceGroupService.getAllSSGCodesForPerson(personToUpdate);

            if (CollectionUtils.isEmpty(internalSSGCodes)) {
                if (CollectionUtils.isNotEmpty(externalSSGsForStudent)) {
                    LOGGER.debug("SSG_SYNC: Assigning external SSGs to person '{}'", personToUpdate.getSchoolId());
                    for (ExternalStudentSpecialServiceGroup externalSSG : externalSSGsForStudent) {
                        createSaveSpecialServiceGroupFromExternal(personToUpdate, externalSSG); //add all external to empty internal
                    }
                }// else ignore
            } else {
                if (CollectionUtils.isEmpty(externalSSGsForStudent)) {
                    if ( configService.getByNameNullOrDefaultValue("special_service_group_unset_from_external_data")
                            .equalsIgnoreCase("true") ) {
                        LOGGER.debug("SSG_SYNC: Deleting internal SSGs for person schoolId '{}' because unset is true",
                                personToUpdate.getSchoolId());
                        personSpecialServiceGroupService.deleteAllForPerson(personToUpdate); //delete all existing non external
                    } else {
                        LOGGER.trace("SSG_SYNC: Skipping Special Service Group assignment deletion for person " +
                                "schoolId '{}' because that operation has been disabled via configuration.",
                                personToUpdate.getSchoolId());
                    }
                } else {
                    final List<String> externalCodes = Lists.newArrayList();

                    for (ExternalStudentSpecialServiceGroup externalSSG : externalSSGsForStudent) {
                        externalCodes.add(externalSSG.getCode());
                        if (!internalSSGCodes.contains(externalSSG.getCode().trim())) {
                            LOGGER.debug("SSG_SYNC: Assigning external SSG '{}' to person '{}'", externalSSG.getCode(),
                                    personToUpdate.getSchoolId());
                           createSaveSpecialServiceGroupFromExternal(personToUpdate, externalSSG); //external not in internal save
                        }// else equals, so ignore
                    }

                    if (configService.getByNameNullOrDefaultValue("special_service_group_unset_from_external_data")
                            .equalsIgnoreCase("true")) {
                        for (String code : internalSSGCodes) {
                            if (!externalCodes.contains(code)) {
                                LOGGER.debug("SSG_SYNC: Deleting internal SSG '{}' for person schoolId '{}' " +
                                        "because unset is true", code, personToUpdate.getSchoolId());
                                personSpecialServiceGroupService.deleteByCode(code, personToUpdate);
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
                LOGGER.error("SSG_SYNC: Error saving Special Service Group for " +
                        person.getSchoolId() + " of " + ssg.getCode() + "! ", onfve);
            }
            return newSSG;
        }
    }

    private SpecialServiceGroup getInternalSpecialServiceGroupCode(final String specialServiceGroupCode) {
        try {
            return specialServiceGroupService.getByCode(specialServiceGroupCode);
        } catch (final ObjectNotFoundException e) {
            LOGGER.debug("Special Service Group " + specialServiceGroupCode +" referenced in external table not "
                    + "available internally. ", e);
            return null;
        }
    }
}