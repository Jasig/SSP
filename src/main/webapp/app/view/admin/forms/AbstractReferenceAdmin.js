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
Ext.require([
    'Ext.ux.grid.FiltersFeature'
]);
Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.abstractreferenceadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.AbstractReferenceAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        colorsStore: 'colorsStore',
        colorsUnpagedStore: 'colorsUnpagedStore',
        colorsAllStore: 'colorsAllStore',
        colorsAllUnpagedStore: 'colorsAllUnpagedStore',
        confidentialityLevelOptionsStore: 'confidentialityLevelOptionsStore',
		columnRendererUtils: 'columnRendererUtils'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var me=this;

        var filters = {
                ftype: 'filters',
                encode: false, // json encode the filter query
                local: true   // defaults to false (remote filtering)
        };
    	var sort = me.sort;
    	
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', { 
    							clicksToEdit: 2,
    							controller: me.getController(),
    							listeners: {
	    							cancelEdit: function(rowEditor, item){
	    								var columns = rowEditor.grid.columns;
	    								var record = rowEditor.context.record;
	    								
										if(record.get('id') == '' || record.get('id') == null || record.get('id') == undefined){
											me.store.load();
										}
										
	    								Ext.each(rowEditor.editor.items.items, function(item) {
											
	    									if( item.store ) {
											
	    										item.store.clearFilter(true);
	    									}			
	    								});
										
										
	    							}
    							}
    					});
    	     	
    	
    	var addVisible = true;
    	var deleteVisible = true;
    	var headerInstructions = null;
    	var hasPagingToolbar = true;

    	if( me.interfaceOptions ) {
    		addVisible = me.interfaceOptions.addButtonVisible;
    		deleteVisible = me.interfaceOptions.deleteButtonVisible;    
    		headerInstructions = me.interfaceOptions.headerInstructions;

    		if ( typeof me.interfaceOptions.hasPagingToolbar != 'undefined' ) {
    		    hasPagingToolbar = me.interfaceOptions.hasPagingToolbar;
    		}
    	}

        // Special handling for view config so we don't accidentally clobber
        // any config that might have been set in the constructor but still
        // can use the struct below to fill in gaps. If we just passed "me"
        // to applyIf and viewConfig had been set already, then all this
        // config would be skipped.
        me.viewConfig = me.viewConfig || {};
        Ext.applyIf(me.viewConfig, {
            itemId: 'gridView',
                plugins: {
                ptype: 'gridviewdragdrop',
                    dropGroup: 'gridtogrid',
                    dragGroup: 'gridtogrid',
                    enableDrop: me.interfaceOptions ? me.interfaceOptions.dragAndDropReorder : false,
                    enableDrag: me.interfaceOptions ? me.interfaceOptions.dragAndDropReorder : false
            },
            listeners:{
                drop:function(node, data, mouseOverItem, dropPosition, eOpts){
                    var store = me.store;
                    var items = store.data.items;
                    var droppedItem = data.records[0].data;
                    var pageNumber = 0;
                    var lastItem = items[items.length-1];

                    if(items[items.length-1].data.id == droppedItem.id) {
                        lastItem = items[items.length-2];
                    }

                    while((lastItem.index - store.params.limit * pageNumber) > store.params.limit) {
                        pageNumber++;
                    }

                    var previousSortOrder = store.params.limit * pageNumber;

                    var jsonData = [];

                    Ext.each(items, function(item) {
                        if(item.data.id == droppedItem.id || item.data.sortOrder == previousSortOrder || (item.data.sortOrder - previousSortOrder) > 1) {
                            item.data.sortOrder = previousSortOrder + 1;
                            jsonData.push(Ext.encode(item.data));
                        }
                        previousSortOrder = item.data.sortOrder;
                    });

                    Ext.Ajax.request({
                        url: store.getProxy().url +"/",
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        jsonData: jsonData,
                        success: function(response, view) {
                            var r = Ext.decode(response.responseText); 
                            var columns = me.columns;                            
                            var record = store.findRecord("id", r.id, 0, false, false, true); 
                            			
							store.load();
							if( sort && sort.direction ) {
								store.sort(sort.field, sort.direction);
							}
                        },
                        failure: me.apiProperties.handleError
                    }, this);

                }
            }
        });

        Ext.applyIf(me,
            {
                plugins: cellEditor,
                selType: 'rowmodel',
				cls: 'configgrid',
				features: [filters],
                columns: me.applyColumnOverrides([
					{
                        header: 'Active',
                        required: true,
                        dataIndex: 'objectStatus',
						defaultValue: true,
                        renderer: me.columnRendererUtils.renderObjectStatus,
                        flex: 0.10,
                        field: {
                            xtype: 'oscheckbox'
                        },
                        sortOrder: 10
                    },
                    { header: 'Name',
                        dataIndex: 'name',
                    	editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false,
                            maxLength: 80
                        },
                        flex: 50,
                        sortOrder: 20
                    },
                    { header: 'Description',
                        dataIndex: 'description',
                        flex: 50,
                        field: {
                            xtype: 'textfield'
                        },
                        sortOrder: 30
                    }
                ]),
                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        displayInfo: true,
                        pageSize: me.apiProperties.getPagingSize(),
                        hidden: !hasPagingToolbar
                    },
                    {
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            text: 'Add',
                            iconCls: 'icon-add',
                            xtype: 'button',
                            hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON') || !addVisible,
                            action: 'add',
                            itemId: 'addButton'
                        }, '-', {
                            text: 'Delete',
                            iconCls: 'icon-delete',
                            xtype: 'button',
                            hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_DELETE_BUTTON') || !deleteVisible,
                            action: 'delete',
                            itemId: 'deleteButton'
                        }]
                    },{
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            xtype: 'label',
                            text: headerInstructions ? headerInstructions : 'Double-click to edit an item.'
                        }]
                    }]
            });

    	me.callParent(arguments);
    },

    applyColumnOverrides: function(defaults) {
        var me = this;
        var columnsByDataIndex = {};
        var columns = [];
        Ext.each(defaults, function(column, index){
            var dataIndex = column.dataIndex;
            if ( dataIndex ) {
                columnsByDataIndex[dataIndex] = column;
            }
        });
        if ( me.interfaceOptions && me.interfaceOptions.columnOverrides && me.interfaceOptions.columnOverrides.length > 0 ) {
            Ext.each(me.interfaceOptions.columnOverrides, function(columnOverride, index){
                if ( columnOverride.dataIndex ) {
                    if ( columnsByDataIndex[columnOverride.dataIndex] ) {
                        columnsByDataIndex[columnOverride.dataIndex] = Ext.apply({}, columnOverride, columnsByDataIndex[columnOverride.dataIndex]);
                    } else {
                        columnsByDataIndex[columnOverride.dataIndex] = columnOverride;
                    }
                }
            });
        }
        for ( i in columnsByDataIndex ) {
            columnsByDataIndex[i].field = me.applyColumnFieldOverrides(i, columnsByDataIndex[i].field);
            columns.push(columnsByDataIndex[i]);
        }
        return Ext.Array.sort(columns, function(o1, o2) {
            return o1.sortOrder - o2.sortOrder;
        });
    },

    applyColumnFieldOverrides: function(dataIndex, defaults) {
        var me = this;
        if ( me.interfaceOptions && me.interfaceOptions.columnFieldOverrides && me.interfaceOptions.columnFieldOverrides[dataIndex] ) {
            return Ext.apply({}, me.interfaceOptions.columnFieldOverrides[dataIndex], defaults);
        }
        return defaults;

    }
});
