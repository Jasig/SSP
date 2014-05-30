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
Ext.define('Ssp.view.admin.forms.crg.DisplayReferralsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayreferralsadmin',
	title: 'Referrals Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayReferralsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
		columnRendererUtils: 'columnRendererUtils',
		store: 'challengeReferralsAllStore'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_ASSOCIATIONS')
		        	  }
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
				  cls: 'configgrid',
				  store: me.store,
    		      columns: [
					  {
	                        header: 'Active',
	                        required: true,
	                        dataIndex: 'objectStatus',
							defaultValue: true,
	                        renderer: me.columnRendererUtils.renderObjectStatus,
	                        flex: .10,
	                        field: {
	                            xtype: 'oscheckbox'
	                        }
	                    },
						{ 
    		                  header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1,
    		                  renderer : function(value, metadata, record) {
    		                      metadata.tdAttr = 'data-qtip="' + record.get('description') + '"';
    		                      return value;
    		                  }
    		                }],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
							pageSize: me.apiProperties.getPagingSize(),
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
      		              dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Referral onto a Challenge folder'
      		                       }]  
      		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});