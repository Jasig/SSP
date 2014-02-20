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
Ext.define('Ssp.model.external.FinancialAid', {
    extend: 'Ssp.model.external.AbstractExternal',
    fields: [{name: 'schoolId', type: 'string'},
             {name: 'financialAidGpa', type: 'auto'},
             {name: 'gpa20BHrsNeeded', type: 'auto'},
             {name: 'gpa20AHrsNeeded', type: 'auto'},
             {name: 'neededFor67PtcCompletion', type: 'auto'},
             {name: 'currentYearFinancialAidAward', type: 'string'},
             {name: 'sapStatus', type: 'string'},
             {name: 'sapStatusCode', type: 'string'},
             {name: 'fafsaDate', type: 'date', dateFormat: 'c'},
             {name: 'currentYearFinancialAidAward', type: 'string'},
             {name: 'financialAidRemaining', type: 'auto'},
             {name: 'originalLoanAmount', type: 'auto'},
             {name: 'balanceOwed', type: 'auto'},
             
             {name: 'financialAidFileStatus', type: 'string'},
             {name: 'eligibleFederalAid', type: 'string'},
             {name: 'termsLeft', type: 'auto'},
             {name: 'institutionalLoanAmount', type: 'auto'}
             ]
});