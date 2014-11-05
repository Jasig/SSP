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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponse',{
    extend: 'Ext.form.Panel',
    alias : 'widget.earlyalertresponse',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController',
    inject: {
        earlyAlert: 'currentEarlyAlert',
        outcomesStore: 'earlyAlertOutcomesAllUnpagedStore',
        outreachesStore: 'earlyAlertOutreachesAllUnpagedStore'
    },
    initComponent: function() {
        var me=this;
        Ext.applyIf(me, {
            autoScroll: true,
            title: 'Early Alert Response',
            defaults:{
                labelWidth: 200
            },
            items: [
			
                {
                           xtype: 'toolbar',
                           dock: 'top',
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
                       },{
                    xtype: 'displayfield',
                    fieldLabel: 'Early Alert Course',
                    value: me.earlyAlert.get('courseName') +  ' ' + me.earlyAlert.get('courseTitle')
                 },{
                     xtype: 'multiselect',
                     name: 'earlyAlertOutreachIds',
                     itemId: 'outreachList',
                     fieldLabel: 'Outreach'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
                     store: me.outreachesStore,
                     displayField: 'name',
 					queryMode: 'local',
                     msgTarget: 'side',
                     valueField: 'id',
                     invalidCls: 'multiselect-invalid',
                     minSelections: 1,
                     allowBlank: false,
                     anchor: '95%'
                 },{
                    xtype: 'combobox',
                    itemId: 'outcomeCombo',
                    name: 'earlyAlertOutcomeId',
                    fieldLabel: 'Outcome',
                    emptyText: 'Select One',
                    store: me.outcomesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true,
                    anchor: '95%'
                },{
                    xtype: 'textfield',
                    itemId: 'otherOutcomeDescriptionText',
                    name: 'earlyAlertOutcomeOtherDescription',
                    fieldLabel: 'Other Outcome Description',
                    anchor: '95%'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment',
                    allowBlank: false
                },{
                    xtype: 'checkboxfield',
                    fieldLabel: 'Closed',
                    name: 'closed',
                    itemId: 'closedField'
                },{
                   xtype:'earlyalertreferrals',
                   flex: 1
                }
				],
            
            
			dockedItems: [{
                           xtype: 'toolbar',
                           items: [{
                                       text: 'Return to Early Alert List',
                                       xtype: 'button',
                                     
                                       itemId: 'responseGotoEAListButton'
                                   }, '-', {
                                       text: 'Return to Early Alert Details',
                                       xtype: 'button',
                                      
                                       itemId: 'responseGotoEADetailsButton'
                                   }]
                       }]		   
			
        });

        return me.callParent(arguments);
    }   
});