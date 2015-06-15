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
Ext.define('Ssp.controller.CustomizableExportController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        exportService: 'exportService'
    },
    control: {
        'exportButton': {
            click: 'onExportClick'
        },
        'cancelButton': {
            click: 'onCancelClick'
        }
    },
    init: function() {
        var me=this;
        return me.callParent(arguments);
    },

    getBulkCriteria: function() {
        var me = this;
        return me.getView().getBulkCriteria();
    },
    
    onExportClick: function() {
    	var me=this;
     	var view = me.getView();
     	var bulkCriteria = me.getBulkCriteria();
        var exportCustomOptions = [];

     	if (view) {
     	    var exportCheckboxes = view.query('checkboxgroup[name=exportColumnOptions]')[0];

     	    if (exportCheckboxes) {
     	        exportCheckboxes = exportCheckboxes.getChecked();

                if (exportCheckboxes.length > 0) {
                    Ext.Array.each(exportCheckboxes, function(rec){
                        exportCustomOptions.push(rec.name);
                    },me);
                }
              	window.open(me.exportService.buildExportCustomizableSearchUrl(bulkCriteria, exportCustomOptions),'_blank');
                view.close();
                return;
     	    }
     	}
        me.errorMsg();
    },

    errorMsg: function() {
        Ext.Msg.alert('SSP Error','An error occurred in Custom Exportable Search. ' +
        'Please refresh and try again, if this keeps occurring, contact your System Administrator.');
    },

    closeView: function() {
        var me = this;
        me.getView().close();
    },

    onCancelClick: function() {
        var me=this;
        me.closeView();
    }
});
