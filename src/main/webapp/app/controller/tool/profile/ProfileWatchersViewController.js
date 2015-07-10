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
Ext.define('Ssp.controller.tool.profile.ProfileWatchersViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        service: 'transcriptService',
        personLite: 'personLite',
        store: 'watchStudentListStore',
    },

    //return the base url with the student id populated
    getBaseUrl: function(id) {
        var me = this;
        var baseUrl = me.apiProperties.createUrl(me.apiProperties.getItemUrl('personWatchedBy'));
        baseUrl = baseUrl.replace('{id}', id);
        return baseUrl;
    },
    init: function() {
        var me = this;

        me.getView().setLoading(true);
        me.getView().setTitle('Current Watchers for ' + me.personLite.get('firstName'));
        var personId = me.personLite.get('id');
        me.store.removeAll();
        var p = me.getBaseUrl(personId);
        me.store.getProxy().url = p;
        me.store.load({
            scope: this,
            callback: function(records, operation, success) {
                me.callbackFunction(records, operation, success, me);
            }
        });

        return this.callParent(arguments);
    },

    callbackFunction: function(records, operation, success, scope) {
        var me = scope;
        if (success) {
            me.getView().setLoading(false);
        } else {
            Ext.Msg.alert("A system error has occurred while retrieving the list of watchers.");
            me.getView().setLoading(false);
        }
    }
});