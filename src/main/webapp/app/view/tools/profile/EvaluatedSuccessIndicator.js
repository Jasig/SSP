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
Ext.define('Ssp.view.tools.profile.EvaluatedSuccessIndicator', {
    extend: 'Ext.Container',
    alias: 'widget.evaluatedsuccessindicator',
    width: '100%',
    height: '100%',
    
    initComponent: function(){
        var me = this;
        
        var tpl = new Ext.XTemplate('<tpl for=".">' +
            '<div class="indicator-wrap" style="width:100%;">' +
                '<div class="indicator-label" data-qtip="{indicatorDescription}">{indicatorName}:</div>' +
                '<div class="indicator-value">{indicatorValue}</div>' +
                '<div class="indicator-glyph">' +
                    '<span class="fa-stack">' +
                        '<i class="fa fa-circle fa-stack-3x" style="color:{[this.getIconColor(values.indicatorStatus)]};"></i>' +
                        '<i class="fa {[this.getGlyph(values.indicatorStatus)]} fa-stack-3x fa-inverse" style="color:{[this.getGlyphColor(values.indicatorStatus)]};"></i>' +
                    '</span>' +
                '</div>' +
            '</div>' +
        '</tpl>', {
            getGlyph: function(iStatus){
                var glyph = 'fa-ban';
                if (iStatus) {
                    var risk = iStatus.toString().toLowerCase();
                    switch (risk) {
                        case "high":
                            glyph = 'fa-check-circle';
                            break;
                        case "medium":
                            glyph = 'fa-minus-circle';
                            break;
                        case "low":
                            glyph = 'fa-times-circle';
                            break;
                    }
                }
                return glyph;
            },
            getIconColor: function(iStatus){
                var color = 'white';
                if (iStatus) {
                    var risk = iStatus.toString().toLowerCase();
                    switch (risk) {
                        case "high":
                            color = 'white';
                            break;
                        case "medium":
                            color = 'black';
                            break;
                        case "low":
                            color = 'white';
                            break;
                    }
                }
                return color;
            },
            getGlyphColor: function(iStatus){
                var color = '#e8e8e8';
                if (iStatus) {
                    var risk = iStatus.toString().toLowerCase();
                    switch (risk) {
                        case "high":
                            color = 'green';
                            break;
                        case "medium":
                            color = 'yellow';
                            break;
                        case "low":
                            color = 'red';
                            break;
                    }
                }
                return color;
            }
        });
        
        Ext.apply(me, {
            layout: 'anchor',
            padding: '4 10 4 10',
            defaults: {
                anchor: '100%'
            },
            tpl: tpl
        });
        
        return me.callParent(arguments);
    }
});
