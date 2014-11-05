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
Ext.define('Ssp.view.admin.forms.config.MessageTemplatesAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.messagetemplatesadmin',
	title: 'Message Templates Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
   controller: 'Ssp.controller.admin.config.MessageTemplatesAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'messagetemplatesdisplayadmin', 
	                    anchor: '100%',
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});