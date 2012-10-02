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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlert', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.earlyalert',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentEarlyAlert',
        treeStore: 'earlyAlertsTreeStore'
    },
	width: '100%',
	height: '100%',	
	initComponent: function() {	
    	var me=this;
		Ext.apply(me, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',
		            cls: 'early-alert-tree-panel',
		            collapsible: false,
		            useArrows: true,
		            rootVisible: false,
		            store: me.treeStore,
		            multiSelect: false,
		            singleExpand: true,
    		        columns: [{
    		            xtype: 'treecolumn',
    		            text: 'Responses',
    		            flex: .5,
    		            sortable: false,
    		            dataIndex: 'text'
    		        },{
    		            text: 'Created By',
    		            flex: 1,
    		            dataIndex: 'createdBy',
    		            renderer : me.columnRendererUtils.renderCreatedBy,
    		            sortable: false
    		        },{
    		            text: 'Created Date',
    		            flex: 1,
    		            dataIndex: 'createdDate',
    		            renderer : Ext.util.Format.dateRenderer('Y-m-d g:i A'),
    		            sortable: false
    		        },{
    		            text: 'Status',
    		            flex: .5,
    		            sortable: false,
    		            dataIndex: 'closedDate',
    		            renderer: me.columnRendererUtils.renderEarlyAlertStatus
    		        },{
    		            text: 'Details',
    		            flex: 2,
    		            sortable: false,
    		            dataIndex: 'gridDisplayDetails'
    		        }],
    		        
    		        dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: '',
				            xtype: 'button',
				            hidden: !me.authenticatedPerson.hasAccess('RESPOND_EARLY_ALERT_BUTTON'),
			            	width: 28,
					        height: 28,
				            cls: 'earlyAlertResponseIcon',
				            itemId: 'respondButton'
				        },{
				            tooltip: 'Display detail for the selected item',
				            text: 'Details',
				            xtype: 'button',
				            hidden: !me.authenticatedPerson.hasAccess('EARLY_ALERT_DETAILS_BUTTON'),
				            itemId: 'displayDetailsButton'
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});