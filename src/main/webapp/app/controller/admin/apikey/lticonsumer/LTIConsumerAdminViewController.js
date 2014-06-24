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
Ext.define('Ssp.controller.admin.apikey.lticonsumer.LTIConsumerAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        store: 'ltiConsumersStore',
        formUtils: 'formRendererUtils'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'editlticonsumer'
    },
    control: {
        'addButton': {
            click: 'onAddClick'
        },
        view: {
            itemdblclick: 'onEditClick'
        }
    },
    init: function() {
        var me=this;

        me.formUtils.reconfigureGridPanel( me.getView(), me.store);
        me.store.load();

        return me.callParent(arguments);
    },

    onEditClick: function(view, record, item, index, event, eventListenerOpts) {
        this.displayEditor(record);
    },

    onAddClick: function(button){
        this.displayEditor(null);
    },

    displayEditor: function(client){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {client: client});
    }
});