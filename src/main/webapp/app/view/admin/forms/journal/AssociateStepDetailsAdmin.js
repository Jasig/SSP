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
Ext.define('Ssp.view.admin.forms.journal.AssociateStepDetailsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatestepdetailsadmin',
	title: 'Step Details Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    		     autoScroll: true,
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            dragGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            hidden: !me.authenticatedPerson.hasAccess('STEP_DETAIL_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});