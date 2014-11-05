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
Ext.define('Ssp.controller.admin.campus.DefineCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	campusService: 'campusService',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	panelLayout: null,
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin'
    },
    control: {  	
    	prevBtn: '#prevButton',
    	nextBtn: '#nextButton',
    	finishBtn: '#finishButton',
    	
    	'nextButton': {
			click: 'onNextClick'
		},
		
		'prevButton': {
			click: 'onPrevClick'
		},
		
		'finishButton': {
			click: 'onFinishClick'
		},
		
		'cancelCampusEditorButton': {
			click: 'onCancelClick'
		}
    },
	init: function() {
		this.store.load();
		this.panelLayout = this.getView().getLayout();
		this.enableButtons();
		return this.callParent(arguments);
    },

    navigate: function( direction ){
        this.panelLayout[direction]();
        this.enableButtons();
    },
    
    enableButtons: function(){
    	var layout = this.panelLayout;
    	this.getPrevBtn().setDisabled(!layout.getPrev());
        this.getNextBtn().setDisabled(!layout.getNext());
        if (!layout.getNext())
        {
        	this.getFinishBtn().setDisabled(false);
        }else{
        	this.getFinishBtn().setDisabled(true);
        }
    },
    
	onNextClick: function(button) {
		this.navigate("next");
	},
	
	onPrevClick: function(button){
		this.navigate("prev");
	},
	
	onFinishClick: function(button){
		var me=this;
		var campusView = Ext.ComponentQuery.query('.editcampus')[0];
		var campusForm = campusView.getForm();		
		var formsToValidate = [campusForm];
		var validateResult = me.formUtils.validateForms( formsToValidate );
		
		// validate the campus and save
		if ( validateResult.valid ) 
		{
			campusForm.updateRecord();
			me.getView().setLoading( true );
			me.campusService.saveCampus( me.model.data, {success:me.saveCampusSuccess, 
				  failure:me.saveCampusFailure, 
				  scope: me} );		
		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},

    saveCampusSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
    },
    
    saveCampusFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },

	onCancelClick: function(button){
		this.displayMain();
	},
    
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});	
	}
});