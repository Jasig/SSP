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
Ext.define('Ssp.controller.admin.cda.CDAEditViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        store: 'confidentialityDisclosureAgreementsStore',
        service: 'confidentialityDisclosureAgreementService',
        authenticatedPerson: 'authenticatedPerson'
    },
    control: {
        'saveButton': {
            click: 'save'
        },
		'cancelButton': {
            click: 'closeWindow'
        },
        saveSuccessMessage: '#saveSuccessMessage'
    },

    init: function() {
        this.store.load({
            scope: this
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

    save: function(button, scope) {
        var record, jsonData;
        var me = this;

        var view = me.getEditFormView();

        if (view.getForm().isValid()) {
            view.getForm().updateRecord();
            record = view.getRecord();
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
    
    closeWindow: function() {
		//Get the active window
		var win = Ext.WindowManager.getActive();
		if (win) {
		   win.close();
		}
	},

    saveSuccess: function(r, scope) {
        var me = scope;
        var view = me.getEditFormView();
        view.setLoading(false);

        //reload the form
        me.store.load();
        me.formUtils.displaySaveSuccessMessage(me.getSaveSuccessMessage());
        me.closeWindow();
    },

    saveFailure: function(response, scope) {
        var me = scope;
        var view = me.getEditFormView();
        view.setLoading(false);
        me.getView().setLoading(false);
    }
});
