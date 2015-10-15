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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertDetails', {
    extend: 'Ext.form.Panel',
    alias: 'widget.earlyalertdetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController',
    inject: {
        model: 'currentEarlyAlert',
        selectedReasonsStore: 'earlyAlertDetailsReasonsStore',
        selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        treeStore: 'earlyAlertsTreeStore',
		currentEarlyAlertResponsesGridStore: 'currentEarlyAlertResponsesGridStore',
		textStore: 'sspTextStore'
    },
    width: '100%',
    height: '100%',

    // By default we assume the component causing this view to load has already
    // loaded the EA of interest into a shared resource (the injected
    // 'currentEarlyAlert')
    reloadEarlyAlert: false,

    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            autoScroll: true,
            title: me.textStore.getValueByCode('ssp.label.early-alert.details-title','Early Alert Details'),
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 0 0 0',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                    //labelAlign: 'right',
                    //labelWidth: 80
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: 0.40,
                    items: [{
                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.created-by','Created By'),
                        
                        name: 'createdByPersonName',
                        itemId: 'createdByField'
                    }, {
                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.created-date','Created Date'),
                        
                        name: 'createdDate',
                        itemId: 'createdDateField',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.course-name','Course Name'),
                        
                        name: 'courseName'
                    }, {
                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.campus','Campus'),
                        itemId: 'campusField',
                        
                        name: 'campus'
                    }, {
                        xtype: 'multiselect',
                        name: 'earlyAlertReasonIds',
                        itemId: 'earlyAlertReasonsList',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.reasons','Reasons'),
                        store: me.selectedReasonsStore,
                        displayField: 'name',
                        anchor: '95%'
					}, {
						fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.other-reason','Other Reason'),
						name: 'earlyAlertReasonOtherDescription',
						hidden: !me.model.get('earlyAlertReasonOtherDescription')
                    }, {
                        xtype: 'multiselect',
                        name: 'earlyAlertSuggestionIds',
                        itemId: 'earlyAlertSuggestionsList',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.suggestions','Suggestions'),
                        store: me.selectedSuggestionsStore,
                        displayField: 'name',
                        anchor: '95%'
					 }, {
						fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.other-suggestion','Other Suggestion'),
						name: 'earlyAlertSuggestionOtherDescription',
						hidden: !me.model.get('earlyAlertSuggestionOtherDescription')
                    }, {                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.comment','Comment'),
                        name: 'comment'
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        xtype: 'toolbar',
                        dock: 'top',
                        items: []
                    }, {
                        xtype: 'gridpanel',
                        title: me.textStore.getValueByCode('ssp.label.early-alert.responses-title','Responses'),
                        id: 'detailResponseGridPanel',
						store: me.currentEarlyAlertResponsesGridStore,
                        columns: [{
                            text: me.textStore.getValueByCode('ssp.label.early-alert.created-by','Created By'),
                            flex: 1,
                            dataIndex: 'createdBy',
                            renderer: me.columnRendererUtils.renderCreatedBy,
                            sortable: true
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.early-alert.created-date','Created Date'),
                            flex: 1,
                            dataIndex: 'createdDate',
                            renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A'),
                            sortable: true
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.early-alert.status','Status'),
                            flex: 0.5,
                            sortable: true,
                            dataIndex: 'closedDate',
                            renderer: me.columnRendererUtils.renderEarlyAlertStatus
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.early-alert.display-details','Details'),
                            flex: 2,
                            sortable: true,
                            dataIndex: 'gridDisplayDetails'
                        }]
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: 0.30,
                    items: [{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.status','Status'),
                        name: 'status',
                        itemId: 'statusField'
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.closed-by','Closed By'),
                        name: 'closedByPersonName',
                        itemId: 'closedByField'
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.closed-date','Closed Date'),
                        name: 'closedDate',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: me.textStore.getValueByCode('ssp.label.early-alert.email-cc','Email CC'),
                        
                        name: 'emailCC'
                    }]
                
                }]
            }],
            
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: me.textStore.getValueByCode('ssp.label.early-alert.return-to-list-button','Return to Early Alert List'),
                    xtype: 'button',
                    itemId: 'finishButton'
                },{
                    xtype: 'tbspacer'
                },{
                    text: me.textStore.getValueByCode('ssp.label.early-alert.respond-button','Respond  to selected Early Alert'),
                    xtype: 'button',
                    itemId: 'detailRespondButton',
                    hidden: !me.authenticatedPerson.hasAccess('RESPOND_EARLY_ALERT_BUTTON')
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
