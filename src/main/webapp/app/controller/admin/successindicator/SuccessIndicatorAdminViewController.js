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
Ext.define('Ssp.controller.admin.successindicator.SuccessIndicatorAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        store: 'successIndicatorsAllStore',
        unpagedStore: 'successIndicatorsAllUnpagedStore',
        adminSelectedIndex: 'adminSelectedIndex',
        formUtils: 'formRendererUtils',
        model: 'currentSuccessIndicator',
        storeUtils: 'storeUtils'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'editindicator'
    },
    control: {
        'addButton': {
            click: 'onAddClick'
        },

        view: {
            itemdblclick: 'onItemClicked'
        }
    },
    init: function(){
        var me = this;
        var params = {
            store: me.store,
            unpagedStore: me.unpagedStore,
            propertyName: "name",
            grid: me.getView(),
            model: me.model,
            selectedIndex: me.adminSelectedIndex
        };
        me.storeUtils.onStoreUpdate(params);
        return me.callParent(arguments);
    },

    onItemClicked: function(grid, record, item, e, eOpts){
        var me = this;

        if (record) {
            me.model.data = record.data;
            me.displayEditor();
        }
    },

    onAddClick: function(button){
        var me = this;
        var model = new Ssp.model.reference.SuccessIndicator();
        me.model.data = model.data;
        me.displayEditor();
    },

    displayEditor: function(){
        var me = this;
        var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
    },

    destroy: function(){
        var me = this;
        return me.callParent(arguments);
    }
});
