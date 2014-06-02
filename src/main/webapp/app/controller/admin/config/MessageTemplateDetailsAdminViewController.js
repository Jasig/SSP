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
Ext.define('Ssp.controller.admin.config.MessageTemplateDetailsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        model: 'currentMessageTemplate',
        store: 'messageTemplatesStore',
		adminSelectedIndex: 'adminSelectedIndex'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'messagetemplatesadmin'
    },
    control: {
        'saveButton': {
            click: 'onSaveClick'
        },
        
        'cancelButton': {
            click: 'onCancelClick'
        },
        
        modifiedBy: '#modifiedBy',
        modifiedDate: '#modifiedDate'
    },
    
    init: function(){
        this.getView().getForm().loadRecord(this.model);
        
        this.getModifiedBy().setValue(this.model.getModifiedByPersonName());
        
        this.getModifiedDate().setValue(this.model.getFormattedModifiedDate());
        
        return this.callParent(arguments);
    },
    onSaveClick: function(button){
        var me = this;
        var record, id, jsonData, url, parentId;
        url = this.store.getProxy().url;
		me.getView().setLoading(true);
        this.getView().getForm().updateRecord();
        record = this.model;
        id = record.get('id');
        jsonData = record.data;
        if (id.length > 0) {
            Ext.Ajax.request({
                url: url + "/" + id,
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                jsonData: jsonData,
                success: function(response, view){
							me.adminSelectedIndex.set('value',1);
							me.displayMain();
                },
                failure: this.apiProperties.handleError
            }, this);
        }
    },
    
    
    onCancelClick: function(button){
    	me.adminSelectedIndex.set('value', 11);
        this.displayMain();
    },
    
    displayMain: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});
