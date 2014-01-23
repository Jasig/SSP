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
Ext.define('Ssp.model.external.FinancialAidAward', {
	extend: 'Ssp.model.external.AbstractExternal',
    fields: [{name: 'termCode', type: 'string'},
             {name: 'accepted', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name: 'acceptedLong', type: 'string'}],
             
	populateFromExternalData: function(data){
    	var me=this;
    	me.set("termCode", data.termCode);
    	me.set("schoolId", data.schoolId);
    	me.set("accepted", data.accepted);
		me.set("acceptedLong", data.accepted.toLowerCase() == "y" ? "Accepted" : "Not Accepted");
		me.commit();
    }
});