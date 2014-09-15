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
Ext.define('Ssp.view.tools.profile.FinancialAidAwards', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.financialaidawards',
    mixins: ['Deft.mixin.Injectable'],
    store:Ext.create('Ext.data.Store', {
    	fields: [{name: 'accepted', type: 'string'},
                 {name: 'schoolId', type: 'string'},
                 {name: 'termCode', type: 'string'}]
	}),
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            queryMode:'local',
            store: me.store,
            autoScroll: true,
 			markDirty: false,
            columns: [{
            	xtype: 'gridcolumn',
                text: 'Term',
                dataIndex: 'termCode',
                flex: 0.2
            },{
            	xtype: 'gridcolumn',
            	text: 'Accepted',
                dataIndex: 'acceptedLong',
                flex: 0.2
            }]
        });
        
        return me.callParent(arguments);
    }
});