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
Ext.define('Ssp.controller.admin.config.EnrollmentStatusAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        store: 'configurationOptionsUnpagedStore',
        formUtils: 'formRendererUtils',
        apiProperties: 'apiProperties'
    },
    
    control: {
        'addButton': {
            click: 'onAddClick'
        },
        esCodeField: '#esCode',
        esLabelField: '#esLabel',
        esDefaultField: '#esDefault',
        
        
        enrollmentstatuslistadmin: '#enrollmentstatuslistadmin',
        enrollmentstatusaddadmin: '#enrollmentstatusaddadmin'
    },
    
    init: function(){
        var me = this;
        me.store.clearFilter();
        
        me.store.filter([{
            filterFn: function(item){
                return item.get("name") == 'status_code_mappings';
            }
        }]);
        
        
        me.formUtils.reconfigureGridPanel(me.getEnrollmentstatuslistadmin(), me.store);
        me.store.load({
            extraParams: {
                limit: "-1"
            }
        });
        
        
        return me.callParent(arguments);
    },
    
    clearFormFields: function(){
        var me = this;
        me.getEsCodeField().setValue('');
        me.getEsLabelField().setValue('');
    },
    
    onAddClick: function(button){
        var me = this;
        var formUtils = me.formUtils;
        var formsToValidate = [me.getEnrollmentstatusaddadmin().getForm()];
        var validateResult = me.formUtils.validateForms(formsToValidate);
        if (validateResult.valid) {
            var configData = Ext.pluck(me.store.data.items, 'data')[0];
            var valuesString = configData.value;
            var pointer = valuesString.indexOf('default');
			
            var vsLength = (valuesString.length - 2);
			
            if (pointer != -1) {
				var defaultPointer = (pointer - 1);
				var subDefault = valuesString.substr(0, defaultPointer);
				var rest = valuesString.substr(defaultPointer, valuesString.length - 2);
			}
			else
			{
				
				var subDefault = valuesString.substr(0, vsLength);
				var rest = '';
			}
			
		
            if (valuesString.length) {
				
					 var addString =  '"' + me.getEsCodeField().getValue() + '":' +
                	'"' +
                	me.getEsLabelField().getValue();
			
               
                
            }
            
            else {
                var addString = "{" + '"' + me.getEsCodeField().getValue() + '":' +
                '"' +
                me.getEsLabelField().getValue();
            }
			
            
            if (me.getEsDefaultField().getValue()) {
                var addDefaultString = addString + '",' +
                '"default":' +
                '"' +
                me.getEsDefaultField().getValue() +
                '"' +
                "}";
            }
            else {
                var addDefaultString = addString + '"';
            }
			
			
            if (me.getEsDefaultField().getValue()){
				var newValueString = subDefault + addDefaultString;
			}
			else{
				if(rest != ''){
					
						var newValueString = subDefault  + addDefaultString + "," + rest;
						}
				else
					var newValueString = subDefault + addDefaultString + '"' + "}";
			}
		
			
            configData.value = newValueString;
            var rec = me.store.first();
            Ext.Ajax.request({
                url: me.store.getProxy().url + "/" + configData.id,
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                jsonData: configData,
                success: function(response, view){
                    var r = Ext.decode(response.responseText);
                    rec.commit();
                    me.getEnrollmentstatuslistadmin().getStore().sync();
                    rec.persisted = true;
                    
                    me.getEnrollmentstatusaddadmin().getForm().reset();
                    
                },
                failure: this.apiProperties.handleError
            }, this);
        }
        else {
            me.formUtils.displayErrors(validateResult.fields);
        }
        
    }
    
});
