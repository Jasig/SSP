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
Ext.define('Ssp.view.ErrorWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.ssperrorwindow',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
    	store: 'errorsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Error! Please correct the errors listed below:',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
				    modal: true, 
		    		layout: 'fit',
    				items: [{
    			        xtype: 'grid',
    			        border: false,
    			        columns:[{ header: 'Error', 
		 				           dataIndex: 'label',
						           sortable: false,
						           menuDisabled: true,
						           flex:.25 
						         },{ header: 'Message', 
						           dataIndex: 'errorMessage',
						           renderer: me.columnRendererUtils.renderErrorMessage,
						           sortable: false,
						           menuDisabled: true,
						           flex:.75 
						         }],     
    			        store: me.store
    			    }],
    			    bbar: [
    			           { xtype: 'button', 
    			        	 text: 'OK', 
    			        	 itemId: 'closeButton', 
    			        	 handler: function() {
    			        		 me.close();
    			             }
    			           }
    			         ]
		    	    });
    	
    	return me.callParent(arguments);
    }
});