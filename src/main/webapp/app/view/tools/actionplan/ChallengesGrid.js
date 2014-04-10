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
Ext.define('Ssp.view.tools.actionplan.ChallengesGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.challengesgrid',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ChallengesGridViewController',
    inject: {
        searchChallengeReferralStore: 'searchChallengeReferralStore',
        apiProperties: 'apiProperties',
        columnRendererUtils: 'columnRendererUtils'
    },
    width: 450,
    border: 0,
    itemId: 'challengesGrid',
    fullReferralDescription: function(){
        var me = this;
        
        return function(value, metaData, record){
            var fullDesc = record.get('challengeReferralDescription');
            metaData.tdAttr = 'data-qtip="' + fullDesc + '"';
            var tpl = new Ext.Template('<div class="wrappable-cell">{NAME}</div>');
            
            return tpl.apply({
                NAME: record.get('challengeReferralDescription')
            });
            //return fullDesc;
        }
    },
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            store: this.searchChallengeReferralStore,
            cls: 'tasksgrid',
			enableDragDrop: false,
            columns: [{
                header: 'Challenge',
                dataIndex: 'challengeName',
                xtype: 'gridcolumn',
                renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
                    var tpl = new Ext.Template('<div class="wrappable-cell">{NAME}</div>');
                    
                    return tpl.apply({
                        NAME: record.get('challengeName')
                    });
                },
                width: 150
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'challengeReferralName',
                header: 'Referrals',
                flex: 1,
                renderer: me.fullReferralDescription()
            }],
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
				itemId: 'challengeGridPager',
				store: this.searchChallengeReferralStore,
                //pageSize: me.apiProperties.getPagingSize(),
               listeners: {
                    afterrender: function(){
                        var a = Ext.query("button[data-qtip=Refresh]");
                        for (var x = 0; x < a.length; x++) {
                            a[x].style.display = "none";
                        }
                    }
                }
            }],
           multiSelect: true,
            viewConfig: {
                copy: true,
				
                plugins: {
                    ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtogrid',
		                  enableDrag: true
                }
            
            }
        });
        
        return me.callParent(arguments);
    }
    
});
