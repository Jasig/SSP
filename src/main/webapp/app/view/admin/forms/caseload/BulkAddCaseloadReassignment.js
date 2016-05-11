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
Ext.define('Ssp.view.admin.forms.caseload.BulkAddCaseloadReassignment', {
	extend: 'Ext.form.Panel',
	alias : 'widget.bulkaddcaseloadreassignment',
	title: 'Bulk Add Caseload Assignment',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.caseload.BulkAddCaseloadReassignmentViewController',
	height: '100%',
	width: '100%',
    inject: {
        textStore: 'sspTextStore'
    },
	initComponent: function() {
		var me=this;
		var defaultWarningMessage = 'Warning:  If a large number students are being added or caseload reassigned, the performance of SSP '
		                    + 'could be impacted and this action should be done off hours.';
        Ext.applyIf(me, {
            autoScroll: true,
            border: 0,
            padding: 0,
            items: [{
                xtype: 'label',
                anchor: '100%',
                text: me.textStore.getValueByCode('ssp.label.bulk-add-reassign.warning-message', defaultWarningMessage)
            }, {
                xtype: 'tbspacer',
                height: 10
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height: 50,
                border: 0,
                padding: 0,
                defaults: {
                    anchor: '95%'
                },
                layout: {
                  type: 'hbox'
                },
                items: [{
                    xtype: 'fileuploadfield',
                    name: 'file',
                    itemId: 'file',
                    flex: 1,
                    fieldLabel: me.textStore.getValueByCode('ssp.label.bulk-add-reassign.file','File'),
                    buttonText: me.textStore.getValueByCode('ssp.label.browse-button','Browse...'),
                    labelWidth: 150,
                    labelAlign: 'top',
                    padding: 5
                }]
            }],
	            dockedItems: [{
	       		               xtype: 'toolbar',
	       		               items: [{
			       		                   text: 'Save',
			       		                   xtype: 'button',
			       		                   action: 'save',
			       		                   itemId: 'saveButton',
										   formBind: true
			       		               }]
	       		           }]
        });

        return me.callParent(arguments);
    }
});