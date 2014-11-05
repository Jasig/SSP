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
Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name:'entryDate',type: 'date',dateFormat:'c'},
             {name:'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      },
			 {name:'journalSource', type:'auto'},
			 {name: 'journalS', convert: function(value, record) {
			 	return record.get('journalSource').name;
			 }},
			  {name: 'journalModifiedBy', convert: function(value, record) {
			 	return record.get('modifiedBy').firstName + ' ' + record.get('modifiedBy').lastName;		
			 }},
			  {name: 'journalCreatedBy', convert: function(value, record) {
			 	return record.get('createdBy').firstName + ' ' + record.get('createdBy').lastName;		
			 }},
			 {name: 'journalCFlevel', convert: function(value, record) {
			 	return record.get('confidentialityLevel').name;	
			 }},
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