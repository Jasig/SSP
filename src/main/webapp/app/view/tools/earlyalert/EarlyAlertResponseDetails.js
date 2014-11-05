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
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponseDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponsedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController',
    inject: {
    	selectedOutreachesStore: 'earlyAlertResponseDetailsOutreachesStore',
    	selectedReferralsStore: 'earlyAlertResponseDetailsReferralsStore'
    },
    title: 'Early Alert Response Details',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	autoScroll: true,
            items: [{
	                xtype: 'displayfield',
	                fieldLabel: 'Created By',
	                anchor: '100%',
	                name: 'createdByPersonName',
	                itemId: 'createdByField'
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Created Date',
	                anchor: '100%',
	                name: 'createdDate',
	                itemId: 'createdDateField',
	                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Outcome',
	                anchor: '100%',
	                itemId: 'outcomeField',
	                name: 'outcome'
	            },{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            itemId: 'earlyAlertOutreachList',
		            fieldLabel: 'Outreach',
		            store: me.selectedOutreachesStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
		            xtype: 'multiselect',
		            name: 'earlyAlertReferralIds',
		            itemId: 'earlyAlertReferralsList',
		            fieldLabel: 'Referrals',
		            store: me.selectedReferralsStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
                    xtype: 'displayfield',
                    fieldLabel: 'Comment',
                    anchor: '100%',
                    name: 'comment'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Return to Early Alert Details',
		       		                   xtype: 'button',
		       		                   itemId: 'finishButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});