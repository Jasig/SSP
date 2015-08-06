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
Ext.define('Ssp.view.admin.forms.cda.CDAEdit', {
    extend: 'Ext.form.Panel',
    alias: 'widget.cdaedit',
    id: 'cdaeditform',
    mixins: ['Deft.mixin.Injectable',
        'Deft.mixin.Controllable'
    ],
    controller: 'Ssp.controller.admin.cda.CDAEditViewController',
    hidden: true,
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',



        formUtils: 'formRendererUtils',
        store: 'confidentialityDisclosureAgreementsStore',
        service: 'confidentialityDisclosureAgreementService',


    },
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            //floating: true,
            title: 'Confidentiality Disclosure Agreement Admin',
            autoScroll: true,
            //width: '100%',
            //height: '100%',
            bodyPadding: 5,
            layout: 'anchor',
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 125
            },
            defaultType: 'displayfield',
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: 'Active',
                defaultType: 'checkboxfield',
                items: [{
                    //                    boxLabel  : '',
                    name: 'enabled',
                }]
            }, {
                fieldLabel: 'Name',
                xtype: 'textfield',
                disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
                name: 'name',
                allowBlank: false,
                anchor: '95%',
                maxLength: 80
            }, {
                fieldLabel: 'Description',
                xtype: 'textfield',
                disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
                name: 'description',
                anchor: '95%'
            }, {
                xtype: 'ssphtmleditor',
                fieldLabel: 'Disclosure Agreement',
                enableColors: false,
                allowBlank: false,
                disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
                enableAlignments: false,
                anchor: '95% 80%',
                name: 'text'
            }],

            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Save',
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON'),
                    action: 'save',
                    //formBind: true,
                    itemId: 'saveButton',
                    //handler: this.savett

                },'-',{
                    text: 'Cancel',
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON'),
                    action: 'Cancel',
                    //formBind: true,
                    itemId: 'cancelButton',
                    //handler: this.savett

                }, {
                    xtype: 'label',
                    html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
                    itemId: 'saveSuccessMessage',
                    style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
                    hidden: true
                }]
            }]
        });

        return me.callParent(arguments);
    },
    savett: function(button, scope) {
        alert('edit');

        var record, id, jsonData;
        var me = scope;

        var view = Ext.getCmp("cdaeditform"); //me.getEditFormView();
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

});
