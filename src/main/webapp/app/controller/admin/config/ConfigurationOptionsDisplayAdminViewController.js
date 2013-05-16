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
Ext.define('Ssp.controller.admin.config.ConfigurationOptionsDisplayAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'configurationOptionsStore',
    	formUtils: 'formRendererUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },       
	init: function() {
		var me=this;
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    }, 
    
	onSaveClick: function(button) {
		var me=this;
		var grid = button.up('grid');
		var store = grid.getStore();
		var records = store.getUpdatedRecords();
	    for(var i =0; i < records.length; i++){
	       var rec = records[i];
		   	var id = rec.get('id');
			var jsonData = rec.data;
			 Ext.Ajax.request({
				url: grid.getStore().getProxy().url+"/"+id,
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: jsonData,
				success: function(response, view) {
						var r = Ext.decode(response.responseText);
						rec.commit();
						grid.getStore().sync();
						rec.persisted = true;
					
				},
				failure: this.apiProperties.handleError
			}, this);
	       //}
	    }
	},
	
	onCancelClick: function(button){
		var me=this;
		me.store.load();
	}
});