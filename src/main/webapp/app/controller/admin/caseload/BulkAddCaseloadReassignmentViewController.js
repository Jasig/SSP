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
Ext.define('Ssp.controller.admin.caseload.BulkAddCaseloadReassignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
       	authenticatedPerson: 'authenticatedPerson',
       	formUtils: 'formRendererUtils',
       	textStore: 'sspTextStore'
    },
    config: {
        containerToLoadInto: 'adminforms',
        formToDisplay: 'bulkaddcaseloadreassignment'
    },
    control: {
		'saveButton': {
			click: 'onSaveButtonClick'
		}
    },
	init: function() {
	    var me = this;
		return me.callParent(arguments);
    },
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseloadAddReassign') );
		return baseUrl;
    },
    onSaveButtonClick: function(button) {
		var me=this;
   		var messageBox = Ext.Msg.confirm({
   			title:me.textStore.getValueByCode('ssp.message.bulk-add-reassign.confirm-title','Confirm'),
   			msg: me.textStore.getValueByCode('ssp.message.bulk-add-reassign.confirm_save','Performing this action could impact the performance of SSP while the file is being processed.  You may want to consider running this during off hours.  Would you like to continue?'),
   			buttons: Ext.Msg.OKCANCEL,
   			fn: me.completeSave,
   			scope: me
   		});
        messageBox.msgButtons['ok'].setText(me.textStore.getValueByCode('ssp.label.bulk-add-reassign.confirm-ok-button',"Continue"));
        messageBox.msgButtons['cancel'].setText(me.textStore.getValueByCode('ssp.label.bulk-add-reassign.confirm-cancel-button',"Cancel"));
        return;
    },
    completeSave: function(button) {
    	var me=this;
    	var url=me.getBaseUrl();
        if(button == 'ok'){
            if(!Ext.ComponentQuery.query('#file', me.getView())[0].getValue()) {
                Ext.Msg.alert(
                    me.textStore.getValueByCode('ssp.message.bulk-add-reassign.error-title','SSP Error'),
                    me.textStore.getValueByCode('ssp.message.bulk-add-reassign.select-file','Please select a file to upload.')
                    );
               return;
            }
            me.getView().getForm().submit(
            {
                url: url,
                method : 'POST',
                waitMsg: me.textStore.getValueByCode('ssp.message.bulk-add-reassign.wait-message','Uploading Your File...'),
                success: function (fp, o)
                {
                    Ext.Msg.alert(
                        me.textStore.getValueByCode('ssp.message.bulk-add-reassign.success-title','Success'),
                        me.textStore.getValueByCode('ssp.message.bulk-add-reassign.upload-successful','File uploaded successfully.')
                        );
                    me.displayMain();
                },
                failure: function (fp,o)
                {
                    if(o.result.message)
                    {
                        Ext.Msg.alert(
                            me.textStore.getValueByCode('ssp.message.bulk-add-reassign.error-title','SSP Error'),
                            me.textStore.getValueByCode('ssp.message.bulk-add-reassign.upload-failure-message','%MESSAGE%', {'%MESSAGE%':o.result.message})
                            );
                        return;
                    }
                    if(o.result.exception)
                    {
                        Ext.Msg.alert(
                            me.textStore.getValueByCode('ssp.message.bulk-add-reassign.error-title','SSP Error'),
                            me.textStore.getValueByCode('ssp.message.bulk-add-reassign.upload-failure','There has been an error uploading your file.  Please contact the system administrator.')
                            );
                        return;
                    }
                }
            });
        }
	},
    displayMain: function(){
        var me = this;
        var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
    }
});