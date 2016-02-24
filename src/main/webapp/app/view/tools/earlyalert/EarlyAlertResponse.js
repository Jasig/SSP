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
        outreachesStore: 'earlyAlertOutreachesAllUnpagedStore',
        configStore: 'configurationOptionsUnpagedStore',
        textStore: 'sspTextStore'
    },
    initComponent: function() {
        var me=this;
        Ext.applyIf(me, {
            autoScroll: true,
            title: me.textStore.getValueByCode('ssp.label.early-alert.response-title','Early Alert Response'),
            defaults:{
                labelWidth: 200
            },
            items: [
			
                {
                           xtype: 'toolbar',
                           dock: 'top',
                           items: [{
                                       text: me.textStore.getValueByCode('ssp.label.save-button','Save'),
                                       xtype: 'button',
                                       action: 'save',
                                       itemId: 'saveButton'
                                   }, '-', {
                                       text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel'),
                                       xtype: 'button',
                                       action: 'cancel',
                                       itemId: 'cancelButton'
                                   }]
                       },{
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.course','Early Alert Course'),
                    value: me.earlyAlert.get('courseName') +  ' ' + me.earlyAlert.get('courseTitle')
                 },{
                     xtype: 'multiselect',
                     name: 'earlyAlertOutreachIds',
                     itemId: 'outreachList',
                     fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.outreach','Outreach')+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
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
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.outcome','Outcome'),
                    emptyText: me.textStore.getValueByCode('ssp.emtpy-text.early-alert.outcome','Select One'),
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
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.other-outcome-description','Other Outcome Description'),
                    anchor: '95%'
                },{
                    xtype: 'textareafield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.comment','Comment'),
                    anchor: '95%',
                    name: 'comment',
                    allowBlank: false
                },{
                    xtype: 'checkboxfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.closed','Closed'),
                    name: 'closed',
                    itemId: 'closedField'
                },{
                    xtype: 'checkboxfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.send-email-to-faculty-creator','Send Email to Faculty/Creator'),
                    name: 'sendCreatorEmail',
                    itemId: 'sendCreatorEmail',
                    hidden: !me.configStore.getConfigByName('ear_show_send_faculty_email')
                },{
                   xtype:'earlyalertreferrals',
                   flex: 1
                }
				],
            
            
			dockedItems: [{
                           xtype: 'toolbar',
                           items: [{
                                       text: me.textStore.getValueByCode('ssp.label.early-alert.return-to-list-button','Return to Early Alert List'),
                                       xtype: 'button',
                                     
                                       itemId: 'responseGotoEAListButton'
                                   }, '-', {
                                       text: me.textStore.getValueByCode('ssp.label.early-alert.return-to-details-button','Return to Early Alert Details'),
                                       xtype: 'button',
                                      
                                       itemId: 'responseGotoEADetailsButton'
                                   }]
                       }]		   
			
        });

        return me.callParent(arguments);
    }   
});