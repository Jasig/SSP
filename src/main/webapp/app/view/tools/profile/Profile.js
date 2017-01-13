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
Ext.define("Ssp.view.tools.profile.Profile", {
    extend: "Ext.form.Panel",
    alias: "widget.profile",
    mixins: [ "Deft.mixin.Injectable", "Deft.mixin.Controllable" ],
    inject: {
        textStore: 'sspTextStore'
    },
    controller: "Ssp.controller.tool.profile.ProfileToolViewController",
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            layout: "fit",
            title: "Profile",
            padding: 0,
            border: 0,
            preventHeader: true,
            items: [ Ext.createWidget("tabpanel", {
                activeTab: 0,
                itemId: "profileTabs",
                items: [ {
                    title: me.textStore.getValueByCode('ssp.label.main.dashboard', 'Dashboard'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profiledashboard"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.details', 'Details'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profiledetails"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.financial', 'Financial'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profilefinancial"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.transcript', 'Transcript'),
                    autoScroll: true,
                    items: [ {
                        xtype: "transcript"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.placement', 'Placement'),
                    autoScroll: true,
                    items: [ {
                        xtype: "placement"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.contact', 'Contact'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profilecontact"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.coach', 'Coach'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profilecoach"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.watchers', 'Watchers'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profilewatchers"
                    } ]
                }, {
                    title: me.textStore.getValueByCode('ssp.label.main.schedule', 'Schedule'),
                    autoScroll: true,
                    items: [ {
                        xtype: "profileschedule"
                    } ]
                } ]
            }) ]
        });
        return me.callParent(arguments);
    }
});