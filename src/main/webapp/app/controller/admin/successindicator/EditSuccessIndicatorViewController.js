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
Ext.define('Ssp.controller.admin.successindicator.EditSuccessIndicatorViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        formUtils: 'formRendererUtils',
        model: 'currentSuccessIndicator',
        store: 'successIndicatorsAllStore',
        adminSelectedIndex: 'adminSelectedIndex'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'successindicatoradmin'
    },
    control: {
        'saveButton': {
            click: 'onSaveClick'
        },

        'cancelButton': {
            click: 'onCancelClick'
        },

        indicatorCode: '#indicatorCode',
        modelCode: '#modelCode',
        modelName: '#modelName',
        indicatorInstruction: '#indicatorInstruction',

        evaluationType: {
            selector: '#evaluationType',
            listeners: {
                change: 'onEvaluationTypeChange'
            }
        },
        evaluationTypeCard: '#evaluationTypeCard'
    },

    init: function(){
        var me = this;

        me.getView().getForm().loadRecord(me.model);
        me.disableFields(false);
        if (me.model.get('indicatorGroup') != 'RISK') {
            me.disableFields(true);
        }

        return this.callParent(arguments);
    },

    onSaveClick: function(button){
        var me = this;
        var record, id, jsonData, url;
        me.getView().setLoading( true );

        if(me.getView().getForm().isValid()) {

            me.getView().getForm().updateRecord();
            url = me.store.getProxy().url;
            record = me.model;
            id = record.get('id');
            jsonData = record.data;
            if (id) {
                // editing
                this.apiProperties.makeRequest({
                    url: url + "/" + id,
                    method: 'PUT',
                    jsonData: jsonData,
                    successFunc: me.onSaveSuccess,
                    failureFunc: me.onSaveFailure,
                    scope: me
                });
            } else {
                // adding
                this.apiProperties.makeRequest({
                    url: url,
                    method: 'POST',
                    jsonData: jsonData,
                    successFunc: me.onSaveSuccess,
                    failureFunc: me.onSaveFailure,
                    scope: me
                });
            }
        } else {
            Ext.Msg.alert('SSP Error', 'There are errors highlighted in red');
            me.getView().setLoading( false );
        }
    },

    onSaveSuccess: function () {
        var me = this;
        me.adminSelectedIndex.set('value', 1);
        me.displayMain();
    },

    onSaveFailure: function () {
        var me = this;
        Ext.Msg.alert('SSP Error', 'Changes could not be saved. Please try again or contact your system administrator.');
        me.getView().setLoading( false );
    },

    onCancelClick: function(button){
        var me = this;
        me.adminSelectedIndex.set('value', -1);
        me.displayMain();
    },

    displayMain: function(){
        var me = this;
        me.getView().setLoading( false );
        var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
    },

    onEvaluationTypeChange: function(combo, newVal, oldVal, eOpts) {
        var me = this;
        var card = me.getEvaluationTypeCard().getLayout();

        card.setActiveItem(newVal === 'SCALE' ? 0 : 1);
    },

    disableFields: function(val) {
        var me = this;
        var indicatorCodeField = me.getIndicatorCode();
        var modelCodeField = me.getModelCode();
        var modelNameField = me.getModelName();
        var indicatorInstructionField = me.getIndicatorInstruction();

        indicatorCodeField.setDisabled(val);
        modelCodeField.setDisabled(val);
        modelNameField.setDisabled(val);
        indicatorInstructionField.setReadOnly(val);
    }
});
