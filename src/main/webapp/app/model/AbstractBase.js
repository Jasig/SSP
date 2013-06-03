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
Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'},
             {name: 'createdBy',
              convert: function(value, record) {
		            var obj  = {id:value.id || '',
		                        firstName: value.firstName || '',
		                        lastName: value.lastName || ''};	
		            return obj;
		      }
             },
             {name: 'modifiedBy',
              convert: function(value, record) {
 		            var obj  = {id:value.id || '',
 		                        firstName: value.firstName || '',
 		                        lastName: value.lastName || ''};	
 		            return obj;
 		      }
             },
             {name: 'createdDate', type: 'date', dateFormat: 'time'},
             /*,{name: 'objectStatus', type: 'string'}*/
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'}
             ],
    
	populateFromGenericObject: function( record ){
		if (record != null)
		{
			for (fieldName in this.data)
	    	{
				if ( record[fieldName] )
	    		{
	    			this.set( fieldName, record[fieldName] );
	    		}
	    	}
		}
    },
    
    getCreatedByPersonName: function(){
    	return this.get('createdBy').firstName + ' ' + this.get('createdBy').lastName;
    },

    getCreatedById: function(){
    	return this.get('createdBy').id + ' ' + this.get('createdBy').id;
    },
    
    getFormattedCreatedDate: function(){
    	return Ext.util.Format.date( this.get('createdDate'),'m/d/Y');   	
    },
    
    getFormattedModifiedDate: function(){
    	return Ext.util.Format.date( this.get('modifiedDate'),'m/d/Y');   	
    },
    
    /* Used in conjunction with sorting on loading/refreshing the grid view. 
     * objectStatus is translated into the 'active' field when loading to and
     * from the backend, otherwise return the name as passed. If future 
     * fields have the same sort of translation as active/objectStatus do, 
     * add those exceptions here. 
     */
    getServerSideFieldName: function( fieldName ) {
    	if(fieldName == 'active') {
    		return 'objectStatus';
    	}     	
    	return fieldName;
    }
});
