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
Ext.define('Ssp.view.SearchTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.searchtab',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        authenticatedPerson: 'authenticatedPerson'
    },
  	initComponent: function() {
   		var me = this;
   		Ext.apply(me, {
            submitEmptyText: false,
            title: 'Students',
            collapsible: true,
            collapseDirection: 'left',
            border: 0,
            layout: 'fit',
            items: [
                Ext.createWidget('tabpanel', {
                    activeTab: 0,
                    items: [{
                        title: 'My Caseload',
                        hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
                        autoScroll: true,
                        border: 0,
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: 'myCaseload'}]
                    },{
                        title: 'Search',
                        autoScroll: true,
                        hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
                        border: 0,
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: 'search'}]
                    }]
                })
            ]

        });

        return me.callParent(arguments);
    }
});