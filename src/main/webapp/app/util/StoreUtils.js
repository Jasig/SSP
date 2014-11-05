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
Ext.define('Ssp.util.StoreUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
        errorsStore: 'errorsStore'
    },
	
	initComponent: function() {
		var me = this;
		// selectStoreToModel requires following params: unpagedStore, store, model, propertyName, grid
		me.appEventsController.assignEvent({eventName: 'selectStoreToModel', callBackFunc: this.onStoreUpdate, scope: this});
	},
	
	onStoreUpdate: function(ps){
		this.bindStore(ps);
		if(ps.model.get(ps.propertyName) && (ps.model.dirty || ps.selectedIndex.get('value') == 1)){
			var sortDirection = false;
			var sort = false;
			if(ps.store.sorters && ps.store.sorters.items.length > 0){
				sortDirection = ps.store.sorters.items[0].direction;
				sort = ps.store.sorters.items[0].property;
			}
			if(!ps.unpagedStore.getProxy().extraParams)
			  ps.unpagedStore.getProxy().extraParams = {};
			
			ps.unpagedExtraParams = JSON.parse(JSON.stringify(ps.unpagedStore.getProxy().extraParams));
			
			ps.unpagedStore.getProxy().setExtraParam("status", "ALL");
			ps.unpagedStore.getProxy().setExtraParam("limit", "-1");
			if(sortDirection){
				ps.unpagedStore.getProxy().setExtraParam("sortDirection", sortDirection);
				ps.unpagedStore.getProxy().setExtraParam("sort", sort);
			}
			ps.unpagedStore.load({callback:function(records, operation, success){
				this.onUnpagedStoreLoaded(records, operation, success, ps);
			},scope:this, single:true});
		}else{
			ps.store.load();
		}
	},
	
	getModelIndex: function(propertyName, model,store){
		// try id first as it has best chance of uniqueness
		if(model.get("id")){
		  	var index = store.findExact("id", model.get("id"));
			if(index >= 0)
				return index;
		}
		return store.findExact(propertyName, model.get(propertyName));
	},
	
	onUnpagedStoreLoaded: function(records, operation, success, params){
		var index = this.getModelIndex(params.propertyName, params.model, params.unpagedStore);
		var page = parseInt(index/params.store.pageSize);
		params.unpagedStore.getProxy().extraParams = params.unpagedExtraParams;
		params.store.currentPage = page + 1;
		
		params.store.load({callback:function(records, operation, success){
			this.onStoreLoaded(records, operation, success, params)}, scope:this, single:true});
	},
	
	onStoreLoaded:function(records, operation, success, params){
		var index = this.getModelIndex(params.propertyName, params.model, params.store);
		params.grid.getView().select(index);
		params.grid.getSelectionModel().select(index);
		
        var sn = params.grid.getView().getSelectedNodes()[0];
        
        Ext.get(sn).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR,
            Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
        params.selectedIndex.set('value', false);
	},
	
	bindStore:function(params){
		var pagers = params.grid.query('pagingtoolbar');
		params.grid.bindStore(params.store);
		if(pagers && pagers.length == 1){
			pagers[0].bindStore(params.store);
		}
	}
});