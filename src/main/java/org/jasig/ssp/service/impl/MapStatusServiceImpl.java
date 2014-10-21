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
package org.jasig.ssp.service.impl;

import org.jasig.ssp.factory.external.ExternalPersonPlanStatusTOFactory;
import org.jasig.ssp.factory.external.MapStatusReportLiteTOFactory;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.MapStatusService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.external.AbstractPlanStatusReportTO;
import org.jasig.ssp.transferobject.external.MapStatusReportLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MapStatusServiceImpl implements MapStatusService {

    @Autowired
    private ConfigService configService;
    @Autowired
    private PersonService personService;
    @Autowired
    private MapStatusReportService mapStatusReportService;
    @Autowired
    private ExternalPersonPlanStatusService externalPersonPlanStatusService;
    @Autowired
    private MapStatusReportLiteTOFactory mapStatusReportLiteTOFactory;
    @Autowired
    private ExternalPersonPlanStatusTOFactory planStatusFactory;


    @Override
    @Transactional(readOnly=true)
    public AbstractPlanStatusReportTO getByPersonId(UUID personId) throws ObjectNotFoundException {
        // pretty much a straight copy/paste of a function that used to be inlined into PlanController. Inconsistent
        // empty value return behaviors are a direct result of that.

        String schoolId = null;
        Person student = personService.get(personId);
        schoolId = student.getSchoolId();

        Boolean calcPlanStatus = Boolean.parseBoolean(configService.getByNameEmpty("calculate_map_plan_status").trim().toLowerCase());

        if(calcPlanStatus)
        {
            PagingWrapper<MapStatusReport> allForPerson = mapStatusReportService.getAllForPerson(student, null);
            if(allForPerson.getRows().size() > 0)
            {
                MapStatusReport report = allForPerson.getRows().iterator().next();
                return mapStatusReportLiteTOFactory.from(report);
            }
            else
                return new MapStatusReportLiteTO(); // legacy inconsistency. see above
        }
        else
        {
            final ExternalPersonPlanStatus externalPlanStatus = externalPersonPlanStatusService.getBySchoolId(schoolId);
            if ( externalPlanStatus == null ) {
                return null; // legacy inconsistency. see above
            }
            return planStatusFactory.from(externalPlanStatus);
        }
    }
}
