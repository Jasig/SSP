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
Ext.define('Ssp.store.DirectoryPersonSearch', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.SearchPerson',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
    remoteSort: true,
    config: {
        baseUrlName: 'directoryPersonSearch'
    },

	constructor: function(){
		var me=this;
		var p = me.apiProperties.getProxy(me.apiProperties.getItemUrl(me.getBaseUrlName()));
		p.directionParam = 'sortDirection';
        p.simpleSortMode = true;
		Ext.apply(me, {
							proxy: p,
							autoLoad: false,
							autoSync: false
						});
		return me.callParent(arguments);
}});