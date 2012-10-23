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
Ext.define('Ssp.controller.person.SpecialServiceGroupsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
    	store: 'specialServiceGroupsBindStore',
    	service: 'specialServiceGroupService'
    },
	init: function() {
		var me=this;
				
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },
    
	getAllSuccess: function( r, scope ){
		var me=scope;
    	var items;
    	var view = me.getView();
    	var selectedSpecialServiceGroups = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('specialServiceGroups') );
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);
    		
    		items = [{
	            xtype: 'itemselectorfield',
	            itemId: 'specialServiceGroupsItemSelector',
	            name: 'specialServiceGroups',
	            anchor: '100%',
	            height: 250,
	            fieldLabel: 'Service Groups',
	            store: me.store,
	            displayField: 'name',
	            valueField: 'id',
	            value: ((selectedSpecialServiceGroups.length>0) ? selectedSpecialServiceGroups : [] ),
	            allowBlank: true,
	            buttons: ["add", "remove"],
				listeners: {
					toField: {
						boundList: {
							scope: me,
							drop: me.maybeRefireFromFieldLoadWithNonEmptyStore
						}
					},
					fromField: {
						boundList: {
							scope: me,
							itemdblclick: me.maybeRefireFromFieldLoadWithNonEmptyStore

						}
					}
				}
	        }];
    		view.add(items);
			me.registerAdditionalListeners();
    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    },

	// Drops, double clicks and button clicks have to be handled separately -
	// there's no single event they both fire from ItemSelector to indicate that
	// a selection has been updated. But they both have the same problem when
	// selecting the last item in the "fromField" (see below). Drop handlers are
	// registered with standard view config above. Button registration is a
	// little bit different and is handled here. Note that you can't just
	// replace itemSelector.onAddBtnClick b/c that method is registered as the
	// button handler when the button is created, so the button won't see your
	// replacement.
	registerAdditionalListeners: function() {
		var me = this;
		var itemSelector = me.findItemSelector();
		var addButton = itemSelector.query('button[iconCls=x-form-itemselector-add]')[0];
		var origAddButtonHandler = addButton.handler;
		addButton.setHandler(function() {
			var me = this;
			origAddButtonHandler.apply(itemSelector);
			me.maybeRefireFromFieldLoadWithNonEmptyStore();
		}, me);
	},

	// Hack to work around a bug in ItemSelector.setValue() which prevents
	// selecting the last item in the "fromField". The fromField's store count
	// is decremented immediately when an item is selected, but setValue(),
	// which is called after that decrement, assumes the fromField store is
	// uninitialized if that decrement results in an empty fromField store. In
	// that case it just registers a 'load' event listener on the store and
	// returns. The early return prevents the ItemSelector from seeing an
	// updated list of selected items.
	maybeRefireFromFieldLoadWithNonEmptyStore: function() {
		var me = this;
		var itemSelector = me.findItemSelector();
		var fromField = me.itemSelector.fromField;
		var toField = me.itemSelector.toField;
		var origGetCount = fromField.store.getCount;
		// this is safe b/c we don't even start to initialize the view
		// until after the store has been initialized, which obviates the guard
		// against uninitialized fromField stores in ItemSelector.setValue()
		if ( origGetCount.apply(fromField.store) === 0 ) {
			fromField.store.getCount = function() { return 1; };
			fromField.store.fireEvent('load', fromField.store);
			fromField.store.getCount = origGetCount;
		}
	},

	// Factored into method rather than inlined in getAllSuccess() b/c we need
	// to be able to find the iterm selector component when callbacks/listeners
	// fire, which could theoretically happen before getAllSuccess() could cache
	// this result
	findItemSelector: function() {
		var me = this;
		me.itemSelector = me.itemSelect ||  me.getView().form.findField("specialServiceGroups");
		return me.itemSelector;
	}
});