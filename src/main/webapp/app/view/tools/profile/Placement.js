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
Ext.define('Ssp.view.tools.profile.Placement', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.placement',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],	
    width: '100%',
	height: '100%',
	minHeight: 615,
    controller: 'Ssp.controller.tool.profile.PlacementViewController',
    autoScroll: true,
    inject: {
        store: 'placementStore'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            store: me.store,
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'type',
                    text: 'Type',
					flex: 1,
				    
					renderer: function(value, metaData, record, rowIndex, colIndex, store) {
						var me = this;
						if((record.data.hasDetails == true) ){
							return '<a title="Click to get more detailed information about test results from Smarter Measure." target="_test_results" href="'+record.get("testProviderLink")+'">'+value+'</a>';
						}
			             return value;
			         }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'score',
                    text: 'Score',
					flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'status',
                    text: 'Status',
					flex: 1
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'takenDate',
                    text: 'Date',
					renderer: Ext.util.Format.dateRenderer('m/d/Y'),
					flex: 1
                },{
                    xtype: 'gridcolumn',
                    dataIndex: 'outcome',
                    text: 'Outcome',
					flex: 1
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});