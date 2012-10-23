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
Ext.define('Ssp.controller.person.ServiceReasonsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        serviceReasonsStore: 'serviceReasonsStore'
    },
    
	init: function() {
		var me=this;

		var serviceReasonsSuccessFunc = function(records,operation,success){
			if (records.length > 0)
	    	{
	    		var items = [];
				Ext.Array.each(records,function(item,index){
	    			items.push(item.raw);
	    		});
				var serviceReasonsFormProps = {
	    				mainComponentType: 'checkbox',
	    				formId: 'personservicereasons', 
	                    fieldSetTitle: 'Select all that apply:',
	                    itemsArr: items, 
	                    selectedItemsArr: me.person.get('serviceReasons'), 
	                    idFieldName: 'id', 
	                    selectedIdFieldName: 'id',
	                    additionalFieldsMap: [] };
	    		
	    		me.formUtils.createForm( serviceReasonsFormProps );	    		
	    	}
		};
		
		me.serviceReasonsStore.load({scope: me, callback: serviceReasonsSuccessFunc});
		
		return this.callParent(arguments);
    }
});