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
languages = [
    ['eng', 'English', 'ascii'],
    ['spa', 'Spanish/Latin American'],
    ['fre', 'France (France)'],
    ['gla', 'Gaelic/Irish'],
    ['NOTHING', 'FOR DEV']
];

/* Language chooser combobox  */
var store1 = Ext.create('Ext.data.ArrayStore', {
    fields: ['code', 'language'],
    data: languages // from languages.js
});


Ext.define('Ssp.view.Main', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
    mixins: ['Deft.mixin.Injectable',
        'Deft.mixin.Controllable'
    ],
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        textStore: 'sspTextStore'
    },
    controller: 'Ssp.controller.MainViewController',
    initComponent: function() {
        var me = this;

        me.textStore.load();


        Ext.apply(me, {
            layout: {
                type: 'hbox',
                align: 'stretch'
            },

            dockedItems: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: me.textStore.getValueByCode('ssp.label.students'),
                    hidden: !me.authenticatedPerson.hasAccess('STUDENTS_NAVIGATION_BUTTON'),
                    itemId: 'studentViewNav',
                    action: 'displayStudentRecord'
                }, {
                    xtype: 'button',
                    text: me.textStore.getValueByCode('ssp.label.admin'),
                    hidden: !me.authenticatedPerson.hasAccess('ADMIN_NAVIGATION_BUTTON'),
                    itemId: 'adminViewNav',
                    action: 'displayAdmin'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    id: 'report',
                    xtype: 'sspreport'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'combo',
                    displayField: 'language',
                    valueField: 'code',                   
                    store: Ext.create('Ext.data.ArrayStore', {
                        fields: ['code', 'language'],
                        data: languages
                    }),
                    listeners: {

                        select: function(cb, record) {
                            Ext.util.Cookies.set('defaultLangCode', cb.getValue());
                            //me.textStore.load();
                            window.location.reload();
                        },
						render: function() {			        		        
							var val = Ext.util.Cookies.get('defaultLangCode');
							this.setValue(val);
						}
                    }
                }]
            }
        });



        return me.callParent(arguments);
    }
});