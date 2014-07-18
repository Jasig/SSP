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
                text: '<u>Watch Student</u>',
                width: 110,
                height: 20,
        		hidden: !me.authenticatedPerson.hasAccess('WATCHLIST_WATCH_BUTTON'),
                xtype: 'button',
                cls: "makeTransparent",
                itemId: 'watchStudentButton'
            },                    
			{
                xtype: 'tbspacer',
                flex: .05
            },{
                tooltip: 'Email Student',
                text: '<u>Email Student</u>',
                width: 110,
                height: 20,
        		hidden: !me.authenticatedPerson.hasAccess('EMAIL_STUDENT_BUTTON'),
                xtype: 'button',
                cls: "makeTransparent",
                itemId: 'emailStudentButton'
            },                    
			{
                xtype: 'tbspacer',
                flex: .05
            },
			{
                
                text: '',
                width: 200,
                height: 20,
                xtype: 'button',
                itemId: 'emailCoachButton',
				cls: "makeTransparent"
            },
			{
				xtype: 'tbspacer',
                flex: .03
			},
			{
                tooltip: 'Coaching History',
                text: '<u>Coaching History</u>',
                width: 110,
                height: 20,
                xtype: 'button',
				cls: "makeTransparent",
                itemId: 'viewCoachingHistoryButton'
            }, 
			{
                xtype: 'tbspacer',
                flex: .03
            },{
                xtype: 'button',
                itemId: 'studentRecordEditButton',
                text: '',
                tooltip: 'Edit Student',
				cls: "editPerson20Icon",
				width: 23
            },
			{
                xtype: 'tbspacer',
                flex: .02
            }],
            items: [{
                xtype: 'toolsmenu',
                flex: .60
            }, {
                xtype: 'tools',
                flex: 4.40
            }]
        });
        return this.callParent(arguments);
    }
});
