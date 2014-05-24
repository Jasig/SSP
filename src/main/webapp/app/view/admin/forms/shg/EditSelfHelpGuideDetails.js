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
Ext.define('Ssp.view.admin.forms.shg.EditSelfHelpGuideDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editselfhelpguidedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.EditSelfHelpGuideViewController',
	title: 'Edit Self Help Guide',
    inject: {
    	selfHelpGuidesStore: 'selfHelpGuidesStore',
        authenticatedPerson: 'authenticatedPerson'
    },
    autoScroll: true,
    collapsible: true,
    scroll: 'vertical',
	initComponent: function() {
    	var me=this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Self Help Guide Name',
                    anchor: '100%',
                    name: 'name',
                    allowBlank:false
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },               
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Threshold',
                    anchor: '30%',
                    name: 'threshold'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Introduction',
                    anchor: '100%',
                    allowBlank:false,
                    name: 'introductoryText'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Summary',
                    anchor: '100%',
                    allowBlank:false,
                    name: 'summaryText'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Threshold Summary',
                    anchor: '100%',
                    name: 'summaryTextThreshold'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Early Alert Summary',
                    anchor: '100%',
                    allowBlank:false,
                    name: 'summaryTextEarlyAlert'
                },
                {
                    xtype: 'oscheckbox',
                    fieldLabel: 'Published',
                    anchor: '100%',
                    name: 'objectStatus'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Authentication Required',
                    anchor: '100%',
                    name: 'authenticationRequired'
                }                
            ],
            dockedItems: [{
	               xtype: 'toolbar',
	               items: [{
 		                   text: 'Save',
 		                   xtype: 'button',
 		                   action: 'save',
 		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_SAVE_BUTTON'),
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