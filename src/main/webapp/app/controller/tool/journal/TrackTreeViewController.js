Ext.define('Ssp.controller.tool.journal.TrackTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        journalEntry: 'currentJournalEntry',
        person: 'currentPerson',
    	treeUtils: 'treeRendererUtils'
    },
    config: {
    	journalTrackUrl: '',
    	journalStepUrl: '',
    	journalStepDetailUrl: ''
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick'
    	} 	
    },
    
	init: function() {
		var rootNode = null;
		this.journalTrackUrl = this.apiProperties.getItemUrl('journalTrack');
		this.journalStepUrl = this.apiProperties.getItemUrl('journalStep');
		this.journalStepDetailUrl = this.apiProperties.getItemUrl('journalStepDetail');

		this.loadSteps();

    	this.appEventsController.assignEvent({eventName: 'setJournalTrack', callBackFunc: this.loadSteps, scope: this});		
		
		return this.callParent(arguments);
    },
    
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'setJournalTrack', callBackFunc: this.loadSteps, scope: this});

        return this.callParent( arguments );
    },    
    
    loadSteps: function(){
		// clear the categories
		this.treeUtils.clearRootCategories();
		
		var journalTrackId = this.journalEntry.get('journalTrack').id;
		
    	// load the steps
		if (journalTrackId != null && journalTrackId != "")
		{
			var treeRequest = new Ssp.model.util.TreeRequest();
	    	treeRequest.set('url', this.journalStepUrl);
	    	treeRequest.set('nodeType','journalStep');
	    	treeRequest.set('isLeaf', false);
	    	treeRequest.set('enableCheckedItems', false);	
	    	this.treeUtils.getItems( treeRequest );			
		}
    },
    
    onItemExpand: function(nodeInt, obj){
    	var node = nodeInt;
    	var url = this.journalStepDetailUrl;
    	var nodeType = "";
    	var isLeaf = true;
    	var nodeName =  this.treeUtils.getNameFromNodeId( node.data.id );
    	var id = this.treeUtils.getIdFromNodeId( node.data.id );
    	if (url != "")
    	{
        	var treeRequest = new Ssp.model.util.TreeRequest();
        	treeRequest.set('url', url);
        	treeRequest.set('nodeType', nodeType);
        	treeRequest.set('isLeaf', isLeaf);
        	treeRequest.set('nodeToAppendTo', node);
        	treeRequest.set('enableCheckedItems',true);	
    		this.treeUtils.getItems( treeRequest );
    	}
    },
    
    onItemClick: function(view, record, item, index, e, eOpts){
    	/*
    	var me=this;
    	var successFunc;

    	var name = this.treeUtils.getNameFromNodeId( record.data.id );
    	var id = this.treeUtils.getIdFromNodeId( record.data.id );
    	var challengeId = this.treeUtils.getIdFromNodeId( record.data.parentId );
    	var confidentialityLevelId = "afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c";
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
		    		args.challengeReferralId = r.id;
		    		args.challengeId = challengeId;
		    		args.confidentialityLevelId = confidentialityLevelId;
		    		console.log(r);
		    		me.appEventsController.getApplication().fireEvent('loadTask', args);
		    	}		
			};
	    	
	    	this.apiProperties.makeRequest({
				url: this.apiProperties.createUrl( this.challengeReferralUrl+id ),
				method: 'GET',
				jsonData: '',
				successFunc: successFunc 
			});
    	
    	}
 		*/
    }

});