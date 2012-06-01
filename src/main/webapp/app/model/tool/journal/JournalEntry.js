Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name: 'confidentialityLevel', type:'auto'},
			 {name:'journalSource', type:'auto'},
			 {name:'journalTrack', type:'auto'},
			 {name:'journalEntryDetails',type:'auto',defaultValue:[]}],
			 
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
		this.get("journalEntryDetails").push( {"journalStep":step, "journalStepDetails":[detail] } );
	},
	
	removeJournalStep: function( step ){
		var journalEntryDetails = this.get("journalEntryDetails");
		Ext.Array.each(journalEntryDetails,function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.remove(journalEntryDetails,item);
			}
		});
	}

});