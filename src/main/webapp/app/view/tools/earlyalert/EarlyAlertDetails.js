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
 /*
 * IRSC CUSTOMIZATIONS
 * 06/17/2014 - Jonathan Hart IRSC TAPS 20140039 - Add Faculty Interventions data stores to EA Details
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
		selectedInterventionsStore: 'earlyAlertDetailsInterventionsStore', //TAPS 20140039
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        treeStore: 'earlyAlertsTreeStore',
		currentEarlyAlertResponsesGridStore: 'currentEarlyAlertResponsesGridStore'
    },
    width: '100%',
    height: '100%',
    title: 'EarlyAlert Details',

    // By default we assume the component causing this view to load has already
    // loaded the EA of interest into a shared resource (the injected
    // 'currentEarlyAlert')
    reloadEarlyAlert: false,

    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            autoScroll: true,
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
                    flex: .40,
                    items: [{
                    
                        fieldLabel: 'Created By',
                        
                        name: 'createdByPersonName',
                        itemId: 'createdByField'
                    }, {
                    
                        fieldLabel: 'Created Date',
                        
                        name: 'createdDate',
                        itemId: 'createdDateField',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: 'Course Name',
                        
                        name: 'courseName'
                    }, {
                    
                        fieldLabel: 'Campus',
                        itemId: 'campusField',
                        
                        name: 'campus'
                    }, {
                        xtype: 'multiselect',
                        name: 'earlyAlertReasonIds',
                        itemId: 'earlyAlertReasonsList',
                        fieldLabel: 'Reasons',
                        store: me.selectedReasonsStore,
                        displayField: 'name',
                        anchor: '95%'
					}, {
						fieldLabel: 'Other Reason',
						name: 'earlyAlertReasonOtherDescription',
						hidden: !me.model.get('earlyAlertReasonOtherDescription')
                    }, {
                        xtype: 'multiselect',
                        name: 'earlyAlertSuggestionIds',
                        itemId: 'earlyAlertSuggestionsList',
                        fieldLabel: 'Suggestions',
                        store: me.selectedSuggestionsStore,
                        displayField: 'name',
                        anchor: '95%'
					 }, {
						fieldLabel: 'Other Suggestion',
						name: 'earlyAlertSuggestionOtherDescription',
						hidden: !me.model.get('earlyAlertSuggestionOtherDescription')
                    }
					//TAPS 20140039 BEGIN
					, {
                        xtype: 'multiselect',
                        name: 'earlyAlertInterventionIds',
                        itemId: 'earlyAlertInterventionsList',
                        fieldLabel: 'Faculty Interventions',
                        store: me.selectedInterventionsStore,
                        displayField: 'name',
                        anchor: '95%'
					 }
					//TAPS 20140039 END
					, {                    
                        fieldLabel: 'Comment',
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
                        title: 'Responses',
                        id: 'detailResponseGridPanel',
						store: me.currentEarlyAlertResponsesGridStore,
                        columns: [{
                            text: 'Created By',
                            flex: 1,
                            dataIndex: 'createdBy',
                            renderer: me.columnRendererUtils.renderCreatedBy,
                            sortable: true
                        }, {
                            text: 'Created Date',
                            flex: 1,
                            dataIndex: 'createdDate',
                            renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A'),
                            sortable: true
                        }, {
                            text: 'Status',
                            flex: .5,
                            sortable: true,
                            dataIndex: 'closedDate',
                            renderer: me.columnRendererUtils.renderEarlyAlertStatus
                        }, {
                            text: 'Details',
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
                    flex: .30,
                    items: [{
                        fieldLabel: 'Status',
                        name: 'status',
                        itemId: 'statusField'
                    }, {
                        fieldLabel: 'Closed By',
                        name: 'closedByPersonName',
                        itemId: 'closedByField'
                    }, {
                        fieldLabel: 'Closed Date',
                        name: 'closedDate',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                    }, {
                    
                        fieldLabel: 'Email CC',
                        
                        name: 'emailCC'
                    }]
                
                }]
            }],
            
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Return to EarlyAlert List',
                    xtype: 'button',
                    itemId: 'finishButton'
                },{
                    xtype: 'tbspacer'
                },{
                    text: 'Respond  to selected EarlyAlert',
                    xtype: 'button',
                    itemId: 'detailRespondButton',
                    hidden: !me.authenticatedPerson.hasAccess('RESPOND_EARLY_ALERT_BUTTON')
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
