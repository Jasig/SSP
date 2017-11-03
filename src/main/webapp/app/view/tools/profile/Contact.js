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
Ext.define('Ssp.view.tools.profile.Contact', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profilecontact',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileContactViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        textStore:'sspTextStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                margin: '0 5 5 0',
                layout: 'hbox',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelAlign: 'right',
                    labelWidth: 100
                },
                items: [{
                    xtype: 'container',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: 0.60,
                    items: [{
                        xtype: 'fieldset',
                        border: 1,
                        cls: 'ssp-form',
                        title: me.textStore.getValueByCode('ssp.label.main.contact.mailing-address','Mailing Address'),
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },
                        flex: 0.40,
                        items: [{
                            fieldLabel: me.textStore.getValueByCode('ssp.label.non-local', 'Non Local'),
                            name: 'nonLocalAddress',
                            labelWidth: 80,
                            renderer: me.columnRendererUtils.renderFriendlyBoolean
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.address','Address'),
                            height: '60',
                            name: 'address',
                            labelWidth: 80,
                            itemId: 'address'
                        }]
                    }, {
                        xtype: 'container',
                        border: 0,
                        title: '',
                        flex: 0.80,
                        defaultType: 'displayfield',
                        padding: '0 0 40 0',
                        defaults: {
                            anchor: '100%'
                        },
                        items: [{
                            fieldLabel: me.textStore.getValueByCode('ssp.label.home-phone','Home Phone'),
                            name: 'homePhone'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.cell-phone','Cell Phone'),
                            name: 'cellPhone'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.work-phone','Work Phone'),
                            name: 'workPhone'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-phone','Alternate Phone'),
                            name: 'alternatePhone'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.school-email', 'School Email'),
                            name: 'primaryEmailAddress',
                            itemId: 'primaryEmailAddress',
                            listeners: {
                                render: function(c){
                                    c.getEl().on('click', function(){
                                        this.fireEvent('click', c);
                                    }, c);
                                }
                            }
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-email', 'Alternate Email'),
                            name: 'secondaryEmailAddress'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.dob', 'DOB'),
                            name: 'birthDate',
                            itemId: 'birthDate',
                            height: '60'
                        
                        }]
                    }]
                }, {
                    xtype: 'container',
                    border: 0,
                    
                    flex: 0.05
                
                }, {
                    xtype: 'container',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: 0.40,
                    items: [{
                        fieldLabel: '',
                        padding: '0 0 0 10',
                        height: '20'
                    
                    }, {
                        xtype: 'fieldset',
                        border: 1,
                        cls: 'ssp-form',
                        title: me.textStore.getValueByCode('ssp.label.main.contact.alternate-address','Alternate Address'),
                        
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },

                        items: [{
                            xtype: 'button',
                            itemId: 'editButton',
                            name: 'editButton',
                            text: me.textStore.getValueByCode('ssp.label.main.contact.alternate-address.edit','Edit'),
                            anchor: '33%',
                            buttonAlign: 'right',
                            action: 'edit'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.alt-in-use','In Use'),
                            name: 'alternateAddressInUse',
                            labelWidth: 80,
                            itemId: 'alternateAddressInUse'
                        }, {
                            fieldLabel: me.textStore.getValueByCode('ssp.label.address','Address'),
                            name: 'alternateAddress',
                            labelWidth: 80,
                            height: '60',
                            itemId: 'alternateAddress'
                        }]
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
