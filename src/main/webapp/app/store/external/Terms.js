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
Ext.define('Ssp.store.external.Terms', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.external.Term',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties'
    },
    
    constructor: function() {
        var me = this;
        me.callParent(arguments);
        me.addListener('load', me.sortAfterLoad, me, {
            single: true
        });
        Ext.apply(this.getProxy(), {
            url: this.getProxy().url + this.apiProperties.getItemUrl('terms'),
            extraParams: {
                limit: '-1'
            },
            autoLoad: true
        });
        return;
    },
    
    sortAfterLoad: function() {
        var me = this;
        me.sort('startDate', 'DESC');
    },

    getCurrentTermIndex: function() {
        var me = this;
        var idx = -1;
        var now = new Date().getTime();
        me.each(function(record) {
            if ( record.get('endDate') && record.get('endDate').getTime() < now ) {
                return false;
            }
            idx++;
        });
        // if all terms historical, will return -1. If some or all end in the future, will return the one
        // with the nearest start
        return idx;
    },
    
    getCurrentAndFutureTermsStore: function(sortEarliestFirst, maximum, minimum) {
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            model: "Ssp.model.external.Term"
        });
        store.loadRecords(me.getCurrentAndFutureTerms(maximum, minimum));
        me.sortStoreByDate(store, sortEarliestFirst);
        return store;
    },

    getClonedStoreSortedByDate: function(sortEarliestFirst) {
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            model: "Ssp.model.external.Term"
        });
        store.loadData(me.getRange(0, me.getCount() - 1));
        me.sortStoreByDate(store, sortEarliestFirst);
        return store;
    },

    sortStoreByDate: function(store, sortEarliestFirst) {
        if (sortEarliestFirst) {
            store.sort('startDate', 'ASC');
        } else {
            store.sort('startDate', 'DESC');
        }
    },
    
    getFutureTermsStore: function(sortEarliestFirst) {
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            model: "Ssp.model.external.Term"
        });
        store.loadData(me.getFutureTerms());
        me.sortStoreByDate(store, sortEarliestFirst);
        return store;
    },
    
    getTermsFromTermCodesStore: function(termCodes) {
        var me = this;
        var terms = [];
        Ext.Array.forEach(termCodes, function(termCode){
            var index = me.findExact('code', termCode);
            terms.push(me.getAt(index));
        });
        var store = Ext.create('Ext.data.Store', {
            model: "Ssp.model.external.Term"
        });
        store.loadData(terms);
        store.sort('startDate', 'DESC');
        return store;
    },
    
    getTermsFromTermCodes: function(termCodes) {
        var me = this;
        return me.getTermsFromTermCodesStore(termCodes).getRange(0);
    },
    
    // maximum is the maximum number of school years from current date to return terms for
    // set to null to get all terms
    // minimum is the number of school years from current term to start collecting terms
    // negative values pull previous years, positive goes into the future, 0 pulls current year
    // null start at current term
    getCurrentAndFutureTerms: function(maximum, minimum) {
        var me = this;
        var startIndex = 0
        var endIndex = me.getCurrentTermIndex();
        if (endIndex == -1) {
        	endIndex = me.getCount();
        }

        if (me.getAt(endIndex)) {
            var startReportYear = me.getAt(endIndex).get("reportYear");
            if (maximum !== null && maximum !== undefined) {
                startIndex = me.findExact('reportYear', startReportYear + maximum);
                if (startIndex == -1) {
                    startIndex = 0;
                }
            }

            // No specific call to find the last that meets criteria or to pull indexes
            // This will find the first term (chronologically in the same school year as the currentTerm
            if (minimum !== null && minimum !== undefined ) {
                var index = me.findExact('reportYear', startReportYear + minimum);
                var gindex = index;
                var count = 0;
                while (index != -1) {
                    index = me.findExact('reportYear', startReportYear + minimum, ++index);
                    if (index != -1) {
                        gindex = index;
                    }
                    if (++count >= 10) {
                        break;
                    }
                }
                if (gindex == -1) {
                    endIndex = me.getCount();
                } else {
                    endIndex = gindex;
                }
            }
        }
        return me.getRange(startIndex, endIndex);
    },
    
    isPastTerm: function(termCode) {
        var me = this;
        var startIndex = 0;
        var currentTermIndex = me.getCurrentTermIndex();
        var termIndex = me.findExact('code', termCode);

        if (currentTermIndex && termIndex && me.getAt(termIndex)) {
            if (me.getAt(termIndex).get('endDate').getTime() <= (new Date()).getTime()) {
                return true;
            } else {
                return termIndex <= currentTermIndex ? false : true;
            }
        } else {
            return false;
        }
    },
    
    getFutureTerms: function(){
        var me = this;
        var currentTermIndex = (me.getCurrentTermIndex()-1);
        if (currentTermIndex >= 0) {
            return me.getRange(0, currentTermIndex);
        }
    },

    getCurrentAndFutureYearStore: function(sortEarliestFirst){
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            model: "Ssp.model.external.Term"
        });
        store.loadRecords(me.getCurrentAndFutureTerms());
        me.sortStoreByDate(store, sortEarliestFirst);
        
        
        var uniqueIds = store.collect("reportYear");
        var items = [];
        Ext.Array.each(uniqueIds, function(item){
            var newModel = Ext.create("Ssp.model.external.Term");
            newModel.set('reportYear', item);
            items.push(newModel);
        });
        
        store.loadData(items);
        
        return store;
    }
});
