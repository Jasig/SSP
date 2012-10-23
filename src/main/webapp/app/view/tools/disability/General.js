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
Ext.define('Ssp.view.tools.disability.General', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitygeneral',
    height: '100%',
    width: '100%',
    bodyPadding: 0,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: 'ODS Status',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'If temporary eligibility, please explain',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    value: 'Display Field',
                    fieldLabel: 'ODS Registration Date',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'ODS Counselor',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referred to ODS By',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});