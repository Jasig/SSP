Ext.define('Ssp.controller.tool.actionplan.TaskTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
    	treeUtils: 'treeRendererUtils'
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick'
    	},
   	
    	'searchButton': {
			click: 'onSearchClick'
		}   	
    },
    
	init: function() {
		var rootNode = null;
		
		// clear the categories
		this.treeUtils.clearRootCategories();

    	// load student intake challenges
     	this.treeUtils.appendChildren(null,[{
	        text: 'Student Intake Challenges',
	        id: '0'+'_studentIntakeChallenges',
	        leaf: false,
	        destroyBeforeAppend: false
	      }]);
     	     	
    	// load the categories
    	this.treeUtils.getItems({url: 'reference/challengeCategory/', 
                                 nodeType: 'challengeCategory', 
                                 isLeaf: false});

		return this.callParent(arguments);
    },
    
    onItemExpand: function(nodeInt, obj){
    	var node = nodeInt;
    	var url = "";
    	var nodeType = "";
    	var isLeaf = false;
    	var nodeName =  this.treeUtils.getNameFromNodeId(node.data.id);
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
 
    	switch ( nodeName )
    	{
    		case 'challengeCategory':
    			url = 'reference/challenge/'; // +id;
    			nodeType = 'challenge';
    			break;
    			
    		case 'studentIntakeChallenge':
    			url = 'person/'+this.person.get('id')+'/challenge/';
    			nodeType = 'challenge';
     			break;

    		case 'challenge':
    			url = 'reference/challengeReferral/'; // +id;
    			nodeType = 'referral';
    			isLeaf = true;
    			break;
    	}

    	if (url != "")
    	{
    		this.treeUtils.getItems({url: url, 
    			                    nodeType: nodeType, 
    			                    isLeaf: isLeaf, 
    			                    nodeToAppendTo: node});
    	}
    },
    
    onSearchClick: function(){
    	console.log('TaskTreeViewController->onSearchClick');    	
    },
    
    onItemClick: function(view, record, item, index, e, eOpts){
    	var me=this;
    	var successFunc;
    	var name = this.treeUtils.getNameFromNodeId( record.data.id );
    	var id = this.treeUtils.getIdFromNodeId( record.data.id );
    	var challengeId = this.treeUtils.getIdFromNodeId( record.data.parentId );
    	var confidentialityLevel = "EVERYONE";
    	// load the referral
    	if (name=='referral')
    	{
	    	successFunc = function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	if (r)
		    	{
		    		var args = new Object();
		    		args.name = r.name;
		    		args.description = r.description || '';
		    		args.challengeReferralId = r.challengeReferralId;
		    		args.challengeId = challengeId;
		    		args.confidentialityLevel = confidentialityLevel;
		    		me.appEventsController.getApplication().fireEvent('loadTask', args);
		    	}		
			};
	    	
	    	this.apiProperties.makeRequest({
				url: this.apiProperties.createUrl('reference/challengeReferral/')+id,
				method: 'GET',
				jsonData: '',
				successFunc: successFunc 
			});
    	
    	}
    }
    
	
});