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
Ext.define('Ssp.view.tools.map.MAPView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.mapview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.MAPViewController',
    
    width: '100%',
    height: '100%',
  initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit'
            },
            autoScroll: true,
            padding: 0,
            preventHeader: true,
            border: 0,
            margin: '0 0 0 0 ',
            minWidth: '500',
            items: [{
    			xtype: 'semesterpanelcontainer',
    			flex:1
            },	{
    			xtype: 'faview',
				itemId: 'faPopUp',
    			flex:1,
				hidden:true,
				hideable:false
            }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
				height: '25',
                items: [{
                    tooltip: 'Create New Plan',
                    text: '<u>New Plan</u>',
                    //width: 30,
                    height: 22,
                   // cls: 'emailIcon',
                    xtype: 'button',
                    itemId: 'createNewPlanButton'
                }, {
                    tooltip: 'Load Saved Plan',
                    text: '<u>Load Saved Plan</u>',
                    height: 22,
                    xtype: 'button',
                    itemId: 'loadSavedPlanButton'
                },
				{
                    tooltip: 'Load Template',
                    text: '<u>Load Template</u>',
                    height: 22,
                    xtype: 'button',
                    itemId: 'loadTemplateButton',
					hidden: true,
					hideable:false
                },
                
				 
                 {
                    xtype: 'button',
                    text: 'Save',
                    itemId: 'addTool',
                    height: 22,
                    menu: {
                    items: [
                        
                        {
                            xtype: 'button',
                            text: 'Save Plan As',
                            itemId: 'savePlanButton'
                        },
                        {
                            xtype: 'button',
                            text: 'Save Template As' ,
                            itemId: 'saveTemplateButton',
							hidden:true,
							hideable:false
                        }
                    ]
                    }
                },
                {
                    xtype: 'tbspacer',
                    flex: .05
                }
                
				]
            },
			{
                xtype: 'plantool'
                
                
            },
			{
                xtype: 'moveplan'
                
                
            }
			]
            
        });
        
        return me.callParent(arguments);
    }
    
});
