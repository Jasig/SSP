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
Ext.define('Ssp.view.admin.forms.config.BackgroundJobDetails', {
    extend: 'Ext.window.Window',
    alias: 'widget.backgroundjobdetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    title: 'Display BackgroundJob Details',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        backgroundjob: 'currentBackgroundJob',
        backgroundjobservice: 'backgroundJobService'
    },
    height: 315,
    width: 520,
    resizable: true,
    modal: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                minWidth: 80,
                labelWidth: 100,
                padding: '5 0 0 5'
            },
            items: [{
                xtype: 'displayfield',
                fieldLabel: 'Name',
                name: 'displayName',
                value: me.backgroundjob.get('displayName')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Description',
                name: 'description',
                value: me.backgroundjob.get('description')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Default Run-Time',
                name: 'defaultTime',
                value: me.backgroundjob.get('defaultTime')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Configuration Name',
                name: 'configName',
                value: me.backgroundjob.get('configName')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Running',
                name: 'running',
                hidden: true,
                value: me.backgroundjob.get('running')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Executable',
                name: 'executable',
                value: ((me.backgroundjob.get('executable')) ? 'Yes' : 'No')
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'bottom',
                layout: {
                  pack: 'center',
                  type: 'hbox'
                },
                items: [{
                    xtype: 'button',
                    text: 'Go Back',
                    listeners: {
                        click: function(){
                            me = this;
                            me.close();
                        },
                        scope: me
                    }
                }, {
                    xtype: 'button',
                    text: 'Execute Job',
                    hidden: !me.backgroundjob.get('executable'),
                    listeners: {
                        click: function(){
                            var me = this;

                            if (me.backgroundjob && me.backgroundjob.get('executable')) {
                                var title = 'Execute ' + me.backgroundjob.get('displayName') + ' Background Job';
                                var message = 'Warning: This job may take several hours to complete and slow the application while running. ' +
                                    'There is no notification when this job has completed. Avoid executing multiple times ' +
                                    'without making sure it has completed. \n' +
                                    'Do you wish to execute the ' + me.backgroundjob.get('displayName') + ' job?';

                                Ext.Msg.confirm({
                                    title: title,
                                    msg: message,
                                    buttons: Ext.Msg.YESNO,
                                    fn: function( btnId ) {
                                        if (btnId == "yes") {
                                            me.backgroundjobservice.runBackgroundJob(me.backgroundjob.get('name'), {
                                                success: function(r, scope) {
                                                    var me=scope;
                                                    var dialogOpts = {
                                                        buttons: Ext.Msg.OK,
                                                        icon: Ext.Msg.INFO,
                                                        fn: Ext.emptyFn,
                                                        title: '',
                                                        msg: 'Background Job Started!',
                                                        scope: me
                                                    };
                                                    Ext.Msg.show(dialogOpts);
                                                },
                                                failure: function(r, scope) {
                                                    var me=scope;
                                                }
                                            });
                                        }
                                    },
                                    scope: me
                                });
                            }
                            me.close();
                        },
                        scope: me
                    }
                }]
            }]
        });
        return this.callParent(arguments);
    }
});
