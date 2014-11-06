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
Ext.define('Ssp.controller.admin.apikey.oauth2.EditOAuth2ClientAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        permissionsStore: 'permissionsStore'
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
        },
        authorities: '#authorities',
        selectAllPermissions: {
            click: 'onSelectAllPermissions'
        },
        deselectAllPermissions: {
            click: 'onDeselectAllPermissions'
        },
        showPermissions: {
            click: 'onShowHidePermissions'
        }
    },
    allAuthorities: [],
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
        me.getView().setLoading(true);
        me.initPermissions(me.initFormSuccess,
                           me.initFormFailure,
                           me);
    },

    initPermissions: function(success, failure, scope) {
        var me = this;
        me.permissionsStore.load(function(records, operation, storeLoadSuccess) {
            if ( storeLoadSuccess ) {
                var checkboxItems = [];
                Ext.Array.each(records, function(record, idx, all){
                    checkboxItems.push({
                        boxLabel: record.get('name'),
                        name: 'authorities',
                        inputValue: record.get('authority')
                    });
                    me.allAuthorities.push(record.get('authority'));
                });
                me.getAuthorities().add(checkboxItems);
                success.apply(scope, []);
            } else {
                failure.apply(scope, []);
            }
        });
    },

    onSelectAllPermissions: function() {
        var me = this;
        me.getAuthorities().setValue({authorities: me.allAuthorities});
    },

    onDeselectAllPermissions: function() {
        var me = this;
        me.getAuthorities().setValue({authorities: null});
    },

    onShowHidePermissions: function() {
        var me = this;
        me.togglePermissionsVisibility(!(me.getAuthorities().isVisible()));
    },

    hidePermissions: function() {
        var me = this;
        if ( !(me.getAuthorities().isVisible()) ) {
            return;
        }
        me.togglePermissionsVisibility(false);
    },

    togglePermissionsVisibility: function(visible) {
        var me = this;
        me.getAuthorities().setVisible(visible);
        me.getSelectAllPermissions().setVisible(visible);
        me.getDeselectAllPermissions().setVisible(visible);
        me.getShowPermissions().toggleText();
    },

    initFormSuccess: function() {
        var me = this;
        var view = me.getView();
        view.getForm().loadRecord(view.client);
        // If permissions checkbox group is hidden when the form tries to
        // load the record nothing will end up checked. So wait to hide
        // the permissions until the form is initialized.
        me.hidePermissions();
        me.getView().setLoading(false);
    },

    initFormFailure: function() {
        var me = this;
        me.getView().setLoading(false);
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
        if ( !(me.getSecretDelete().getValue()) && me.getSecret().getValue().trim().length ) {
            if ( me.getSecret().getValue().trim() !== me.getSecretConfirm().getValue().trim() ) {
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

        // Not sure why by form.updateRecord() won't map this field, whereas
        // loadRecord() works just fine in the other direction
        var selectedAuthorities = me.getAuthorities().getValue().authorities;
        if ( typeof selectedAuthorities === 'string' ) {
            selectedAuthorities = [ selectedAuthorities ];
        }
        record.set('authorities', selectedAuthorities);
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
