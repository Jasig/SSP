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
Ext.define('Ssp.view.admin.forms.crg.EditChallenge',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editchallenge',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditChallengeViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	title: 'Edit Challenge',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Challenge Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Tags',
                    anchor: '100%',
                    name: 'tags'
                },{
                    xtype: 'combobox',
                    name: 'defaultConfidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    emptyText: 'Select One',
                    store: this.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'acronym',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true
            	},{
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Description',
                    anchor: '100%',
                    name: 'selfHelpGuideDescription'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Question',
                    anchor: '100%',
                    name: 'selfHelpGuideQuestion'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%',
                    name: 'showInStudentIntake'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Search',
                    anchor: '100%',
                    name: 'showInSelfHelpSearch'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});