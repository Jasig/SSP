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
Ext.define('Ssp.view.tools.legacyremarks.LegacyRemarks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.legacyremarks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    minHeight: '400',
    width: '100%',
    height: '100%',
    autoScroll: true,
	title: 'Legacy System Remarks/Notes and Communication to Student',
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            store: me.store,
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'type',
                    text: 'Type',
                    flex: .10
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'author',
                    text: 'Author',
                    flex: .20
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    text: 'Remark',
                    flex: .20
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dateandtime',
                    text: 'Date and Time',
                    flex: .20
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'comment',
                    text: 'Comment',
					sortable: 'false',
                    flex: .30
                }
            ],
            viewConfig: {
                markDirty:false
            }
        });

        me.callParent(arguments);
    }
});