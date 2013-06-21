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
Ext.define('Ssp.model.external.AbstractExternal', {
    extend: 'Ext.data.Model',
    fields: [{name: 'name', type: 'string'},
             {name: 'code', type: 'string'}],
    
 	populateFromGenericObject: function( record ){
		if (record != null)
		{
			for (fieldName in this.data)
	    	{
				//TODO this was orginally if(record[fieldName]) this does not work for booleans
				// Revisit this at some point
				if ( record typeof === 'object' && fieldName in record ) {
				  this.set( fieldName, record[fieldName] );
				} else {
				  this.set(fieldName, null);
				}
	    	}
		}
    }
});
