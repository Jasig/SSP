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
Ext.define('Ssp.store.reference.Texts', {
//    extend: 'Ssp.store.reference.AbstractReferences',   Even though this is store is a reference, it cannot extend AbstractReferences due to a circular reference with apiProperties
	extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.Text',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiUrlStore: 'apiUrlStore'
    },
    constructor: function(){
    	me = this;

    	var url = this.getItemUrl('blurb');
    	if (Ext.util.Cookies.get('defaultLangCode') != null) {
    		url = url + "?langCode=" + Ext.util.Cookies.get('defaultLangCode')
    	}

		Ext.apply(me, {
							proxy: me.getProxy(url),
							autoLoad: false,
							autoSync: false,
							pageSize: -1,
							params : {
								page : 0,
								start : 0,
								limit : -1,
							}
						}
		);
    	return me.callParent(arguments);
    },
    getValueByCode:function(code, defaultVal, data)
    {
    	var me=this;
    	var index = me.findExact('code',code);
    	var str = '';
    	if(index >= 0){
    		str = me.getAt(index).get('value');
    	}
    	else if (defaultVal != null){
    		str = defaultVal;
//    		str = '{' + defaultVal + '}';
    	}
    	else{
    		str = code;
    	}

    	if (data!=null) {
		   var output = str.replace(/%[^%]+%/g, function(match) {
			   if (match in data) {
				   return(data[match]);
			   } else {
				   return("");
			   }
		   });
		   return(output);
        }
   		return str;
    },
	/*
	 * Returns the base url of an item in the system.
	 * @itemName - the name of the item to locate.
	 * 	the returned item is returned by name from the apiUrlStore.
	 */
	getItemUrl: function( itemName ){
		var index = this.apiUrlStore.findExact('name', itemName);
		if(index == undefined || index < 0 )
			return "";
		var record = this.apiUrlStore.getAt(index);
		var url = "";
		if (record != null)
			url = record.get('url');
		return url;
	},
	createUrl: function(value){
		return Ssp.mixin.ApiProperties.getBaseApiUrl() + value;
	},
	getProxy: function(url){
		me = this;
		var proxyObj = {
			type: 'rest',
			url: me.createUrl(url),
			async: true,
			simpleSortMode: true,
			directionParam:'sortDirection',
			actionMethods: {
				create: "POST",
				read: "GET",
				update: "PUT",
				destroy: "DELETE"
			},
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'results',
				successProperty: 'success',
				message: 'message'
			},
			writer: {
				type: 'json',
				successProperty: 'success'
			}
		};
		return proxyObj;
	}
});