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
Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
    	textStore: 'sspTextStore'
    },
    model: 'Ssp.model.Tool',
    autoLoad: false,
    
    constructor: function(){

        var me=this;
        me.callParent( arguments );

        me.textStore.load( function (){
        	var sspTools = [
        	                { group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.main'), toolType: "profile", active: true },
        	                { group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.intake'), toolType: "studentintake", active: true },
        	                { group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.actionplan'), toolType: "actionplan", active: true },
        	                { group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.journal'), toolType: "journal", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.earlyalert'), toolType: "earlyalert", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.map'), toolType: "map", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.emailstudent'), toolType: "emailstudent", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.acommodation'), toolType: "accommodation", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.documents'), toolType: "documents", active: true },
        	    	        { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.notes'), toolType: "personnotes", active: true },
        	                { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.caseloadassign'), toolType: "toolcaseloadreassignment", active: true },
        	    			
        	                /*
        	                { group:'rc1', name: "", toolType: "earlyalert", active: false },
        	                { group:'rc1', name: "----------------", toolType: "earlyalert", active: false },
        	                { group:'rc1', name: "Config Link", toolType: "earlyalert", active: false },
        	                { group:'rc1', name: "Program Viewer", toolType: "earlyalert", active: false },
        	                { group:'rc1', name: "MAP Help", toolType: "earlyalert", active: false }


        	                
        	                 { group:'rc1', name: "SIS", toolType: "StudentInformationSystem", active: true },
        	                 { group:'rc1', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
        	                 { group:'rc1', name: "Student Success", toolType: "StudentSuccess", active: false }
        	                 */
        	            ];

        	            // set the model
        	            me.loadData( me.applySecurity( sspTools ) );
        	}        
        );

        return me;

    },
    
    applySecurity: function( tools ){
    	var me=this;
    	var sspSecureTools = [];
    	
    	Ext.Array.each( tools, function( tool, index){
    		var toolSecurityIdentifier = tool.toolType.toUpperCase() + '_TOOL';
    		if (me.authenticatedPerson.hasAccess( toolSecurityIdentifier ) && tool.active)
    		{
    			sspSecureTools.push( tool );
    		}
    	});
    	
    	return sspSecureTools;
    }
           
    //groupField: 'group'
});
