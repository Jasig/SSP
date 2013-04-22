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
Ext.define('Ssp.controller.tool.map.SavePlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan'
    },
    
	control: {
		view: {
			show: 'onShow'
		}
	},

	semesterStores: [],
	init: function() {
		var me=this;
	    me.resetForm();
	   // me.currentMapPlan.set('name','test_test_ui');
	    me.getView().query('form')[0].loadRecord( me.currentMapPlan );
		return me.callParent(arguments);
    },
    resetForm: function() {
        var me = this;
        //me.getView().getForm().reset();
    },
    onShow: function(){
    	var me=this;
    },
});
