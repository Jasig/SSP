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
				item.journalStepDetail.push(detail);
			}
		});
		if (stepExists==false){
			this.addJournalStep( step, detail );
		}
	},
	
	removeJournalDetail: function( step, detail ){
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.each( item.journalStepDetail, function(innerItem, innerIndex){
					// remove the detail
					if ( innerItem.id == detail.id ){
						Ext.Array.remove(item.journalStepDetail,innerItem);
					}
				},this);
								
				// no details remain, so remove the step
				if (item.journalStepDetail.length<1)
				{
					this.removeJournalStep( step );
				}
			}
		},this);
	},
	
	addJournalStep: function( step, detail ){
		this.get("journalEntryDetails").push( {"journalStep":step, "journalStepDetail": [detail] } );
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
	}
});