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
Ext.define('Ssp.view.tools.sis.Registration', {
	extend: 'Ext.form.Panel',
	alias: 'widget.sisregistration',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Academic Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Registration Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Start Term',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'CUM GPA',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});