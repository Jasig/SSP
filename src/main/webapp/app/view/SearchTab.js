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
        appEventsController: 'appEventsController',
        textStore:'sspTextStore'
    },
  	initComponent: function() {
   		var me = this;
        tabPanelAccessor = function() {
            return me.items.items[0];
        };
   		Ext.apply(me, {
            submitEmptyText: false,
            title: me.textStore.getValueByCode('ssp.label.students', 'Students'),
            collapsible: true,
            collapseDirection: 'left',
            listeners: {
                afterrender: {
                    fn: function(c){
                        c.collapseTool.el.dom.firstChild.setAttribute('alt', me.textStore.getValueByCode('ssp.label.expand-collapse', 'Expand / Collapse'));
                    }
                },
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
                    id: 'searchTabPanel',
                    listeners: {
                    	tabchange: function(tabPanel, newTab, oldTab, eOpts)  {
                            // TODO really shouldn't have to do this. Ext.tab.Panel provides a getActiveTab()
                    		newTab.items.items[0].isActiveTab = true;
                    		oldTab.items.items[0].isActiveTab = false;
                        }
                    },
                     tbar: [{ 
                    	xtype: 'label', 
                    	text: me.textStore.getValueByCode('ssp.label.default-search-tab', 'Default Search Tab'),
                    	id : 'defaultTabLabel',
                    	listeners: { 
                    		element: 'el', 
               				click: function () { 
               					
               					//clicking sets a cookie and the defaultTabLabel Index to the current tab
               					//the next time they log in this will be the default tab
								var activeTab = Ext.getCmp('searchTabPanel').getActiveTab();
								var activeTabIndex = Ext.getCmp('searchTabPanel').items.findIndex('id', activeTab.id);		               					
		               			Ext.util.Cookies.set('defaultTabIndex', activeTabIndex); 
		               			Ext.getCmp('defaultTabLabel').setText(me.textStore.getValueByCode('ssp.label.default-search-tab', 'Default Search Tab'));
		               			defaultTabLabel.index = activeTabIndex;		               			
               				} 
                    	} 
                    }],
                    items: [{
                        title: me.textStore.getValueByCode('ssp.label.my-caseload', 'My Caseload'),
                        hidden: !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH'),
                        border: 0,
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: me.hasOnlySearch() ? 'search' : 'myCaseload', isActiveTab: me.defaultActiveTabIndex() === 0, tabPanelAccessor: tabPanelAccessor}]
                    },{
                        title: me.textStore.getValueByCode('ssp.label.my-watchlist', 'My Watch List'),
                        border: 0,
                        hidden: !me.authenticatedPerson.hasAccess('WATCHLIST_TOOL'),
                        layout: 'fit',
                        items: [{xtype: 'search', tabContext: me.hasOnlySearch() ? 'search' : 'watchList', isActiveTab: me.defaultActiveTabIndex() === 1, tabPanelAccessor: tabPanelAccessor}]
                    },{
                        title: me.textStore.getValueByCode('ssp.label.search', 'Search'),
                        hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
                        border: 0,
                        layout: 'fit',
                        // search needs to register to hear about inter-tab nav events so we pass this panel in as an
                        // attempt to avoid arbitrary componentmanager queries or fragile up().up().up() navigation
                        items: [{xtype: 'search',
                            style: {'overflow-y': 'auto'},
                            tabContext: 'search',
                            isActiveTab: me.defaultActiveTabIndex() === 2,
                            tabPanelAccessor: tabPanelAccessor}]
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
		
		//set the default Tab Index
		var tabIndex = Ext.util.Cookies.get('defaultTabIndex');
		if(tabIndex!=null){
			defaultTabLabel.index = parseInt(tabIndex);	
			return parseInt(tabIndex);
		}		
		
		if ( me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ) {
			defaultTabLabel.index = 0;
			return 0;
		} else if ( me.authenticatedPerson.hasAccess('WATCHLIST_TOOL') ) {
			defaultTabLabel.index = 1;
			return 1;
		} else {
			defaultTabLabel.index = 2;
		    return 2;
        }
	}
});