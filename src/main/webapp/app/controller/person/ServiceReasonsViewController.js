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
Ext.define('Ssp.controller.person.ServiceReasonsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
		apiProperties: 'apiProperties',
    	formRendererUtils: 'formRendererUtils',
    	columnRendererUtils: 'columnRendererUtils',
        person: 'currentPerson',
        store: 'serviceReasonsAllUnpagedStore',
		itemSelectorInitializer: 'itemSelectorInitializer',
		service: 'serviceReasonsService'
    },
	
	init: function() {
		var me=this;
				
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },
    
	getAllSuccess: function( r, scope ){
		var me=scope;
    	var selectedServiceReasons = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('serviceReasons') );

        me.store.loadData(r.rows);
        me.store.clearFilter(true);
        me.formRendererUtils.applyAssociativeStoreFilter(me.store, selectedServiceReasons);
		
		

        me.itemSelectorInitializer.defineAndAddSelectorField(me.getView(), selectedServiceReasons, {
            itemId: 'serviceReasonsItemSelector',
            name: 'serviceReasons',
            fieldLabel: '<div style="float:right; width: 48%; ">Assigned to the Student</div><div style="width: 50%;">Available Service Reasons</div>',
			labelAlign: 'top',
			labelSeparator: ' ',
            store: me.store
        });
		
		

	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }
	
    
	
});