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
Ext.define('Ssp.view.SearchTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.searchtab',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController'
    },
  	initComponent: function() {
   		var me = this;
        tabPanelAccessor = function() {
            return me.items.items[0];
        };
   		Ext.apply(me, {
            submitEmptyText: false,
            title: 'Students',
            collapsible: true,
            collapseDirection: 'left',
            listeners: {
                beforecollapse: function() {
                    me.appEventsController.loadMaskOn();
                },
                collapse: function() {
                    me.appEventsController.loadMaskOff();
                },
                beforeexpand: function() {
                    me.appEventsController.loadMaskOn();
                },
                expand: function() {
                    me.appEventsController.loadMaskOff();
                }
            },
            border: 0,
            layout: 'fit',
            items: [
                Ext.createWidget('tabpanel', {
                    activeTab: me.defaultActiveTabIndex(),
                    listeners: {
                    	tabchange: function(tabPanel, newTab, oldTab, eOpts)  {
                            // TODO really shouldn't have to do this. Ext.tab.Panel provides a getActiveTab()
                    		newTab.items.items[0].isActiveTab = true;
                    		oldTab.items.items[0].isActiveTab = false;
                        }
                    },
                    items: [{
                        title: 'My Caseload',
                        hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
                        autoScroll: true,
                        border: 0,
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: me.hasOnlySearch() ? 'search' : 'myCaseload', isActiveTab: me.defaultActiveTabIndex() === 0, tabPanelAccessor: tabPanelAccessor}]
                    },{
                        title: 'My Watch List',
                        autoScroll: true,
                        border: 0,
                        hidden: !me.authenticatedPerson.hasAccess('WATCHLIST_TOOL'),
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: me.hasOnlySearch() ? 'search' : 'watchList', isActiveTab: me.defaultActiveTabIndex() === 1, tabPanelAccessor: tabPanelAccessor}]
                    },{
                        title: 'Search',
                        autoScroll: true,
                        hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
                        border: 0,
                        layout: 'fit',
                        // search needs to register to hear about inter-tab nav events so we pass this panel in as an
                        // attempt to avoid arbitrary componentmanager queries or fragile up().up().up() navigation
                        items: [{xtype: 'search', tabContext: 'search', isActiveTab: me.defaultActiveTabIndex() === 2, tabPanelAccessor: tabPanelAccessor}]
                    }]
                })
            ]

        });

        return me.callParent(arguments);
    },
    hasOnlySearch : function() {
    	var me = this;
    	return me.authenticatedPerson.hasAccess('STUDENT_SEARCH') && !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') && !me.authenticatedPerson.hasAccess('WATCHLIST_TOOL');
    },
	defaultActiveTabIndex: function() {
		var me = this;
		if ( me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ) {
			return 0;
		} else if ( me.authenticatedPerson.hasAccess('WATCHLIST_TOOL') ) {
			return 1;
		} else {
		    return 2;
        }
	}
});
