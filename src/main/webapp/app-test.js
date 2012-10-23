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
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ssp': './app'
	}
});

Ext.require([
            'Ext.app.Application',
         	'Ssp.model.Person',
         	'Ssp.model.tool.studentintake.StudentIntakeForm',
         	'Ssp.model.tool.studentintake.PersonDemographics',
         	'Ssp.model.tool.studentintake.PersonEducationGoal',
         	'Ssp.model.tool.studentintake.PersonEducationPlan',
         	'Ssp.model.reference.AbstractReference',
         	'Ssp.view.admin.forms.AbstractReferenceAdmin',
         	'Ssp.mixin.ApiProperties',
         	'Ssp.util.FormRendererUtils',
         	'Ssp.util.ColumnRendererUtils',
         	'Ext.tab.*'
         ]);

var Application = null;

Ext.onReady(function() {
	Deft.Injector.configure({
	    currentPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"0"});
	        },
	        singleton: true
	    },
	    apiProperties: {
	        fn: function(){
	            return new Ssp.mixin.ApiProperties({});
	        },
	        singleton: true
	    },
	    formRendererUtils:{
	        fn: function(){
	            return new Ssp.util.FormRendererUtils({});
	    	},
	        singleton: true
	    }
	});
	
	Application = Ext.create('Ext.app.Application', {
        name: 'Ssp',

        controllers: [
            'MainViewController'
        ],

        launch: function() {
            //include the tests in the test.html head
            jasmine.getEnv().addReporter(new jasmine.TrivialReporter());
            jasmine.getEnv().execute();
        }
    });
});