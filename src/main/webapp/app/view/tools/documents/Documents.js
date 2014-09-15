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
Ext.define('Ssp.view.tools.documents.Documents', {
	extend: 'Ext.grid.Panel',
    alias: 'widget.documents',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	controller: 'Ssp.controller.tool.documents.DocumentsViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentStudentDocument'
    },
    width: '100%',
    height: '100%',
    padding: 0,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit'
            },
            title: 'Documents Attached to the Student Record',
            autoScroll: true,
            padding: 0,
            columns: [{
                        xtype: 'gridcolumn',
                        dataIndex: 'modifiedDate',
                        text: 'Date',
                        flex: 0.10,
                        renderer: me.columnRendererUtils.renderModifiedByDate
                    }, {
                        xtype: 'gridcolumn',
                        dataIndex: 'name',
                        text: 'Name',
                        flex: 0.20
                    }, {
                        xtype: 'gridcolumn',
                        dataIndex: 'fileName',
                        text: 'File',
                        flex: 0.20
                    }, {
                        xtype: 'gridcolumn',
                        dataIndex: 'author',
                        text: 'Author',
                        flex: 0.10
                    }, 
					{
						xtype: 'gridcolumn',
						dataIndex: 'confidentialityLevelName',
	                	flex: 0.15,
						header: 'Confidentiality'
                    },
					{
                        xtype: 'gridcolumn',
                        dataIndex: 'comment',
                        text: 'Comments',
                        sortable: 'false',
                        flex: 0.25
                    }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    tooltip: 'Add a Document',
                    width: 60,
                    height: 30,
                    xtype: 'button',
                    itemId: 'addDocumentButton',
	                hidden: !me.authenticatedPerson.hasAccess('DOCUMENTS_ADD_BUTTON'),
                    text: 'Add'
                }, {
                    tooltip: 'Edit Document',
                    text: 'Edit',
                    width: 60,
                    height: 30,
                    xtype: 'button',
	                hidden: !me.authenticatedPerson.hasAccess('DOCUMENTS_EDIT_BUTTON'),
                    itemId: 'editDocumentButton'
                }, {
                    tooltip: 'Delete Document',
                    text: 'Delete',
                    width: 60,
                    height: 30,
                    xtype: 'button',
	                hidden: !me.authenticatedPerson.hasAccess('DOCUMENTS_DELETE_BUTTON'),
                    itemId: 'deleteDocumentButton'
                }, {
                    tooltip: 'Download Document',
                    text: 'Download',
                    width: 60,
                    height: 30,
	                hidden: !me.authenticatedPerson.hasAccess('DOCUMENTS_DOWNLOAD_BUTTON'),
                    xtype: 'button',
                    itemId: 'downloadDocumentButton'
                }]
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});



