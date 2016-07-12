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
package org.jasig.ssp.service.external;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.external.ExternalStudentSpecialServiceGroup;
import java.util.List;
import java.util.Set;

public interface ExternalStudentSpecialServiceGroupService extends ExternalDataService<ExternalStudentSpecialServiceGroup> {

    /**
     * Returns all raw external student ssgs for student, they might not match a code in Special Service Groups
     * @param schoolId
     * @return
     */
	List<ExternalStudentSpecialServiceGroup> getStudentExternalSpecialServiceGroups(String schoolId);

    /**
     * Returns ssgs for student that can be adequately matched to internal Special Service Groups by code
     *   on the code field.
     * @param studentPerson
     * @return
     */
    Set<PersonSpecialServiceGroup> getStudentsExternalSSGsSyncedAsInternalSSGs(Person studentPerson);

    /**
     * Updates internal person ssgs from external student ssgs based on configurations and matches on code field
     *   Saves the result into person special service groups
     * @param studentPersonToUpdate
     */
    void updatePersonSSGsFromExternalPerson(Person studentPersonToUpdate);
}
