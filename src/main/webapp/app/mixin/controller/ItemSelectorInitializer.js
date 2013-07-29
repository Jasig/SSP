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
Ext.define('Ssp.mixin.controller.ItemSelectorInitializer', {
    statics: {
        fieldNameIdx: 1,
        getNextFieldNameIdx: function() {
            return this.fieldNameIdx++;
        }
    },

    nextFieldName: function() {
        return 'itemSelectorField' + this.self.getNextFieldNameIdx();
    },

    defineSelectorField: function(initialFieldValues, componentDefOverrides) {
        return Ext.apply({
            xtype: 'itemselectorfield',
            anchor: '100%',
            height: 250,
            name: this.nextFieldName(),
            displayField: 'name',
            valueField: 'id',
            value: ((initialFieldValues && initialFieldValues.length>0) ? initialFieldValues : [] ),
            allowBlank: true,
            buttons: ["add", "remove"],
            listeners: {
                toField: {
                    boundList: {
                        scope: this,
                        drop: this.maybeRefireFromFieldLoadWithNonEmptyStore
                    }
                },
                fromField: {
                    boundList: {
                        scope: this,
                        itemdblclick: this.maybeRefireFromFieldLoadWithNonEmptyStore
                    }
                }
            }
        }, componentDefOverrides);
    },

    defineAndAddSelectorField: function(view, initialFieldValues, componentDefOverrides) {
        var componentDef = this.defineSelectorField(initialFieldValues, componentDefOverrides);
        view.add([componentDef]);
        this.registerAdditionalListeners(view, componentDef);
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
    registerAdditionalListeners: function(view, componentDef) {
        var me = this;
        var itemSelector = me.findItemSelector(view, componentDef);
        var addButton = itemSelector.query('button[iconCls=x-form-itemselector-add]')[0];
        var origAddButtonHandler = addButton.handler;
        addButton.setHandler(function() {
            var me = this;
            origAddButtonHandler.apply(itemSelector);
            me.maybeRefireFromFieldLoadWithNonEmptyStore(view, componentDef);
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
    maybeRefireFromFieldLoadWithNonEmptyStore: function(view, componentDef) {
        var me = this;
        var itemSelector = me.findItemSelector(view, componentDef);
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

    // Factored into method rather than inlined in defineAndAddSelectorField()
    // b/c we need to be able to find the item selector component when
    // callbacks/listeners fire, which could theoretically happen before
    // defineAndAddSelectorField() could cache this result
    findItemSelector: function(view, componentDef) {
        var me = this;
        me.itemSelector = me.itemSelector ||  view.form.findField(componentDef.name);
        return me.itemSelector;
    }
});
