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
Ext.define('Ssp.controller.ToolsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        personLite: 'personLite',
        toolsStore: 'toolsStore'
    },
    control: {
        view: {
            itemclick: 'onItemClick',
            viewready: 'onViewReady'
        }
    
    },
    
    init: function(){
        return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
        var me = this;
        me.appEventsController.assignEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.assignEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        if (me.personLite.get('id') != "") {
            me.loadPerson();
        }
    },
    
    destroy: function(){
        var me = this;
        
        me.appEventsController.removeEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.removeEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        return me.callParent(arguments);
    },
    
    onLoadPerson: function(){
        this.loadPerson();
    },
    
    onTransitionStudent: function(){
        this.selectTool('journal');
        this.loadTool('journal');
    },
    
    loadPerson: function(){
        this.selectTool('profile');
        this.loadTool('profile');
    },
    
    selectTool: function(toolType){
        var tool = this.toolsStore.find('toolType', toolType)
        this.getView().getSelectionModel().select(tool);
    },
    
    onItemClick: function(grid, record, item, index){
        var me = this;
        
        //Listeners will need to return false to halt navigation from this point. 
        var skipCallBack = this.appEventsController.getApplication().fireEvent('toolsNav', record, me);  
        
        if (record.get('active') && me.personLite.get('id') != "" && skipCallBack) {
            this.loadTool(record.get('toolType'));
        }
        else
        if(record.get('toolType') === 'caseloadreassignment' && skipCallBack) {
            this.loadTool(record.get('toolType'));
        }
    },
    
    loadTool: function(toolType){
        var me = this;
        var comp;

		//TAKE OUT LEGACY AND DOCUMENTS GRANT
        if (me.authenticatedPerson.hasAccess(toolType.toUpperCase() + '_TOOL')) {
            comp = me.formUtils.loadDisplay('tools', toolType, true, {});
        }
        else {
            me.authenticatedPerson.showUnauthorizedAccessAlert();
        }
    }
});
