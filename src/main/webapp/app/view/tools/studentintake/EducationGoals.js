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
Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	alias: 'widget.studentintakeeducationgoals',
	id : "StudentIntakeEducationGoals",   
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.EducationGoalsViewController',
    inject:{
    	formUtils: 'formRendererUtils',
        weeklyCourseWorkHourRangesStore: 'weeklyCourseWorkHourRangesStore',
        registrationLoadRangesStore: 'registrationLoadRangesStore',
		termsStore: 'termsStore'
    },
	width: "100%",
    height: "100%", 
    initComponent: function() {
    	var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    defaultType: "radiogroup",
				    items: [{
				            xtype: "fieldset",
				            border: 0,
				            title: "",
				            id: 'StudentIntakeEducationGoalsFieldSet',
				            defaultType: "textfield",
				            defaults: {
				                anchor: "95%"
				            },
				       items: [{
				            xtype: "radiogroup",
				            id: 'StudentIntakeEducationGoalsRadioGroup',
				            fieldLabel: "Education/Career Goal",
				            allowBlank: true,
				            columns: 1
				        }]
				    },{
			            xtype: "fieldset",
			            border: 0,
			            title: '',
			            defaultType: "textfield",
			            defaults: {
			                anchor: "95%"
			            },
			       items: [{
				        fieldLabel: 'What is your planned major?',
				        name: 'plannedMajor'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your major?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutMajor", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutMajor", inputValue: "5"}
			        		]
			        },{
				        xtype: "radiogroup",
				        fieldLabel: 'Have you decided on a career/occupation?',
				        columns: 1,
				        itemId: 'careerDecided',
				        items: [
				            {boxLabel: "Yes", itemId: "careerDecidedCheckOn", name: "careerDecided", inputValue:"true"},
				            {boxLabel: "No", itemId: "careerDecidedCheckOff", name: "careerDecided", inputValue:"false"}]
					},{
				        fieldLabel: 'What is your planned occupation?',
				        name: 'plannedOccupation'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your occupation?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutOccupation", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutOccupation", inputValue: "5"}
			        		]
			        },{
				        xtype: 'radiogroup',
				        fieldLabel: 'Are you confident your abilities are compatible with the career field?',
				        columns: 1,
				        itemId: 'confidentInAbilities',
				        items: [
				            {boxLabel: "Yes", itemId: "confidentInAbilitiesCheckOn", name: "confidentInAbilities", inputValue:"true"},
				            {boxLabel: "No", itemId: "confidentInAbilitiesCheckOff", name: "confidentInAbilities", inputValue:"false"}]
					},{
				        xtype: "radiogroup",
				        fieldLabel: 'Do you need additional information about which academic programs may lead to a future career?',
				        columns: 1,
				        itemId: 'additionalAcademicProgramInformationNeeded',
				        items: [
				            {boxLabel: "Yes", itemId: "additionalAcademicProgramInformationNeededCheckOn", name: "additionalAcademicProgramInformationNeeded", inputValue:"true"},
				            {boxLabel: "No", itemId: "additionalAcademicProgramInformationNeededCheckOff", name: "additionalAcademicProgramInformationNeeded", inputValue:"false"}]
					},{
				        xtype: 'combobox',
				        name: 'registrationLoadName',
				        itemId: 'fieldRegistrationLoadCombo',
				        fieldLabel: 'Field Registration',
				        emptyText: 'Select One',
				        store: me.registrationLoadRangesStore,
				        valueField: 'name',
				        displayField: 'rangeLabel',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'courseWorkWeeklyHoursName',
				        itemId: 'courseWorkWeeklyHoursCombo',
				        fieldLabel: 'Hours per Week for Coursework',
				        emptyText: 'Select One',
				        store: me.weeklyCourseWorkHourRangesStore,
				        valueField: 'name',
				        displayField: 'rangeLabel',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'anticipatedGraduationDateTermCode',
				        itemId: 'anticipatedGraduationDateTermCodeCombo',
				        fieldLabel: 'Anticipated Graduation Date',
				        emptyText: 'Select One',
				        valueField: 'code',
				        displayField:'name',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					}]
				    
				    }]
				});
		
		return me.callParent(arguments);
	}	
});