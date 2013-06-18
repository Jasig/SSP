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
Ext.define('Ssp.view.admin.forms.config.EnrollmentStatusAddAdmin', {
    extend: 'Ext.form.Panel',
    alias: 'widget.enrollmentstatusaddadmin',
    title: 'Add to Enrollment Status',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    width: '100%',
    height: '100%',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            border: 0,
            padding: 0,
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 150
            },
            
            items: [{
                xtype: 'textfield',
                fieldLabel: 'Code',
                name: 'code',
                allowBlank: false,
				itemId: 'esCode'
            }, 
			{
                xtype: 'textfield',
                fieldLabel: 'Label',
                name: 'label',
                allowBlank: false,
				itemId: 'esLabel'
            },
			{
                xtype: 'textfield',
                fieldLabel: 'Default',
                name: 'default',
				itemId: 'esDefault'
            }],
            
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    itemId: 'addButton',
                    text: 'Save',
                    action: 'add'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
