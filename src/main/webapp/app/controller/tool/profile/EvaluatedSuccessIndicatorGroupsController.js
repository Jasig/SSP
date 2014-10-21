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
Ext.define('Ssp.controller.tool.profile.EvaluatedSuccessIndicatorGroupsController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        personLite: 'personLite',
        apiProperties: 'apiProperties'
    },
    control: {
        studentSuccessIndicatorGroup: '#studentSuccessIndicatorGroup',
        interventionSuccessIndicatorGroup: '#interventionSuccessIndicatorGroup',
        riskSuccessIndicatorGroup: '#riskSuccessIndicatorGroup'
    },

    init: function(){
        var me = this;
        var rtn = me.callParent(arguments);
        me.load();
        return rtn;
    },

    load: function() {
        var me = this;
        me.getView().setLoading(true);
        var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEvaluatedSuccessIndicator') );
        url = url.replace( '{personId}', me.personLite.get("id") );
        Ext.create('Ext.data.Store', {
            model: "Ssp.model.EvaluatedSuccessIndicator",
            proxy: {
                type: 'ajax',
                url: url,
                reader: {
                    type: 'json'
                }
            }
        }).load({
           scope: me,
           callback: me.onLoadResponse
        });
    },

    onLoadResponse: function(records, operation, success) {
        var me = this;
        if ( success ) {
            me.renderViewSpecs(me.sortViewSpecs(me.buildViewSpecs(records)));
        } else {
            me.onLoadFailure();
        }
    },

    onLoadFailure: function() {
        var me = this;
        me.getView().setLoading(false);
    },

    buildViewSpec: function(serverModel) {
        return {
            xtype: 'evaluatedsuccessindicator',
            data: {
                indicatorName: serverModel.get('indicatorName'),
                indicatorDescription: serverModel.get('indicatorDescription'),
                indicatorValue: serverModel.get('displayValue'),
                indicatorStatus: serverModel.get('evaluation') ? serverModel.get('evaluation').toLowerCase() : null,
                indicatorModelName: serverModel.get('indicatorModelName') ? serverModel.get('indicatorModelName').toLowerCase() : null,
                indicatorSortOrder: serverModel.get('indicatorSortOrder')
            }
        }
    },

    buildViewSpecs: function(serverModels) {
        var me = this;
        var specs = {
            STUDENT: [],
            INTERVENTION: [],
            RISK: []
        };
        Ext.each(serverModels, function(serverModel, index, models) {
            if ( specs[serverModel.get('indicatorGroupCode')] ) {
                specs[serverModel.get('indicatorGroupCode')].push(me.buildViewSpec(serverModel));
            } // else just toss it
        });
        return specs;
    },

    newViewSpecColumn: function() {
        return {
            xtype: 'container',
            items: []
        }
    },

    sortViewSpecs: function(specs) {
//        Supposed to produce named arrays like this, where each spec is placed into alternating 'columns', i.e. nested
//        'items' arrays, sorted by an indexing property comic book style:
//        [
//            {
//                xtype: 'container',
//                items: [
//                    {
//                        xtype: 'evaluatedsuccessindicator',
//                        data: {
//                            indicatorName: 'GPA',
//                            indicatorValue: '2.03',
//                            indicatorStatus: 'low'
//                        }
//                    }
//                ]
//            },
//            {
//                xtype: 'container',
//                items: [
//                    {
//                        xtype: 'evaluatedsuccessindicator',
//                        data: {
//                            indicatorName: 'REG',
//                            indicatorValue: 'WN2014',
//                            indicatorStatus: 'high'
//                        }
//                    }
//                ]
//            }
//        ]
        var me = this;
        var sortedSpecs = {}
        for ( specName in specs ) {
            var columns = [me.newViewSpecColumn(), me.newViewSpecColumn()];
            sortedSpecs[specName] = columns;
            var specsForName = specs[specName];
            specsForName.sort(function(a,b) {
                var bySortOrder = a.data.indicatorSortOrder - b.data.indicatorSortOrder;
                if ( bySortOrder !== 0 ) {
                    return bySortOrder;
                }
                var byModelName = a.data.indicatorModelName.localeCompare(b.data.indicatorModelName);
                if ( byModelName !== 0 ) {
                    return byModelName;
                }
                return a.data.indicatorName.localeCompare(b.data.indicatorName);
            });
            var nextColIdx = 0;
            Ext.each(specsForName, function(spec, index, all) {
                columns[nextColIdx].items.push(spec);
                nextColIdx = nextColIdx === 0 ? 1 : 0;
            });
        }
        return sortedSpecs;
    },

    renderViewSpecs: function(specs) {
        var me = this;
        Ext.suspendLayouts();
        if ( specs.STUDENT[0].items.length ) {
            me.getStudentSuccessIndicatorGroup().add(specs.STUDENT);
        } else {
            me.getStudentSuccessIndicatorGroup().hide();
        }
        if ( specs.INTERVENTION[0].items.length ) {
            me.getInterventionSuccessIndicatorGroup().add(specs.INTERVENTION);
        } else {
            me.getInterventionSuccessIndicatorGroup().hide();
        }
        if ( specs.RISK[0].items.length ) {
            me.getRiskSuccessIndicatorGroup().add(specs.RISK);
        } else {
            me.getRiskSuccessIndicatorGroup().up().hide();
        }
        Ext.resumeLayouts(true);
        me.getView().setLoading(false);
    }

});