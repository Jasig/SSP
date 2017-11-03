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
Ext.define('Ssp.controller.tool.map.PrintPlanController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController'
       
    },
    control: {
    	optionsPrintView: '#optionsPrintView',
    	
    	'fullFormat': {
    	   selector: '#fullFormat',
    	   listeners: {
            change: 'onoptionsPrintClick'
           }
        },
        
        'matrixFormat': {
    	   selector: '#matrixFormat',
    	   listeners: {
            change: 'onprintmatrixFormatClick'
           }
        },

        'shortMatrixFormat': {
    	   selector: '#shortMatrixFormat',
    	   listeners: {
            change: 'onprintShortMatrixFormatClick'
           }
        }
    },
    
	init: function() {
		var me=this;
		me.getOptionsPrintView().hide();
		return this.callParent(arguments);
    },
    
    onoptionsPrintClick: function(cb, nv, ov){
        var me=this;
        if (nv){
        me.getOptionsPrintView().show();
        }
    
    },
    
    onprintmatrixFormatClick: function(cb, nv, ov){
        var me=this;
        if (nv){
        me.getOptionsPrintView().hide();
        }
    },

    onprintShortMatrixFormatClick: function(cb, nv, ov){
        var me=this;
        if (nv){
        me.getOptionsPrintView().hide();
        }
    }

});
