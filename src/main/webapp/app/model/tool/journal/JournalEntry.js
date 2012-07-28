Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name:'entryDate',type: 'date',dateFormat:'time', defaultValue: new Date()},
             {name:'confidentialityLevel', type:'auto'},
			 {name:'journalSource', type:'auto'},
			 {name:'journalTrack', type:'auto'},
			 {name:'journalEntryDetails',type:'auto',defaultValue:[]}],
	
	getConfidentialityLevelId: function(){
		return this.get('confidentialityLevel').id;
	},
			 
	addJournalDetail: function( step, detail){
		var stepExists = false;
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				// step exists. add the journal detail
				stepExists=true;
				item.journalStepDetails.push(detail);
			}
		});
		if (stepExists==false){
			this.addJournalStep( step, detail );
		}
	},
	
	removeJournalDetail: function( step, detail ){
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.each( item.journalStepDetails, function(innerItem, innerIndex){
					// remove the detail
					if ( innerItem.id == detail.id ){
						Ext.Array.remove(item.journalStepDetails,innerItem);
					}
				},this);
								
				// no details remain, so remove the step
				if (item.journalStepDetails.length<1)
				{
					this.removeJournalStep( step );
				}
			}
		},this);
	},
	
	addJournalStep: function( step, detail ){
		this.get("journalEntryDetails").push( {"journalStep":step, "journalStepDetails": [detail] } );
	},
	
	removeJournalStep: function( step ){
		var journalEntryDetails = this.get("journalEntryDetails");
		Ext.Array.each(journalEntryDetails,function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.remove(journalEntryDetails,item);
			}
		});
	},
	
	removeAllJournalEntryDetails: function(){
		this.set('journalEntryDetails',[]);
	},
	
	getGroupedDetails: function(){
		var groupedDetails=[];
		var journalEntryDetails = this.get('journalEntryDetails');
		Ext.Array.each(journalEntryDetails,function(item,index){
			var stepName=item.journalStep.name;
    		var details = item.journalStepDetails;
    		Ext.Array.each(details,function(detail,index){
    			detail.group=stepName;
    			groupedDetails.push( detail );
    		},this);
    	},this);
		return groupedDetails;
	},
	
	/**
	 * Used to clean the group property from journalEntryDetails,
	 * so that they save correctly. This method should only be
	 * used against json data that is ready to be saved and not
	 * against this object itself.
	 */
	clearGroupedDetails: function( journalEntryDetails ){
		Ext.Array.each(journalEntryDetails,function(item,index){
    		var details = item.journalStepDetails;
    		Ext.Array.each(details,function(detail,index){
    			delete detail.group;
    		},this);
    	},this);
		return journalEntryDetails;		
	}
});