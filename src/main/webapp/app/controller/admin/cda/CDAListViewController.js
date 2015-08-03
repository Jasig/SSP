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
Ext.define('Ssp.controller.admin.cda.CDAListViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        store: 'confidentialityDisclosureAgreementsStore',
        service: 'confidentialityDisclosureAgreementService'
    },

    control: {
	'isCDAEnabled': {
            listeners: {
            	checkChange: 'onEnabledCheckChange'
            }
        },
        'cdaListPanel': {
            itemdblclick: 'doubleClick'
        },
        'saveButton': {
            click: 'save'
        },
	'resetButton': {
	    click: 'resetEditForm'
	},

        saveSuccessMessage: '#saveSuccessMessage'
    },

    init: function() {
        this.store.load({
            scope: this,
            callback: this.loadConfidentialityDisclosureAgreementResult
        });

        return this.callParent(arguments);
    },

    getEditFormView: function() {
	return Ext.getCmp("cdaeditform");
    },

    loadConfidentialityDisclosureAgreementResult: function(cda) {   
        var me = this;     
        var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
        model.populateFromGenericObject(cda);
	
	var view = me.getEditFormView();
	view.loadRecord(model);
    },


    save: function(button) {
        var record, id, jsonData;
        var me = this;

	var view = me.getEditFormView();
        view.setLoading(false);

        if (view.getForm().isValid()) {           
            view.getForm().updateRecord();
            record = view.getRecord();
            id = record.get('id');
            jsonData = record.data;

            view.setLoading(true);

            me.service.save(jsonData, {
                success: me.saveSuccess,
                failure: me.saveFailure,
                scope: me
            });
        } else {
            Ext.Msg.alert('SSP Error', 'There are errors highlighted in red');
        }
    },

    saveSuccess: function(r, scope) {    	
        var me = scope;
        var view = me.getEditFormView();
        view.setLoading(false);
	

	//reload the form
	me.store.load();
        me.formUtils.displaySaveSuccessMessage(me.getSaveSuccessMessage());
    },

    saveFailure: function(response, scope) {
        var me = scope;
        var view = me.getEditFormView();
        view.setLoading(false);
        me.getView().setLoading(false);
    },

    //CDAListView double click event
    doubleClick: function(grid, record, item, index, e, eOpts) {
        this.loadConfidentialityDisclosureAgreementResult(record.data);
    },

    //Make a CDA live
    onEnabledCheckChange: function(column, rowIndex, checked, eOpts){       	
        var me = this;
        me.service.setEnabled(this.store.getAt(rowIndex).data.id, {
            success: me.saveSuccess,
            failure: me.saveFailure,
            scope: me
        });
    },

    resetEditForm: function() {
	var me = this;
        me.getEditFormView().getForm().reset();
    }
});