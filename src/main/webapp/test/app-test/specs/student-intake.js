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
var personId = '252de4a0-7c06-4254-b7d8-4ffc02fe81ff';


describe("student-intake.js --> Employment Shift Items", function() {
	var employmentShiftsStore = null;

    beforeEach(function(){
    	
        if (!employmentShiftsStore) {
        	employmentShiftsStore = Application.getStore('reference.EmploymentShifts');
        }

        expect(employmentShiftsStore).toBeTruthy();
        
        var loaded = false;
		Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
		Form.load(personId,{
			success: function( formData ) {
				var employmentShifts = formData.data.referenceData.employmentShifts;
				Ext.getStore('reference.EmploymentShifts').loadData( employmentShifts );
				loaded = true;
			}
		});
       	
        waitsFor(
            function(){ return loaded; },
            "Employment Shifts Reference Store load never completed",
            4000
        );

    });   
    
    it("Employment Shifts Reference should have at least one item",function(){
        expect(employmentShiftsStore.getCount()).toBeGreaterThan(0);
    });

}); 

describe("student-intake.js --> Gender Items", function() {
	var gendersStore = null;

    beforeEach(function(){
    	
        if (!gendersStore) {
        	gendersStore = Application.getStore('reference.Genders');
        }

        expect(gendersStore).toBeTruthy();
        
        var loaded = false;
		Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
		Form.load(personId,{
			success: function( formData ) {
				var genders = formData.data.referenceData.genders;
				Ext.getStore('reference.Genders').loadData( genders );
				loaded = true;
			}
		});
       	
        waitsFor(
            function(){ return loaded; },
            "Genders Reference Store load never completed",
            4000
        );

    });   
    
    it("Genders Reference should have at least one item",function(){
        expect(gendersStore.getCount()).toBeGreaterThan(0);
    });

});

describe("student-intake.js --> States Items", function() {
	var statesStore = null;

    beforeEach(function(){
    	
        if (!statesStore) {
        	statesStore = Application.getStore('reference.States');
        }

        expect(statesStore).toBeTruthy();
        
        var loaded = false;
		Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
		Form.load(personId,{
			success: function( formData ) {
				var states = formData.data.referenceData.states;
				Ext.getStore('reference.States').loadData( states );
				loaded = true;
			}
		});
       	
        waitsFor(
            function(){ return loaded; },
            "States Reference Store load never completed",
            4000
        );

    });   
    
    it("States Reference should have at least one item",function(){
        expect(statesStore.getCount()).toBeGreaterThan(0);
    });

});