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
Ext.define('Ssp.view.tools.profile.EvaluatedSuccessIndicatorGroups', {
    extend: 'Ext.Container',
    alias: 'widget.evaluatedsuccessindicatorgroups',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.EvaluatedSuccessIndicatorGroupsController',

    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            margin: '20 0 0 0',
            items: [{
                xtype: 'fieldset',
                title: 'Student Indicators',
                flex: 1,
                items: [{
                    xtype: 'evaluatedsuccessindicatorgroup',
                    itemId: 'studentSuccessIndicatorGroup',
                    maxHeight: 340,
                    overflowY: 'auto',
                    flex: 1
                }]
            }, {
                xtype: 'tbspacer',
                width: 10
            }, {
                xtype: 'fieldset',
                title: 'Intervention Indicators',
                flex: 1,
                items: [{
                    xtype: 'evaluatedsuccessindicatorgroup',
                    itemId: 'interventionSuccessIndicatorGroup',
                    maxHeight: 340,
                    overflowY: 'auto',
                    flex: 1
                }]
            }, {
                xtype: 'tbspacer',
                width: 10
            }, {
                xtype: 'fieldset',
                title: 'Risk Indicators',
                flex: 1,
                items: [{
                    xtype: 'evaluatedsuccessindicatorgroup',
                    itemId: 'riskSuccessIndicatorGroup',
                    maxHeight: 340,
                    overflowY: 'auto',
                    flex: 1
                }]
            }]
        });
        return me.callParent(arguments);
    }

});
