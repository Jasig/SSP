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
Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',
	mixins: ['Deft.mixin.Injectable'],
    inject: {
    	colorsStore: 'colorsStore',
    },

	initComponent: function() {
		return this.callParent( arguments );
    },

	renderFriendlyBoolean: function(val, metaData, record) {
		var result = "";
		if (val !== null )
        {
           if (val !== "")
           {
        	   result = ((val===true || val === 'true')?'Yes':'No');
           }
        }
        
        return result;
	},    
    
	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + Ext.util.Format.date( record.get('dueDate') ,'m/d/Y') + '</p>';
        strHtml += '<p>' + ((record.get('completedDate') != null) ? 'COMPLETE' : 'ACTIVE' ) + '</p>';
		strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '<br/>' + record.getCreatedByPersonName().toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},	
	
	renderGoalName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;	
	},	
	
	renderConfidentialityLevelName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;		
	},

	renderCreatedByDate: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y');		
	},

	renderCreatedByDateWithTime: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y g:i A');		
	},	

	renderCreatedBy: function(val, metaData, record) {
	    return record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase();		
	},	
	
	renderCreatedByDateAndName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase() + '</p>';
        strHtml += '<p>' + Ext.util.Format.date( record.get('createdDate'),'m/d/Y') + '</p>';
        strHtml += '</div>';
	    return strHtml;		
	},
 
	renderJournalSourceName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('journalSource').name.toUpperCase() + '</p>';
         strHtml += '</div>';
	    return strHtml;		
	},	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	},

	renderCoachName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getCoachFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderSearchStudentName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},

	renderPersonFullName: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getPersonFullName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},	
	
	renderStudentDetails: function(val, metaData, record) {
		var strHtml = '<div>';
		strHtml += '<p>' + record.getFullName() + '</p>';
		strHtml += '<p>COACH: ' + record.getCoachFullName() + '</p>';
        strHtml += '<p>ID: ' + record.get('schoolId') + '</p>';
        strHtml += '<p>STATUS: ' + record.get('currentProgramStatusName') + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderStudentType: function(val, metaData, record) {
		var strHtml = '<div>';
        strHtml += '<p>' + record.getStudentTypeName() + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderAddToolIcon: function(value,meta,record,rowIx,ColIx, store) {
	    return (record.get("active")==false)?
	                'addToolIcon':
	                'hideAddToolIcon';
	},

	renderErrorMessage: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('errorMessage') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
	
	renderEarlyAlertStatus: function(val, metaData, record) {
		var status = ((record.get('closedDate') != null)? 'Closed' : 'Open');
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + ((record.get('nodeType').toLowerCase() == 'early alert')? status : "N/A") + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
	
	renderActive: function(val, metadata, record) {
		var active = record.get('active') ? "Yes" : "No";
		var strHtml = '<div style="white-space:normal !important;">';
		strHtml += '<p>' + active + '</p>'
		strHtml += '</div>';
		return strHtml;
	},
	
	renderHex: function(val, metadata, record) {
		var hexCode = record.get('hexCode');
		var strHtml = '<div>';
		strHtml += '<div style="float:left;width:49%">';
		strHtml += hexCode;
		strHtml += '</div>'
		strHtml += '<div style="background-color:#' + hexCode + ';width:49%;float:right;">';
		strHtml += '<p>&nbsp;</p>'
		strHtml += '</div>'
		strHtml += '</div>';
		return strHtml;
	},
	
	renderElectiveColor: function(val, metadata, record) {
		var colorsStore = Ext.getStore('colorsStore');
		var color = colorsStore.findRecord('id', val);
		
		if(color == null || color.data == null) {
			return '';
		}
		
		var hexCode = color.data.hexCode;
		var colorName = color.data.name;
		
		var strHtml = '<div>';
		strHtml += '<div style="float:left;width:49%">';
		strHtml += colorName;
		strHtml += '</div>'
		strHtml += '<div style="background-color:#' + hexCode + ';width:49%;float:right;">';
		strHtml += '<p>&nbsp;</p>'
		strHtml += '</div>'
		strHtml += '</div>';
		return strHtml;		
	},
	/**
	 * This method is used to return an object with id values
	 * an array format expected by the ExtJS multiSelect or itemSelect
	 * components.
	 * 
	 * Translates: 
	 * [{"id":"1"},{"id":"2"},{"id":"3"}]
	 * 
	 * Into:
	 * ["1","2","3"]
	 */
	getSelectedIdsForMultiSelect: function( arr ){
		var selectedIds = [];
		Ext.each(arr,function(item,index){
			selectedIds.push(item["id"]);
		});
		return selectedIds;
	},
	
	renderBackgroundColorActive: function(value, metadata, record) {
	    if(record.get('objectStatus') == 'ACTIVE'){
			metadata.style="background-color:#C5D7F1;";
		}
		return value;
	},
	
	renderDateBackgroundColorActive: function(value, metadata, record) {
	    if(record.get('objectStatus') == 'ACTIVE'){
			metadata.style="background-color:#C5D7F1;";
		}
		return Ext.util.Format.date( record.get('createdDate','Y-m-d g:i A'));
	},
	
	renderImportant: function(value, metadata, record) {
		if (record.get('objectStatus') == 'ACTIVE') {
	       metadata.style = "background-color:#C5D7F1";
	    };
		var isImportant = (record.get('isImportant') || record.get('isFinancialAid') || record.get('isF1Visa'));
	    if(isImportant){
			metadata.style += ";color:#BF1C10;"
		}
		return isImportant == true ? "Yes":"";
	},
});
