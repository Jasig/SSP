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
Ext.define('Ssp.view.tools.profile.Coach', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profilecoach',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileCoachViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 1,
            bodyPadding: 0,
            layout: 'anchor',
            
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 5 5 5',
                defaultType: 'displayfield',
				anchor: '100% , 60%',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [{
                	xtype: 'image',
                    fieldLabel: '',
                    src: Ssp.util.Constants.DEFAULT_NO_STUDENT_PHOTO_URL,
                    itemId: 'coachPhotoUrl',
                    width:150,
                    height:150
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    
                    padding: '0 5 15 5',
                    
                    flex: 0.30,
                    items: [{
                    
                        fieldLabel: 'Assigned Coach',
                        name: 'coachName',
                        itemId: 'coachName',
                        labelAlign: 'top',
                        labelPad: 0,
						flex: 1
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Phone',
                        name: 'coachWorkPhone',
                        itemId: 'coachWorkPhone',
                        labelWidth: 40,
						flex: 1
                    
                    }, {
                        fieldLabel: '',
                        name: 'coachPrimaryEmailAddress',
                        itemId: 'coachPrimaryEmailAddress',
						flex: 1
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: '',
                        name: 'coachDepartmentName',
                        itemId: 'coachDepartmentName'
                    
                    }, {
                        fieldLabel: '',
                        name: 'coachOfficeLocation',
                        itemId: 'coachOfficeLocation',
						flex: 1
                    
                    }
					/*, {
                        fieldLabel: 'Coach Type',
                        name: 'coachType',
                        itemId: 'coachType',
                    flex: 1
                    
                    }*/]
                
                }, {
                    xtype: 'fieldset',
                    border: 1,
                    title: 'Most recent activity of this coach with the record',
                    cls: 'makeTitleBold',
                    
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: 0.50,
                    padding: '0 5 5 5',
                    items: [{
                        fieldLabel: 'Date',
                        name: 'coachLastServiceDate',
                        itemId: 'coachLastServiceDate',
                        labelWidth: 35,
                        labelSeperator: false
                    
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Last Service Provided',
                        name: 'coachLastServiceProvided',
                        itemId: 'coachLastServiceProvided',
                        labelAlign: 'top',
                        labelPad: 0,
                        labelWidth: 150
                    
                    }]
                
                }]
            
            }, {
                xtype: 'recentsspactivity',
				anchor: '100% , 40%',
			    width: '100%',
				height: '100%',
				minHeight: 200                     
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
