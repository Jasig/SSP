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
Ext.define('Ssp.model.tool.earlyalert.EarlyAlertResponseGrid', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'personId', type: 'string'},
             {name:'courseName', type:'string'},
             {name:'courseTitle', type:'string'},
             {name:'emailCC', type:'string'},
             {name:'campusId', type:'string'},
             {name:'earlyAlertReasonId', type:'string'},
             {name:'earlyAlertReasonIds', type:'auto'},
             {name:'earlyAlertReasonOtherDescription', type:'string'},
             {name:'earlyAlertSuggestionIds', type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription', type:'string'},
             {name:'comment', type:'string'},
             {name:'closedDate', type: 'date', dateFormat: 'time'},
             {name:'closedById', type:'string'}, 
             {name:'sendEmailToStudent', type:'boolean'},
			 
			 {name:'nodeType',type:'string',defaultValue:'early alert'},
             {name:'gridDisplayDetails', type:'string'},
			 
			 {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});