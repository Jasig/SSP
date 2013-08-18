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
Ext.define('Ssp.controller.admin.apikey.oauth2.EditOAuth2ClientAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'oauth2clientadmin'
    },
    control: {
        'saveButton': {
            click: 'onSaveClick'
        },
        'cancelButton': {
            click: 'onCancelClick'
        },
        secret: '#secret',
        secretConfirm: '#confirmSecret',
        secretDelete: {
            selector: '#deleteSecret',
            listeners: {
                change: 'onSecretDeleteChange'
            }
        }
    },
    init: function() {
        var me = this;
        if ( me.getView().client ) {
            me.initViewForEdit();
        } else {
            me.initViewForCreate();

        }
        return me.callParent(arguments);
    },

    initViewForEdit: function() {
        var me = this;
        me.initForm();
        // maybe set up additional rules here
    },

    initViewForCreate: function() {
        var me = this;
        me.getView().client = Ext.create('Ssp.model.OAuth2Client');
        me.initForm();
    },

    initForm: function() {
        var me = this;
        var view = me.getView();
        view.getForm().loadRecord(view.client);
    },

    onSecretDeleteChange: function() {
        var me = this;
        if ( me.getSecretDelete().getValue() ) {
            me.getSecret().setValue('');
            me.getSecretConfirm().setValue('');
        }
    },

    onSaveClick: function(button){
        var me = this;

        if ( me.validateForm() ) {
            me.persistForm();
        }
    },

    validateForm: function() {
        var me = this;
        var form = me.getView().getForm();
        if ( !(form.isValid()) ) {
            Ext.Msg.alert('SSP Error','Please correct the highlighted errors before resubmitting the form.');
            return false;
        }

        if ( !(me.validateSecret()) ) {
            Ext.Msg.alert('SSP Error','Please correct the highlighted errors before resubmitting the form.');
            me.getSecretConfirm().markInvalid("Secrets do not match");
            return false;
        }

        return true;
    },

    validateSecret: function() {
        // TODO would rather fire validation on every change in the secret field,
        // but this works for now

        var me = this;
        var form = me.getView().getForm();
        if ( !(me.getSecretDelete().getValue()) && me.getSecret().getValue().length ) {
            if ( me.getSecret().getValue() !== me.getSecretConfirm().getValue() ) {
                return false;
            }
        }
        return true;
    },

    persistForm: function() {
        var me = this;
        me.bindFormToModel();

        var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('oauth2Client') );
        var record = me.getView().client;
        var id = record.get('id');
        var jsonData = record.data;
        if (id)
        {
            // editing
            this.apiProperties.makeRequest({
                url: baseUrl+"/"+id,
                method: 'PUT',
                jsonData: jsonData,
                successFunc: me.displayMain,
                scope: me
            });
        }else{
            // adding
            this.apiProperties.makeRequest({
                url: baseUrl,
                method: 'POST',
                jsonData: jsonData,
                successFunc: me.displayMain,
                scope: me
            });
        }
    },

    bindFormToModel: function() {
        var me = this;
        var view = me.getView();
        var form = view.getForm();
        var record = view.client;
        var id = record.get('id');

        form.updateRecord();

        if ( me.getSecretDelete().getValue() ) {
            record.set('secret', null);
            record.set('secretChange', true);
        } else if ( me.getSecret().getValue().length ) {
            record.set('secretChange', true);
        }
    },

    onCancelClick: function(button){
        var me = this;
        me.displayMain();
    },

    displayMain: function(){
        var me = this;
        var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
    }
});