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
Ext.define('Ssp.view.tools.actionplan.EmailActionPlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.emailactionplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.EmailActionPlanController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        appEventsController: 'appEventsController',
        configStore: 'configStore',
		textStore: 'sspTextStore',
		person: 'currentPerson'
    },
    height: 400,
    width: 600,
	itemId:'emailAPWindow',
    resizable: true,
    modal: true,
    initComponent: function(){
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    title: 'Email Action Plan',
					itemId: 'emailAPForm',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Send',
									itemId: 'emailActionPlanButton'
                                },
                                {
                                    xtype: 'button',
                                    text: 'Cancel',
									 listeners: {
            		                    	click:function(){
            		                    		me = this;
            		                    		me.close();
            		                    	},
            		                    	scope: me
            		                    }
                                }
                            ]
                        }
                    ],
                    items: [
					{
						xtype: 'fieldcontainer',
						fieldLabel: '',
						height: 30,
						layout: {
							type: 'hbox'
						},
						items: [{
							xtype: 'checkboxfield',
							anchor: '100%',
							fieldLabel: '',
							boxLabel: 'Send To ' + me.textStore.getValueByCode('ssp.label.school-email'),
							disabled: !me.person.get('primaryEmailAddress'),
							itemId: 'emailAPToPrimary'
						},
						{
		                xtype: 'tbspacer',
		                width: 178
		            },				    
				    {
		            	xtype: 'displayfield',
				    	fieldLabel: me.person.get('primaryEmailAddress'),
                        labelSeparator : '',
                        fieldStyle: 'color:blue'
				    }]
					},
					{
						xtype: 'fieldcontainer',
						fieldLabel: '',
						height: 30,
						layout: {
							type: 'hbox'
						},
						items: [
                        {
                            xtype: 'checkboxfield',
                            anchor: '100%',
                            fieldLabel: '',
                            boxLabel: 'Send To ' + me.textStore.getValueByCode('ssp.label.alternate-email'),
							itemId: 'emailAPToSecondary',
							disabled: !me.person.get('secondaryEmailAddress')
                        },
						{
		                xtype: 'tbspacer',
		                width: 178
		            },				    
				    {
		            	xtype: 'displayfield',
				    	fieldLabel: me.person.get('secondaryEmailAddress'),
                        labelSeparator : '',
                        fieldStyle: 'color:blue'
				    }]
					},
					{
						xtype: 'fieldcontainer',
						fieldLabel: '',
						height: 30,
						layout: {
							type: 'hbox'
						},
						items: [
                        {
                            xtype: 'checkboxfield',
                            anchor: '100%',
                            fieldLabel: '',
                            boxLabel: 'Send To ' + me.configStore.getConfigByName('coachFieldLabel') ,
							disabled: !me.person.getCoachPrimaryEmailAddress(),
							itemId: 'emailAPToCoach'
                        }
						,
						{
		                xtype: 'tbspacer',
		                width: 178
		            },				    
				    {
		            	xtype: 'displayfield',
				    	fieldLabel: me.person.getCoachPrimaryEmailAddress(),
                        labelSeparator : '',
                        fieldStyle: 'color:blue'
				    }]
					},{
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: 'CC This email to additional recipients(comma separated)',
                            labelAlign: 'top',
							itemId: 'ccAPToAdditional',
							padding: '30 0 0 0'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});