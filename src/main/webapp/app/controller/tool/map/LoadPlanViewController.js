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
Ext.define('Ssp.controller.tool.map.LoadPlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	store: 'planStore',
        personLite: 'personLite',
    	apiProperties: 'apiProperties',
    	mapEventUtils: 'mapEventUtils',
    	mapPlanService:'mapPlanService'
    },
	control: {
    	'openButton': {
			click: 'onOpenClick'
		},	
		'cancelButton': {
			click: 'onCloseClick'
		},
		 'allPlansGridPanel':{
		 	itemdblclick: 'onItemDblClick'
		 }
	},

	init: function() {
		var me=this;
		me.getView().setLoading(true)
		var gridPanel = me.getView().query('gridpanel')[0];
		gridPanel.on('reconfigure',me.onAfterReconfigure);
		me.formUtils.reconfigureGridPanel(gridPanel, me.store, null,me.getView());
		return me.callParent(arguments);
    },
    resetForm: function() {
        var me = this;
    },
    onAfterReconfigure: function() {
        var me = this;
		me.getView().setLoading(false);
    },    
    onOpenClick: function(button) {
    	var me = this;
		var grid, record;

		
		record = me.getView().query('gridpanel')[0].getView().getSelectionModel().getSelection()[0];
        if (record) 
        {	
       	 	 me.mapEventUtils.loadPlan(record.get('id'));
     	     me.getView().hide();

        }else{
           Ext.Msg.alert('SSP Error', 'Please select an item to edit.');
        }    	
    },
	
	onItemDblClick: function(grid, record, item, index, e, eOpts) {
		var me = this;
		me.mapEventUtils.loadPlan(record.get('id'));
     	me.getView().hide();
		
	},
		    
	onCloseClick: function(){
		var me = this;
		if(me.getView().fromMapLoad){
			me.mapEventUtils.createNewMapPlan();
		}
		me.getView().hide();
	},
    destroy: function() {
    	var me = this;
		if(me.getView() && me.getView().fromMapLoad){ // view might have already been destroy()ed
			me.mapEventUtils.createNewMapPlan();
		}
        return this.callParent( arguments );
    }  
});
