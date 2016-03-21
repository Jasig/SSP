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
Ext.define('Ssp.view.tools.map.TermNotes', {
    extend: 'Ext.window.Window',
    alias: 'widget.termnotes',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        textStore: 'sspTextStore'
    },
    height: 500,
    width: 500,
    resizable: true,
    initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: me.textStore.getValueByCode('ssp.label.map.term-notes.title','Term Notes'),
            items:[{
                xtype: 'form',
                flex: 1,
                border: 0,
                frame: false,
                layout: {
                    align: 'stretch',
                    type: 'vbox'
                },
                width: '100%',
                height: '100%',
                bodyPadding: 0,
                autoScroll: true,
                name: 'termnotesForm',
                fieldDefaults: {
                        msgTarget: 'side',
                        labelAlign: 'left',
                        labelWidth: 100
                    },
               
				    items: [
				   {
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.term-notes.contact-notes','Advisor/Coach Notes'),
				        allowBlank:true,
				        name: 'contactNotes',
				        xtype: 'textareafield',
				        autoscroll: true,
				        flex:1,
				        maxLength: 4000,
				        enforceMaxLength: true
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.term-notes.student-notes','Student Notes'),
				        name: 'studentNotes',
				        allowBlank:true,
				        xtype: 'textareafield',
				        flex:1,
				        autoscroll: true,
				        maxLength: 4000,
				        enforceMaxLength: true
				    },
				    {
                        name: 'isImportant',
                        inputValue: 'isImportant',
                        xtype:'checkbox',
                        padding: '0 0 0 105',
                        labelSeparator: '',
                        hideLabel: true,
                        boxLabel: me.textStore.getValueByCode('ssp.label.map.term-notes.is-important','Mark As Important'),
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.term-notes.is-important','Mark As Important')
                        }],
				    dockedItems: [{
		                xtype: 'toolbar',
		                dock: 'top',
		                items: [{
		                    xtype: 'button',
		                    name: 'saveButton',
		                    text: me.textStore.getValueByCode('ssp.label.save-button','Save')
		                    
		                }, '-', {
		                    xtype: 'button',
		                    name: 'cancelButton',
		                    text: me.textStore.getValueByCode('ssp.label.map.cancel-button','Cancel'),
							listeners:{
								click:function(){
									this.close();
								},
								scope: me
							}
		                }]
		            
		            }]
		            }]
				});
		
		return me.callParent(arguments);
	}
});