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
Ext.define('Ssp.controller.tool.profile.SapStatusController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        sapStatusesStore: 'sapStatusesAllUnpagedStore'
    },
	control: {
        codeField: '#code',
        nameField: '#name',
		descriptionField: '#description'
	},

	init: function() {
		var me=this;
		var args = arguments;
		
		if(me.sapStatusesStore.getTotalCount() <= 0){
			me.sapStatusesStore.addListener("load", me.sapStatusesStoreLoaded, me, {single:true});
			me.sapStatusesStore.load();
		}else{
			me.sapStatusesStoreLoaded();
		}
		return this.callParent(arguments);
    },

	sapStatusesStoreLoaded: function(){
		var me=this;
		var sapStatus = me.sapStatusesStore.findRecord('code',me.getView().code, 0, false, false, true);
		if(sapStatus){
			me.getCodeField().setValue(sapStatus.get('code'));
			me.getDescriptionField().setValue(sapStatus.get('description'));
			me.getNameField().setValue(sapStatus.get('name'));
		}else{
			me.sapStatusesStore.load();
		}
	},


	destroy: function() {
	        var me=this;
	        return me.callParent( arguments );
	}
	
});