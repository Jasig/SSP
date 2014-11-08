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
Ext.define('Ssp.view.tools.profile.EvaluatedSuccessIndicator', {
    extend: 'Ext.Container',
    alias: 'widget.evaluatedsuccessindicator',
    width: '100%',
    height: '100%',
    
    initComponent: function(){
        var me = this;

        var tpl = new Ext.XTemplate('<tpl for=".">' +
                '<div class="wrapper">' +
                    '<div class="success-indicator {[this.statusToCls(values.indicatorStatus)]}">' +
                        '<h3 class="title" data-qtip="{indicatorName}: {indicatorDescription}">{indicatorName}</h3>' +
                        '<p class="value" data-qtip="{indicatorValue}">{indicatorValue}</p>' +
                        '<p class="rating"><i class="fa {[this.statusToIconCls(values.indicatorStatus)]}" ></i>{[this.statusToDisplayName(values.indicatorStatus)]}</p>' +
                    '</div>' +
            '</tpl>', {
                statusToCls: function(status) {
                    switch (status ? status.toString().toLowerCase() : null) {
                        case "high":
                            return 'positive';
                        case "medium":
                            return 'neutral';
                        case "low":
                            return 'negative';
                        default:
                            return '';
                    }
                },
                statusToIconCls: function(status) {
                    switch (status ? status.toString().toLowerCase() : null) {
                        case "high":
                            return 'fa-check-circle';
                        case "medium":
                            return 'fa-minus-circle';
                        case "low":
                            return 'fa-times-circle';
                        default:
                            return 'fa-ban';
                    }
                },
                statusToDisplayName: function(status) {
                    switch (status ? status.toString().toLowerCase() : null) {
                        case "high":
                            return 'Good';
                        case "medium":
                            return 'Okay';
                        case "low":
                            return 'Poor';
                        default:
                            return 'Unsure';
                    }
                }
        });

        
        Ext.apply(me, {
            layout: 'anchor',
            padding: '2 2 2 2',
            defaults: {
                anchor: '100%'
            },
            tpl: tpl
        });
        
        return me.callParent(arguments);
    }
});
