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
package org.jasig.ssp.factory;

import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

import java.math.BigDecimal;
import java.util.Date;


public interface PersonSearchRequestTOFactory extends
		TOFactory<PersonSearchRequestTO, PersonSearchRequest> {

	PersonSearchRequest from(String studentId, String firstName, String lastName,
			String programStatus,String specialServiceGroup,
			String coachId, String declaredMajor, BigDecimal hoursEarnedMin,
			BigDecimal hoursEarnedMax, BigDecimal gpaEarnedMin,
			BigDecimal gpaEarnedMax, BigDecimal localGpaMin, BigDecimal localGpaMax,
            BigDecimal programGpaMin, BigDecimal programGpaMax,
            String currentlyRegistered, String earlyAlertResponseLate,
			String sapStatusCode, String planStatus, String planExists, Boolean partialPlan,
			Boolean myCaseload, Boolean myPlans,Boolean myWatchList, Date birthDate,
			String actualStartTerm, String personTableType, String homeCampus, String successIndicator, SortingAndPaging sAndP) throws ObjectNotFoundException;

	PersonSearchRequest from(String studentId, String firstName, String lastName,
			String programStatus,String specialServiceGroup,
			String coachId, String declaredMajor, BigDecimal hoursEarnedMin,
			BigDecimal hoursEarnedMax, BigDecimal gpaEarnedMin,
            BigDecimal gpaEarnedMax, BigDecimal localGpaMin, BigDecimal localGpaMax,
            BigDecimal programGpaMin, BigDecimal programGpaMax,
            String currentlyRegistered, String earlyAlertResponseLate,
			String sapStatusCode, String planStatus, String planExists, Boolean partialPlan,
			Boolean myCaseload, Boolean myPlans,Boolean myWatchList, Date birthDate, String actualStartTerm) throws ObjectNotFoundException;

}
