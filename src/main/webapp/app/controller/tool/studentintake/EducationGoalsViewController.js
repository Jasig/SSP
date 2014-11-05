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
Ext.define('Ssp.controller.tool.studentintake.EducationGoalsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentStudentIntake',
		termsStore: 'termsStore'
    },
    control: {
    	careerDecidedCheckOn: '#careerDecidedCheckOn',
    	careerDecidedCheckOff: '#careerDecidedCheckOff',
    	confidentInAbilitiesCheckOn: '#confidentInAbilitiesCheckOn',
    	confidentInAbilitiesCheckOff: '#confidentInAbilitiesCheckOff',
    	additionalAcademicProgramInformationNeededCheckOn: '#additionalAcademicProgramInformationNeededCheckOn',
    	additionalAcademicProgramInformationNeededCheckOff: '#additionalAcademicProgramInformationNeededCheckOff',
		anticipatedGraduationDateTermCodeCombo: '#anticipatedGraduationDateTermCodeCombo'
    },
	init: function() {
		var me=this;

		var personEducationGoal = me.model.get('personEducationGoal');
		var careerDecided = me.model.get('personEducationGoal').get('careerDecided')
		var confidentInAbilities = me.model.get('personEducationGoal').get('confidentInAbilities');
		var additionalAcademicProgramInformationNeeded = me.model.get('personEducationGoal').get('additionalAcademicProgramInformationNeeded');
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			me.getCareerDecidedCheckOn().setValue( careerDecided );
			me.getCareerDecidedCheckOff().setValue( !careerDecided );
			
			me.getConfidentInAbilitiesCheckOn().setValue( confidentInAbilities );
			me.getConfidentInAbilitiesCheckOff().setValue( !confidentInAbilities );
			
			me.getAdditionalAcademicProgramInformationNeededCheckOn().setValue( additionalAcademicProgramInformationNeeded );
			me.getAdditionalAcademicProgramInformationNeededCheckOff().setValue( !additionalAcademicProgramInformationNeeded );
		}		

		if(me.termsStore.getTotalCount() == 0){
				me.termsStore.addListener("load", me.onTermsStoreLoad, me);
				me.termsStore.load();
		}else{
			me.initialiseGraduationDates();
		}
	
		
		return me.callParent(arguments);
    },
	
	onTermsStoreLoad:function(){
		var me = this;
		me.termsStore.removeListener( "onTermsStoreLoad", me.onTermsStoreLoad, me );
		me.initialiseGraduationDates();
	},
	
	initialiseGraduationDates: function(){
		var me = this;
		var futureTermsStore = me.termsStore.getCurrentAndFutureTermsStore(true);
		var currentCode = me.getAnticipatedGraduationDateTermCodeCombo().getValue();
		me.getAnticipatedGraduationDateTermCodeCombo().bindStore(futureTermsStore);
		me.getAnticipatedGraduationDateTermCodeCombo().setValue(currentCode);
	}
});