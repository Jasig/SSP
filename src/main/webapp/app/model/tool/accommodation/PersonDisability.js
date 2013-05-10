/*
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
Ext.define('Ssp.model.tool.accommodation.PersonDisability', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'odsRegistrationDate', type: 'date', dateFormat: 'c', useNull: true},
             {name: 'disabilityStatusId', type: 'string'},
             {name: 'intakeCounselor', type: 'string'},
             {name: 'referredBy', type: 'string'},
             {name: 'contactName', type: 'string'},
             {name: 'releaseSigned', type: 'boolean', useNull:true},
             {name: 'recordsRequested', type: 'boolean', useNull:true},
             {name: 'recordsRequestedFrom', type: 'string'},
             {name: 'referForScreening', type: 'boolean', useNull:true},
             {name: 'documentsRequestedFrom', type: 'string'},
             {name: 'rightsAndDuties', type: 'string'},	 
             {name: 'eligibleLetterSent', type: 'boolean', useNull:true},
             {name: 'eligibleLetterDate', type: 'date', dateFormat: 'c'},
             {name: 'ineligibleLetterSent', type: 'boolean', useNull:true},
             {name: 'ineligibleLetterDate', type: 'date', dateFormat: 'c'},
             {name: 'noDocumentation', type: 'boolean', useNull:true},
             {name: 'inadequateDocumentation', type: 'boolean', useNull:true},
             {name: 'noDisability', type: 'boolean', useNull:true},
             {name: 'noSpecialEd', type: 'boolean', useNull:true},
             {name: 'tempEligibilityDescription', type: 'string'},
             {name: 'onMedication', type: 'boolean', useNull:true},
             {name: 'medicationList', type: 'string'},
             {name: 'functionalLimitations', type: 'string'}],

     /*
      * cleans properties that will be unable to be saved if not null
      */ 
     setPropsNullForSave: function( jsonData ){	
 		// remove registration date field.
    	// it was only a substitute for the createdDate,
    	// so the createdDate field would not be modified by
    	// extjs when the record is updated by it's form prior to save.
    	delete jsonData.odsRegistrationDate;
    	 
    	if ( jsonData.intakeCounselor == "")
 		{
 			jsonData.intakeCounselor = null;
 		}
 		if ( jsonData.referredBy == "")
 		{
 			jsonData.referredBy = null;
 		}
 		return jsonData;
     }
});