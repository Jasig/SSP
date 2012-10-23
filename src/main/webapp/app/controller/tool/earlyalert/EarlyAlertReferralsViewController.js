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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	earlyAlertResponse: 'currentEarlyAlertResponse',
    	service: 'earlyAlertReferralService',
        store: 'earlyAlertReferralsBindStore'
    },
	init: function() {
		var me=this;
		
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return this.callParent(arguments);
    },

	getAllSuccess: function( r, scope ){
    	var me=scope;
    	var items;
    	var view = me.getView();
    	var selectedReferrals = me.earlyAlertResponse.get('earlyAlertReferralIds');
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);
    		
    		items = [{
	            xtype: 'itemselectorfield',
	            itemId: 'earlyAlertReferralsItemSelector',
	            name: 'earlyAlertReferrals',
	            anchor: '100%',
	            height: 250,
	            fieldLabel: 'Department Referrals',
	            store: me.store,
	            displayField: 'name',
	            valueField: 'id',
	            value: ((selectedReferrals.length>0) ? selectedReferrals : [] ),
	            allowBlank: true,
	            buttons: ["add", "remove"]
	        }];
    		
    		view.add(items);
    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }   
});