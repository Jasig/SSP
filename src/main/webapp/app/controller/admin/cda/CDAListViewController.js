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
        service: 'confidentialityDisclosureAgreementService',
        authenticatedPerson: 'authenticatedPerson'
    },

    control: {
        'isCDAEnabled': {
            listeners: {
       //         checkChange: 'onEnabledCheckChange'
            }
        },
        'cdaListPanel': {
            itemdblclick: 'doubleClick'
        },
        'addButton': {
            click: 'addEditForm'
        },
    },

    init: function() {
        this.store.load({
            scope: this,
        });

        return this.callParent(arguments);
    },



    //CDAListView double click event
    doubleClick: function(grid, record, item, index, e, eOpts) {
        var me = this;
        
        var popWindow = Ext.create('Ssp.view.admin.forms.cda.CDAEdit', {
            floating: true,
            centered: true,
            modal: true,
            width: 500,
            height: 500,
            closable: true,
            defaultType: 'textfield',
            bodyPadding: 10,
            renderTo: Ext.getBody(),
        });

        popWindow.loadRecord(record);
        popWindow.show();
    },

    //Make a CDA live
    /*
     * i'm removing this, it's usable, but maybe too much functionality,
     * may as well set true from the detail pain of CDAEdit
    onEnabledCheckChange: function(column, rowIndex, checked, eOpts) {
        var me = this;
        me.service.setEnabled(this.store.getAt(rowIndex).data.id, {
            success: me.saveSuccess,
            failure: me.saveFailure,
            scope: me
        });
    },*/

    addEditForm: function() {
        var me = this;

        var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
        model.populateFromGenericObject(null);
        me.store.load();

        var popWindow = Ext.create('Ssp.view.admin.forms.cda.CDAEdit', {
            floating: true,
            centered: true,
            modal: true,
            width: 500,
            height: 500,
            closable: true,
            defaultType: 'textfield',
            bodyPadding: 10,
            renderTo: Ext.getBody()
        });

        popWindow.loadRecord(model);
        popWindow.show();
    }
});
