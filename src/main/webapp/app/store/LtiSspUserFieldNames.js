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
Ext.define('Ssp.store.LtiSspUserFieldNames', {
    extend: 'Ext.data.Store',
    mixins: [ 'Deft.mixin.Injectable'],
    model: 'Ssp.model.FilterDiscreteValues',
    autoLoad: false,
    constructor: function(){
        var me=this;
        me.callParent( arguments );
		Ext.apply(this, { proxy: '' ,
			  autoLoad: false });
		me.load();
        return me;
    },
    load: function() {
        var me=this;
        var values = [{ displayValue:'username', code: 'username'},
                      { displayValue:'schoolId', code: 'schoolId'},
                      { displayValue: 'primaryEmailAddress', code: 'primaryEmailAddress'}
        ];
        // set the model
        me.loadData( values );
        return me;    	
    	
    }
           
});