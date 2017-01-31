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
Ext.define(
    'Ssp.view.admin.forms.cda.CDAList', {
        extend: 'Ext.grid.Panel',
        id: 'cdaListPanel',
        name: 'cdaListPanel',
        itemid: 'cdaListPanel',
        alias: 'widget.cdalist',
        title: 'Confidentiality Agreement Forms (Active is currently printed for Students)',
        mixins: ['Deft.mixin.Injectable',
            'Deft.mixin.Controllable'
        ],
        inject: {
            apiProperties: 'apiProperties',
            authenticatedPerson: 'authenticatedPerson',
            columnRendererUtils: 'columnRendererUtils',
            store: 'confidentialityDisclosureAgreementsStore'
        },
        height: '100%',
        width: '100%',
        itemId: 'cdaListPanel',
        initComponent: function() {
            var me = this;
            Ext.apply(
                me, {
                    autoScroll: true,
                    selType: 'rowmodel',
                    enableDragDrop: false,
                    cls: 'configgrid',
                    columns: [{
                            name: 'isCDAEnabled',
                            id: 'isCDAEnabled',
                            xtype: 'checkcolumn',
                            text: 'Active',
                            width: 55,
                            dataIndex: 'enabled',
                            inputValue: 'ACTIVE',
                            uncheckedValue: 'INACTIVE',
                            sortable: true,
                            renderer: function(value) {
                                if (value) {
                                    return 'Yes';
                                } else {
                                    return 'No';
                                }
                            }
                        }, {
                            text: 'Name',
                            width: 200,
                            dataIndex: 'name',
                            sortable: true
                        }, {
                            text: 'Description',
                            width: 200,
                            dataIndex: 'description',
                            sortable: true
                        }
                    ],
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            text: 'Add',
                            iconCls: 'icon-add',
                            xtype: 'button',
                            hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON'),
                            action: 'add',
                            itemId: 'addButton'
                        }, '-' ]
                    }, {
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            xtype: 'label',
                            text: 'Double-click to edit an item.'
                        }]
                    }]
                });

            return me.callParent(arguments);
        }
    });
