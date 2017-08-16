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
Ext.define('Ssp.view.tools.map.EmailPlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.emailplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.EmailPlanController',
    inject: {
        appEventsController: 'appEventsController',
        textStore: 'sspTextStore'
    },
    height: 425,
    width: 700,
    resizable: true,
    modal: true,
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: me.textStore.getValueByCode('ssp.label.map.email-plan.title','Email Plan'),
            items: [{
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
                bodyPadding: 10,
				bodyStyle : 'background:none',
                autoScroll: true,
                itemId: 'faEmailPlan',
                items: [{
                	xtype: 'container',
                    defaultType: 'textfield',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    //flex: 1,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        margin: '0 0 0 0',
                        padding: '0 0 0 0',
                        width: '100%',
                        layout: {
                            align: 'stretch',
                            type: 'hbox'
                        },
                        items: [{
                            xtype: 'textfield',
                            name: 'emailTo',
                            vtype: 'multiemail',
                            fieldLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.to','To'),
                            labelWidth: 30,
                            width:400,
                            allowBlank:false,
                            itemId: 'emailTo'
                        }]
                    }, {
                        xtype: 'container',
                        title: '',
                        margin: '0 0 0 0',
                        padding: '5 0 5 0',
                        layout: 'hbox',
                        align: 'stretch' ,
                        items: [{
                            xtype: 'textfield',
                            name: 'emailCC',
                            fieldLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.cc','cc'),
                            vtype: 'multiemail',
                            labelWidth: 30,
                            width:400,
                            itemId: 'emailCC'
                        }]
                    }, {
                        xtype: 'ssphtmleditor',
                        name: 'notes',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.notes','Notes'),
                        labelWidth: 48,
                        inputWidth:400,
                        rows: 15,
                        height: 200
                    }]
                }, {
                    xtype: 'container',
                    border: 0,
                    title: '',
                    defaultType: 'radio',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    layout: 'vbox',
                    align: 'stretch',
                    items: [{
                            checked: false,
                            boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.with-options','Email MAP with Options'),
                            name: 'outputFormat',
                            inputValue: 'fullFormat',
                            itemId: 'fullFormat'
                    }, {
                            checked: true,
                            boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.matrix-format','Email MAP in Matrix Format'),
                            name: 'outputFormat',
                            inputValue: 'matrixFormat',
                            itemId: 'matrixFormat'
                    }, {
                            checked: false,
                            boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.matrix-format','Email MAP in Short Matrix Format'),
                            name: 'outputFormat',
                            inputValue: 'shortMatrixFormat',
                            itemId: 'shortMatrixFormat'
                    }]
                }, {
                    xtype: 'container',
                    border: 0,
                    title: '',
                    defaultType: 'checkbox',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    layout: 'vbox',
                    align: 'stretch' ,
                    itemId: 'optionsEmailView',
                    items: [{
                        checked: true,
                        boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.with-course-description','With Course Description'),
                        name: 'includeCourseDescription'
                    }, {
                        checked: true,
                        boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.with-header-footer','With Header/Footer'),
                        name: 'includeHeaderFooter'
                    }, {
                        checked: true,
                        name: 'includeTotalTimeExpected',
                        labelSeparator: '',
                        boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.total-time','Total Time Expected Outside Class')
                    }, {
                        checked: true,
                         name: 'includeFinancialAidInformation',
                         labelSeparator: '',
                         boxLabel: me.textStore.getValueByCode('ssp.label.map.email-plan.display-financial-aid','Display FinAid Information')
                    }]
                }],
                dockedItems: [{
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [{
                        xtype: 'button',
                        itemId: 'sendEmailButton',
                        text: me.textStore.getValueByCode('ssp.label.send-email-button','Send Email'),
                        listeners:{
                            click: function(){
                                me = this;
                                var form = me.query('form')[0];
                                var record = new Ssp.model.tool.map.PlanOutputData();
                                var emailForm = form.getForm();
                                if ( emailForm.isValid()) {
                                    form.getForm().updateRecord(record);
                                    me.appEventsController.getApplication().fireEvent(me.emailEvent, record);
                                    me.close();
                                } else {
                                    Ext.Msg.alert(
                                        me.textStore.getValueByCode('ssp.message.email-plan.error-title','SSP Error'),
                                        me.textStore.getValueByCode('ssp.message.email-plan.form-errors', 'Please correct the email address in this form.')
                                        );
                                }
                            },
                            scope: me
                        }
                    }, '-', {
                        xtype: 'button',
                        itemId: 'cancelButton',
                        text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel'),
                        listeners: {
                            click:function(){
                                me = this;
                                me.close();
                            },
                            scope: me
                        }
                    }]
                }]
            }],
            listeners: {
                 afterrender: function(c){
                     c.el.dom.setAttribute('role', 'dialog');
                 }
            }
        });
        return me.callParent(arguments);
    }
});
