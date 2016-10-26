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
Ext.define('Ssp.view.tools.documents.UploadDocuments', {
    extend: 'Ext.form.Panel',
    alias: 'widget.uploaddocuments',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	controller: 'Ssp.controller.tool.documents.UploadDocumentsViewController',
    inject: {
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
    	model: 'currentStudentDocument',
    	textStore: 'sspTextStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            border: 0,
            padding: 0,
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'top',
                labelWidth: 150
            },
            items: [{
                xtype: 'fieldcontainer',
                title: '',
                defaultType: 'textfield',
                border: 0,
                padding: 0,
                hidden: me.model.get('id'),
                layout: 'hbox',
                defaults: {
                    anchor: '95%'
                },
                items: [{
                    fieldLabel: me.textStore.getValueByCode('ssp.label.documents.file','File'),
                    xtype: 'fileuploadfield',
                    buttonText: me.textStore.getValueByCode('ssp.label.browse-button','Browse...'),
                    name: 'file',
                    allowBlank: me.model.get('id'),
                    flex: 1,
                    labelAlign: 'top',
                    padding: 5,
                    listeners: {
                        afterrender: function(c){
                             c.fileInputEl.dom.setAttribute('title', me.textStore.getValueByCode('ssp.tooltip.documents.file','Select a File'));
                        }
                    }
                }]
            }, {
                xtype: 'textfield',
                fieldLabel: me.textStore.getValueByCode('ssp.label.documents.file','File'),
                name: 'fileName',
                allowBlank: true,
                disabled:true,
                hidden: !me.model.get('id'),
                anchor: '95%',
                padding: 5
            }, 
            {
                xtype: 'textfield',
                fieldLabel: me.textStore.getValueByCode('ssp.label.documents.comment','Comment'),
                name: 'comment',
                allowBlank: true,
                anchor: '95%',
                padding: 5
            }, {
            
                fieldLabel: me.textStore.getValueByCode('ssp.label.documents.name','Name'),
                xtype: 'textfield',
                name: 'name',
                allowBlank: true,
                anchor: '95%',
                padding: 5
            }, {
                xtype: 'combobox',
                itemId: 'confidentialityLevel',
                name: 'confidentialityLevelId',
                fieldLabel: me.textStore.getValueByCode('ssp.label.documents.confidentiality-level','Confidentiality Level'),
                emptyText: me.textStore.getValueByCode('ssp.empty-text.documents.confidentiality-level','Select One'),
                store: me.confidentialityLevelsStore,
                valueField: 'id',
                displayField: 'name',
                mode: 'local',
                typeAhead: true,
                queryMode: 'local',
                allowBlank: false,
                forceSelection: true,
                anchor: '95%',
                padding: 5
            }],
            
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    itemId: 'saveButton',
                    text: me.textStore.getValueByCode('ssp.label.save-button','Save')
					}, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel')
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
