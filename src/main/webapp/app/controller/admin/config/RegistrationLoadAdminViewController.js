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
Ext.define('Ssp.controller.admin.config.RegistrationLoadAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	store: 'configurationOptionsStore',
		formUtils: 'formRendererUtils',
		apiProperties: 'apiProperties'
    },
	
    control: {
    	'addButton': {
			click: 'onAddClick'
		},
		rlNameField: '#rlName',
    	rlDescriptionField: '#rlDescription',
    	rlRangeStartField: '#rlRangeStart',
    	rlRangeEndField: '#rlRangeEnd',
    	rlLabelField: '#rlLabel',
		
		registrationloadlistadmin: '#registrationloadlistadmin',
		registrationloadaddadmin: '#registrationloadaddadmin'
    }, 
	 
	init: function() {
		var me=this;
		
		me.store.clearFilter();
		
		me.store.filter([{filterFn: function(item) { return item.get("name") == 'registration_load_ranges'; }}]);
		
		me.formUtils.reconfigureGridPanel( me.getRegistrationloadlistadmin(), me.store);
		me.store.load();
		
		
		
		return me.callParent(arguments);
    }, 
    
	onAddClick: function(button) {
		var me=this;
		var formsToValidate = [me.getRegistrationloadaddadmin().getForm()];
        var validateResult = me.formUtils.validateForms(formsToValidate);
        if (validateResult.valid) {
		var configData = Ext.pluck(me.store.data.items,'data')[0];
		var valuesString = configData.value;
		var vsLength = (valuesString.length-1);
		var subVS = valuesString.substr(0,vsLength);
		
		if(valuesString.length)
		{
			var addString = "," + "\n\t\t\t\t\t\t\t\t\t\t\t{" + '"name":' + '"' + me.getRlNameField().getValue() + '",'
							+ '"description":' + '"'  + me.getRlDescriptionField().getValue() + '",'
							+ '"rangeStart":' + me.getRlRangeStartField().getValue() + ','
							+ '"rangeEnd":' + me.getRlRangeEndField().getValue() + ','
							+ '"rangeLabel":' + '"' + me.getRlLabelField().getValue() + '"' + "}"
							+ "]";
		}
							
		else{
			var addString = "[" + "{" + '"name":' + '"' + me.getRlNameField().getValue() + '",'
							+ '"description":' + '"'  + me.getRlDescriptionField().getValue() + '",'
							+ '"rangeStart":' + me.getRlRangeStartField().getValue() + ','
							+ '"rangeEnd":' + me.getRlRangeEndField().getValue() + ','
							+ '"rangeLabel":' + '"' + me.getRlLabelField().getValue() + '"' + "}"
							+ "]";
		}
		var newValueString = subVS + addString;
		configData.value = newValueString;
		var rec = me.store.first();
		Ext.Ajax.request({
				url: me.store.getProxy().url+"/"+configData.id,
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: configData,
				success: function(response, view) {
						var r = Ext.decode(response.responseText);
						rec.commit();
						me.getRegistrationloadlistadmin().getStore().sync();
						rec.persisted = true;
						
				me.getRegistrationloadaddadmin().getForm().reset();
					
				},
				failure: this.apiProperties.handleError
			}, this);
		}
        else {
            me.formUtils.displayErrors(validateResult.fields);
        }
      
	}

});