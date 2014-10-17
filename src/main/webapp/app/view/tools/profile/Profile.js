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
Ext.define('Ssp.view.tools.profile.Profile', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profile',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            title: 'Profile',
            padding: 0,
            border: 0,
            preventHeader: true,
            items: [Ext.createWidget('tabpanel', {
                width: '100%',
                height: '100%',
                activeTab: 0,
                itemId: 'profileTabs',
                items: [{
                    title: 'Dashboard',
                    autoScroll: true,
                    items: [{
                        xtype: 'profiledashboard'
                    }]
                }, {
                    title: 'Details',
                    autoScroll: true,
                    items: [{
                        xtype: 'profiledetails'
                    }]
                }, {
                    title: 'Financial',
                    autoScroll: true,
                    items: [{
                        xtype: 'profilefinancial'
                    }]
                }, {
                    title: 'Transcript',
                    autoScroll: true,
                    items: [{
                        xtype: 'transcript'
                    }]
                }, {
                    title: 'Placement',
                    autoScroll: true,
                    items: [{
                        xtype: 'placement'
                    }]
                }, {
                    title: 'Contact',
                    autoScroll: true,
                    items: [{
                        xtype: 'profilecontact'
                    }]
                }, {
                    title: 'Coach',
                    autoScroll: true,
                    items: [{
                        xtype: 'profilecoach'
                    }]
                }, {
                    title: 'Schedule',
                    autoScroll: true,
                    items: [{
                        xtype: 'profileschedule'
                    }]
                }
				]
            })]
        });
        
        return me.callParent(arguments);
    }
});
