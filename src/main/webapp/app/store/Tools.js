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

		me.onTextStoreLoad();

        return me;
    },
    onTextStoreLoad:function() {
    	var me=this;
    	var sspTools = [
				{ group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.main', 'Main'), toolType: "profile", active: true },
				{ group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.intake', 'Intake'), toolType: "studentintake", active: true },
				{ group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.actionplan', 'Action Plan'), toolType: "actionplan", active: true },
				{ group:'beta', name: me.textStore.getValueByCode('ssp.label.tools.journal', 'Journal'), toolType: "journal", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.earlyalert', 'Early Alert'), toolType: "earlyalert", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.map', 'MAP'), toolType: "map", active: true },
		    	{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.template', 'Template'), toolType: "template", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.emailstudent', 'Email Student'), toolType: "emailstudent", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.acommodation', 'Accommodation'), toolType: "accommodation", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.documents', 'Documents'), toolType: "documents", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.notes', 'Notes'), toolType: "personnotes", active: true },
				{ group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.caseloadassign', 'Caseload Reassign'), toolType: "toolcaseloadreassignment", active: true },
			    { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.externalviewtool1', 'External View 1'), toolType: "externalviewtool1", active: true },
			    { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.externalviewtool2', 'External View 2'), toolType: "externalviewtool2", active: true },
			    { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.externalviewtool3', 'External View 3'), toolType: "externalviewtool3", active: true },
			    { group:'rc1', name: me.textStore.getValueByCode('ssp.label.tools.externalviewtool4', 'External View 4'), toolType: "externalviewtool4", active: true }
		];

            // set the model
            me.loadData( me.applySecurity( sspTools ) );
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
