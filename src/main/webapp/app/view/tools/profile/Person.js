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
Ext.define('Ssp.view.tools.profile.Person', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.profileperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        textStore: 'sspTextStore'
    },
    width: '100%',
    height: '100%',
    renderWithToolTip: function(value, metaData) {
        if ( metaData.el ) {
            metaData.el.set({'data-qtip': value});
        }
        return value;
    },
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldLabel: '',
            layout: 'hbox',
            margin: '0 0 0 0',
            defaultType: 'displayfield',
            fieldDefaults: {
                msgTarget: 'side'
            },
            
            items: [{
                xtype: 'image',
                fieldLabel: '',
                alt: me.textStore.getValueByCode('ssp.label.main.dashboard.student-type', 'Student Photo'),
                src: Ssp.util.Constants.DEFAULT_NO_STUDENT_PHOTO_URL,
                itemId: 'studentPhoto',
                width: 160
            }, {
                xtype: 'container',
                border: 0,
                padding: '0 0 0 5',
                title: '',
                defaultType: 'displayfield',
				flex: 1,
                defaults: {
                    anchor: '100%',
                    margin: 0,
                    fieldCls: 'x-form-display-field truncatable', // default + our extension. not sure how to reference default programmatically
                    renderer: me.renderWithToolTip
                },
                items: [{
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.email', ''),
                    hideLabel: true,
                    name: 'primaryEmailAddressField',
                    itemId: 'primaryEmailAddressField'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.dob', 'DOB'),
                    name: 'birthDate',
                    itemId: 'birthDate',
                    labelWidth: 30
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.student-type', 'Type'),
                    name: 'studentType',
                    itemId: 'studentType',
                    labelWidth: 32
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.program-status', 'Status'),
                    name: 'programStatus',
                    itemId: 'programStatus',
                    labelWidth: 45
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.program-status-reason', 'Status Reason'),
                    name: 'programStatusReason',
                    itemId: 'programStatusReason',
                    hidden: true
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.academic-program', 'Program'),
                    name: 'academicPrograms',
                    itemId: 'academicPrograms',
                    labelWidth: 45,
                    maxHeight: 32
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.home-campus', 'Home Campus'),
                    name: 'homeCampus',
                    itemId: 'homeCampus',
                    labelWidth: 85
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.map-plan', 'Plan'),
                    name: 'mapName',
                    itemId: 'mapName',
                    labelWidth: 32
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.main.dashboard.advisor', 'Plan By'),
                    name: 'advisor',
                    itemId: 'advisor',
                    labelWidth: 45
                }]
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
