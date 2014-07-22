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
"use strict";
var ssp = ssp || {};

(function($, fluid) {

    ssp.EarlyAlertsSummary = function(container, options) {
    	
        // construct the new component
        var that = fluid.initView('ssp.EarlyAlertsSummary', container, options);

        var loadRoster = function() {
            var data = options.earlyAlerts;
            var newModel = fluid.copy(that.pager.model);
            newModel.totalRange = data.length;
            newModel.pageIndex = 0;
            newModel.pageCount = Math.max(1, Math.floor((newModel.totalRange - 1) / newModel.pageSize) + 1);
            fluid.clear(that.pager.options.dataModel);
            fluid.model.copyModel(that.pager.options.dataModel, data);
            that.pager.permutation = undefined;
            that.pager.events.onModelChange.fire(newModel, that.pager.model, that.pager);
            fluid.model.copyModel(that.pager.model, newModel);
        }

        if (earlyAlerts && earlyAlerts.length != 0) {
           

            // Initialize the EarlyAlerts Summary
            var pagerOptions = {
                dataModel: earlyAlerts,
                columnDefs: [
                    { key: 'createdDate', valuebinding: '*.createdDate', sortable: true },
                    { key: 'status', valuebinding: '*.status', sortable: true },
                    { key: 'lastResponseDate', valuebinding: '*.lastResponseDate', sortable: true },
                    { key: 'courseTitle', valuebinding: '*.courseTitle', sortable: true },
                    { key: 'courseTermName', valuebinding: '*.courseTermName', sortable: true }
                ],
                bodyRenderer: {
                    type: 'fluid.pager.selfRender',
                    options: {
                        selectors: { root: that.options.selectors.rosterTable },
                        row: 'row:'
                    }
                },
                pagerBar: {
                    type: 'fluid.pager.pagerBar', 
                    options: {
                        pageList: {
                            type: 'fluid.pager.renderedPageList',
                            options: { linkBody: 'a' }
                        }
                    }
                }
            };
            that.pager = fluid.pager(container, pagerOptions);

            // Click event for selecting a student

        } 
    };

    // defaults
    fluid.defaults('ssp.EarlyAlertsSummary', {
    	selectors: {
            errorsDiv:               '.errors',
            errorTemplate:           '.error-message-template',
            loadingMessage:          '.loading-message',
            earlyAlerts:              '.early-alerts',
            earlyAlertsTable:             '.early-alerts-table'
        }
    });
	
})(jQuery, fluid);
