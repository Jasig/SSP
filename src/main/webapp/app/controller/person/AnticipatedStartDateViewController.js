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
Ext.define('Ssp.controller.person.AnticipatedStartDateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson'
    },
    control: {
    	anticipatedStartTermField: '#anticipatedStartTerm',
    	anticipatedStartYearField: '#anticipatedStartYear',
    	abilityToBenefitField: '#abilityToBenefit'
    
    },
    
	init: function() {
		this.getView().loadRecord( this.person );
		if(this.getView().instantCaseloadAssignment == true){
			this.hideForInstantCaseload();
		}
		return this.callParent(arguments);
    },
    
    hideForInstantCaseload: function(){
    	var me = this;
    	me.getAnticipatedStartTermField().hide();
        me.getAnticipatedStartYearField().hide();
        me.getAbilityToBenefitField().hide();
    }
});