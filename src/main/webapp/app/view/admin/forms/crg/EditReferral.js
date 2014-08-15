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
Ext.define('Ssp.view.admin.forms.crg.EditReferral',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editreferral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditReferralViewController',
	title: 'Edit Referral',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referral Name',
                    anchor: '100%',
                    name: 'name',
                    allowBlank: false,
                    required: true
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Public Description',
                    anchor: '100%',
                    name: 'publicDescription'
                }
                ,{
                    xtype: 'textareafield',
                    fieldLabel: 'Link (No HTML)',
                    inputAttrTpl: " data-qtip='Example: www.google.com  <br /> Remove any existing HTML markup e.g. &quot;&lt; a href=...&gt;&quot; ' ",
                    anchor: '100%',
                    maxLength: 256,
                    name: 'link'
                },  
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%',
                    name: 'showInStudentIntake'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Guide',
                    anchor: '100%',
                    name: 'showInSelfHelpGuide'
                },
				{
                    xtype: 'oscheckbox',
                    fieldLabel: 'Active',
                    name: 'objectStatus'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton',
		       		                formBind: true
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