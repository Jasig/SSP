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
Ext.define('Ssp.controller.tool.actionplan.TaskTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        personLite: 'personLite',
        task: 'currentTask',
    	treeUtils: 'treeRendererUtils'
    },
    config: {
    	categoryUrl: '',
    	challengeUrl: '',
    	challengeReferralUrl: '',
    	personChallengeUrl: ''
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick',
    		viewready: 'onViewReady'
    	}
   	
    	/*
    	'searchButton': {
			click: 'onSearchClick'
		}
		*/  	
    },
    
	onViewReady: function() {
		var rootNode = null;
		
		this.categoryUrl = this.apiProperties.getItemUrl('category');
		this.challengeUrl = this.apiProperties.getItemUrl('challenge');
		this.challengeReferralUrl = this.apiProperties.getItemUrl('challengeReferral');
		this.personChallengeUrl = this.apiProperties.getItemUrl('personChallenge');
		this.personChallengeUrl = this.personChallengeUrl.replace('{id}',this.personLite.get('id'));

		// clear the categories
		this.treeUtils.clearRootCategories();

    	// load student intake challenges
     	this.treeUtils.appendChildren(null,[{
	        text: 'Student Intake Challenges',
	        id: '0'+'_studentIntakeChallenge',
	        leaf: false,
	        destroyBeforeAppend: false
	      }]);
   
    	// load "all" challenges category
     	this.treeUtils.appendChildren(null,[{
	        text: 'All',
	        id: '0'+'_all',
	        leaf: false,
	        destroyBeforeAppend: false
	      }]);     	
     	
    	// load the categories
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', this.categoryUrl);
    	treeRequest.set('nodeType','category');
    	treeRequest.set('isLeaf', false);
    	treeRequest.set('enableCheckedItems', false);	
    	this.treeUtils.getItems( treeRequest );
    },
    
    onItemExpand: function(nodeInt, obj){
    	var me=this;
    	var node = nodeInt;
    	var url = "";
    	var nodeType = "";
    	var isLeaf = false;
    	var nodeName =  me.treeUtils.getNameFromNodeId( node.data.id );
    	var id = me.treeUtils.getIdFromNodeId( node.data.id );
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	var includeToolTip = false;
    	var toolTipFieldName = "";
    	switch ( nodeName )
    	{
    		case 'category':
    			url = me.categoryUrl + '/' + id + '/challenge/';
    			nodeType = 'challenge';
    			break;
    			
    		case 'studentIntakeChallenge':
    			url = me.personChallengeUrl;
    			nodeType = 'challenge';
     			break;

    		case 'all':
    			url = me.challengeUrl;
    			nodeType = 'challenge';
     			break;     			
     			
    		case 'challenge':
    			url = me.challengeUrl + '/' + id + '/challengeReferral/';
    			nodeType = 'referral';
    			isLeaf = true;
    			includeToolTip = true;
    			toolTipFieldName = "description";
    			break;
    	}
    	
    	if (url != "")
    	{
        	treeRequest.set('url', url);
        	treeRequest.set('nodeType', nodeType);
        	treeRequest.set('isLeaf', isLeaf);
        	treeRequest.set('nodeToAppendTo', node);
        	treeRequest.set('enableCheckedItems',false);
        	treeRequest.set('callbackFunc', me.onLoadComplete);
        	treeRequest.set('callbackScope', me);
        	treeRequest.set('includeToolTip', includeToolTip);
        	treeRequest.set('toolTipFieldName', toolTipFieldName);
        	me.treeUtils.getItems( treeRequest );
        	me.getView().setLoading( true );        	
    	}
    },
    
    onLoadComplete: function( scope ){
    	scope.getView().setLoading( false );
    },
    
    /*
    onSearchClick: function(){
    	Ext.Msg.alert('Attention', 'This is a beta item. Awaiting API methods to utilize for search.'); 
    },
    */
    
    onItemClick: function(view, record, item, index, e, eOpts){
    	var me=this;
    	var successFunc;
    	var name = me.treeUtils.getNameFromNodeId( record.data.id );
    	var id = me.treeUtils.getIdFromNodeId( record.data.id );
    	var challengeId = me.treeUtils.getIdFromNodeId( record.data.parentId );
    	var confidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
    	if (name=='referral')
    	{
	    	successFunc = function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var challengeReferral = null;
		    	if (r.rows != null)
		    	{
		    		Ext.Array.each(r.rows,function(item,index){
		    			if (item.id==id)
		    			{
		    				challengeReferral = item;
		    			}
		    		});
		    		if (challengeReferral != null)
		    		{
			    		me.task.set('name', challengeReferral.name);
			    		me.task.set('description', challengeReferral.description);
			    		me.task.set('link', challengeReferral.link);
			    		me.task.set('challengeReferralId', challengeReferral.id);
		    		}
		    		me.task.set('challengeId', challengeId);
		    		me.appEventsController.getApplication().fireEvent('loadTask');
		    	}		
			};
	    	
			
			// TODO: This fix is a temp solution for the SSP-381
			// but that returns a 405 method not allowed
			// for get and put calls that require an id.
			// This method call should be replaced with a
			// get call after the 381 bug has been resolved.
			// Note: the issue appears to work fine under
			// most local environments and not under a number
			// of server environments where SSP has been deployed.
	    	me.apiProperties.makeRequest({
				url: me.apiProperties.createUrl( me.challengeReferralUrl ), // +'/'+id
				method: 'GET',
				jsonData: '',
				successFunc: successFunc 
			});
    	
    	}
    }
});