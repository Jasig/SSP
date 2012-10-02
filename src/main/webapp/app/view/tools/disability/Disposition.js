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
Ext.define('Ssp.view.tools.disability.Disposition', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitydisposition',
    height: '100%',
    width: '100%',
    bodyPadding: 0,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Release Signed',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Records Requested',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Referred for Screening LD/ADD',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Rights and Duties',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Eligibility Letter Sent',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Ineligibility Letter Sent',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'No Disability Documentation Received',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Inadequate Documentation',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Document states individual has no disability',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'HS reports no special ed placement/no report',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'On Medication',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'If yes, list medications',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Functional limitations, please explain',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }

});