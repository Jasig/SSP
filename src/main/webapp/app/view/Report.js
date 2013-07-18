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
Ext.define('Ssp.view.Report',{
	extend: 'Ext.Component',
	alias: 'widget.sspreport',
	height: 0,
	width: 0,
    config: {
    	downloadForm: null,
    	downloadFrame: null
    },
    autoEl: {tag: 'iframe', cls: 'x-hidden', src: Ext.SSL_SECURE_URL},
    initComponent: function(){
    	var me=this;
    	me.downloadForm = Ext.getBody().createChild({
    		tag: 'form', 
			cls: 'x-hidden',
			id: 'sspPortletReportform',
			target: 'sspPortletIFrame'
		});
    	
    	me.downloadFrame = Ext.getBody().createChild({
    		tag: 'iframe',
			cls: 'x-hidden',
			id: 'sspPortletIFrame',
			name: 'iframe',
			src: Ext.SSL_SECURE_URL
		});

    	return me.callParent(arguments);
    },
    
    load: function(config){
    	this.getEl().dom.src = config.url + (config.params ? '?' + Ext.urlEncode(config.params) : '');
    },
    
    loadBlankReport: function( url ){
    	window.open(url,'_blank','');
    },
    
    postReport: function( args ){
		var me=this;
    	Ext.Ajax.request({
			url: args.url,
			form: me.downloadForm,
			params: args.params,
			isUpload: true,
			headers: { 'Content-Type': 'application/json' },
			success: function(response,responseText){
	  			  //Ext.Msg.alert('Notification','Please download your report.');
	  		},
			failure: function(response, options) {
				Ext.Msg.alert('Notification',response.responseText);
	  	    },
			scope: me
		},me);
    }
});