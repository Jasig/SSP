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
Ext.define('Ssp.store.external.Terms', {
	extend: 'Ssp.store.reference.AbstractReferences',
	model: 'Ssp.model.external.Term',
	remoteSort: true,
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },
   
    constructor: function(){
		var me = this;
		me.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('terms'),
    		autoLoad: true});
    	return; 
    },
    
    getCurrentAndFutureTermsStore: function(sortEarliestFirst){
		var me = this;
    	var store = Ext.create('Ext.data.Store', {
		     	model: "Ssp.model.external.Term"
		     });
    	store.loadData(me.getCurrentAndFutureTerms())
		me.sortStoreByDate(store, sortEarliestFirst);
    	return store;
    },

	sortStoreByDate: function(store, sortEarliestFirst){
		if(sortEarliestFirst)
			store.sort('startDate','ASC');
		else
			store.sort('startDate','DESC');
	},

    getFutureTermsStore: function(sortEarliestFirst){
		var me = this;
    	var store = Ext.create('Ext.data.Store', {
		     	model: "Ssp.model.external.Term"
		     });
    	store.loadData(me.getFutureTerms());
		me.sortStoreByDate(store, sortEarliestFirst);
    	return store;
    },

	getTermsFromTermCodesStore: function(termCodes){
		var me = this;
		var terms = [];
		termCodes.forEach(function(termCode){
			var index = me.find( 'code', termCode);
			terms.push(me.getAt(index));
		});
		var store = Ext.create('Ext.data.Store', {
		     	model: "Ssp.model.external.Term"
		     });
    	store.loadData(terms);
		store.sort('startDate', 'DESC');
		return store;
	},

	getTermsFromTermCodes: function(termCodes){
		var me = this;
		return me.getTermsFromTermCodesStore(termCodes).getRange(0);
	},
    
    getCurrentAndFutureTerms: function(maximum){
    	var me = this;
		var startIndex = 0
		var currentTermIndex = me.findBy(me.isCurrentTerm);
		if(maximum){
			var startReportYear = me.getAt(currentTermIndex).get("reportYear");
			startIndex = me.find('reportYear', startReportYear + maximum);
			if(startIndex == -1)
				startIndex = 0;
		}
    	return me.getRange(startIndex, currentTermIndex);
    },

	isPastTerm: function(termCode){
    	var me = this;
		var startIndex = 0
		var currentTermIndex = me.findBy(me.isCurrentTerm);
		var termIndex = me.find('code', termCode)
    	return termIndex <= currentTermIndex ? false : true;
    },
    
    getFutureTerms: function(){
    	var me = this;
    	var currentTermIndex = me.findBy(me.isCurrentTerm) - 1;
    	return me.getRange(0, currentTermIndex);
    },
    
    isCurrentTerm: function(record, id){
    	var me = this;
    	if(record.get('startDate').getTime() >= (new Date()).getTime())
    		return false;
    	return true;
    }
});