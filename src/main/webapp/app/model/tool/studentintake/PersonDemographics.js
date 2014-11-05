/*
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
Ext.define('Ssp.model.tool.studentintake.PersonDemographics', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'coachId', type: 'string'},
             {name: 'maritalStatusId', type: 'string'},
             {name: 'citizenshipId', type: 'string'},
             {name: 'ethnicityId', type: 'string'},
			 {name: 'raceId', type: 'string'},
             {name: 'militaryAffiliationId', type: 'string'},
             {name: 'veteranStatusId', type: 'string'},
             {name: 'primaryCaregiver', type: 'boolean'},
             {name: 'childCareNeeded', type: 'boolean'},
             {name: 'employed', type: 'boolean'},
             {name: 'numberOfChildren', type: 'int'},	 
             {name: 'countryOfResidence', type: 'string'},
             {name: 'paymentStatus', type: 'string'},
             {name: 'gender', type: 'string'},
             {name: 'countryOfCitizenship', type: 'string'},
             {name: 'childAges', type: 'string'},
             {name: 'placeOfEmployment', type: 'string'},
             {name: 'shift', type: 'string'},
             {name: 'wage', type: 'string'},
             {name: 'totalHoursWorkedPerWeek', type: 'string'},
             {name: 'local', type: 'string'},
             {name: 'childCareArrangementId', type: 'string'}]
});