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
Ext.define('Ssp.view.StudentRecord', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.studentrecord',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        authenticatedPerson: 'authenticatedPerson'
    },    
    controller: 'Ssp.controller.StudentRecordViewController',
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(this, {
            title: '',
            collapsible: true,
            collapseDirection: 'left',
            cls: 'studentpanel',
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            tools: [
        	{
                tooltip: 'Watch Student',
                text: 'Watch/Unwatch',
                width: 105,
                height: 20,
        		hidden: true,
                xtype: 'button',
                cls: "makeTransparent x-btn-link",
                itemId: 'watchStudentButton'
            },                    
			
			{
                
                text: '',
                width: 170,
                height: 20,
                xtype: 'button',
                itemId: 'emailCoachButton',
				cls: "makeTransparent x-btn-link"
            },
			
			{
                tooltip: 'Coaching History',
                text: 'Coaching History',
                width: 105,
                height: 20,
                xtype: 'button',
				cls: "makeTransparent x-btn-link",
				hidden: !me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
                itemId: 'viewCoachingHistoryButton'
            }, 
			{
                xtype: 'button',
                itemId: 'studentRecordEditButton',
                text: '',
                tooltip: 'Edit Student',
				cls: "editPerson20Icon",
				width: 23
            },
			{
                xtype: 'tbspacer',
                width: 1
            }],
            items: [{
                xtype: 'toolsmenu',
                flex: 0.60
            }, {
                xtype: 'tools',
                flex: 4.40
            }]
        });
        return this.callParent(arguments);
    }
});
