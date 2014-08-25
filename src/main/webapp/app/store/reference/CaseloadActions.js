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
Ext.define('Ssp.store.reference.CaseloadActions', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    constructor: function(){
        var me=this;
        me.callParent( arguments );
		Ext.apply(this, { proxy: '' ,
			  autoLoad: false });
        return me;
    },
    load: function() {
    	var me = this;
        var values = [{id: "EXPORT", name: "Export to CSV"},
    			    {id: "PROGRAM_STATUS", name: "Change Program Status"},
    			    {id: "WATCH_STUDENT", name: "Watch/UnWatch Students"},
    			    {id: "EMAIL", name: "Email Students"}                     
        ];

        me.loadData( values );
        return me;   	
    	
    }
});