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
Ext.define('Ssp.controller.tool.profile.FinancialAidFileViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        financialAidFilesStore: 'financialAidFilesStore'
    },

	init: function() {
		var me=this;
		var args = arguments;
		var view = me.getView();
		var grid = view.getComponent('financialAidFilesGrid');
		var storeStatuses = grid.getStore();
		storeStatuses.removeAll();
		if(view.financialAidFilesStatuses){
			for(i = 0; i < view.financialAidFilesStatuses.length; i++){
				var fileStatus = Ext.create('Ssp.model.external.FinancialAidFileStatus');
				var status = view.financialAidFilesStatuses[i];
				var file = me.financialAidFilesStore.findRecord('code', status.financialFileCode);
				fileStatus.set("code", status.financialFileCode);
				fileStatus.set("status", status.fileStatus);
				if(file){
					fileStatus.set("description", file.get('description'));
					fileStatus.set("name", file.get('name'));
				}
				storeStatuses.add(fileStatus);
			}
		}	
		return this.callParent(arguments);
    },


	destroy: function() {
	        var me=this;
	        return me.callParent( arguments );
	}
	
});