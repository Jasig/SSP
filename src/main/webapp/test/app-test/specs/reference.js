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
describe("reference.js --> Challenge Items", function() {
    var challengesStore = null;
    
    beforeEach(function(){
    	
        if (!challengesStore) {
           challengesStore = Application.getStore('reference.Challenges');
        }

        expect(challengesStore).toBeTruthy();
        
        challengesStore.load();
       	
        waitsFor(
            function(){ return !challengesStore.isLoading(); },
            "Challenges Reference Store load never completed",
            4000
        );

    });   
    
    it("Challenge Reference should have at least one item",function(){
        expect(challengesStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Child Care Arrangement Items", function() {
	var childCareArrangementsStore = null;

    beforeEach(function(){
    	
        if (!childCareArrangementsStore) {
        	childCareArrangementsStore = Application.getStore('reference.ChildCareArrangements');
        }

        expect(childCareArrangementsStore).toBeTruthy();
        
        childCareArrangementsStore.load();
       	
        waitsFor(
            function(){ return !childCareArrangementsStore.isLoading(); },
            "Child Care Arrangements Reference Store load never completed",
            4000
        );

    });   
    
    it("Child Care Arrangements Reference should have at least one item",function(){
        expect(childCareArrangementsStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Citizenship Items", function() {
	var citizenshipsStore = null;

    beforeEach(function(){
    	
        if (!citizenshipsStore) {
        	citizenshipsStore = Application.getStore('reference.Citizenships');
        }

        expect(citizenshipsStore).toBeTruthy();
        
        citizenshipsStore.load();
       	
        waitsFor(
            function(){ return !citizenshipsStore.isLoading(); },
            "Citizenships Reference Store load never completed",
            4000
        );

    });   
    
    it("Citizenships Reference should have at least one item",function(){
        expect(citizenshipsStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Education Goal Items", function() {
	var educationGoalsStore = null;

    beforeEach(function(){
    	
        if (!educationGoalsStore) {
        	educationGoalsStore = Application.getStore('reference.EducationGoals');
        }

        expect(educationGoalsStore).toBeTruthy();
        
        educationGoalsStore.load();
       	
        waitsFor(
            function(){ return !educationGoalsStore.isLoading(); },
            "Education Goals Reference Store load never completed",
            4000
        );

    });   
    
    it("Education Goals Reference should have at least one item",function(){
        expect(educationGoalsStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Education Level Items", function() {
	var educationLevelsStore = null;

    beforeEach(function(){
    	
        if (!educationLevelsStore) {
        	educationLevelsStore = Application.getStore('reference.EducationLevels');
        }

        expect(educationLevelsStore).toBeTruthy();
        
        educationLevelsStore.load();
       	
        waitsFor(
            function(){ return !educationLevelsStore.isLoading(); },
            "Education Levels Reference Store load never completed",
            4000
        );

    });   
    
    it("Education Levels Reference should have at least one",function(){
        expect(educationLevelsStore.getCount()).toBeGreaterThan(0);
    });

});


describe("reference.js --> Ethnicity Items", function() {
	var ethnicitiesStore = null;

    beforeEach(function(){
    	
        if (!ethnicitiesStore) {
        	ethnicitiesStore = Application.getStore('reference.Ethnicities');
        }

        expect(ethnicitiesStore).toBeTruthy();
        
        ethnicitiesStore.load();
       	
        waitsFor(
            function(){ return !ethnicitiesStore.isLoading(); },
            "Ethnicities Reference Store load never completed",
            4000
        );

    });   
    
    it("Ethnicities Reference should have at least one item",function(){
        expect(ethnicitiesStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Funding Source Items", function() {
	var fundingSourcesStore = null;

    beforeEach(function(){
    	
        if (!fundingSourcesStore) {
        	fundingSourcesStore = Application.getStore('reference.FundingSources');
        }

        expect(fundingSourcesStore).toBeTruthy();
        
        fundingSourcesStore.load();
       	
        waitsFor(
            function(){ return !fundingSourcesStore.isLoading(); },
            "FundingSources Reference Store load never completed",
            4000
        );

    });   
    
    it("FundingSources Reference should have at least one item",function(){
        expect(fundingSourcesStore.getCount()).toBeGreaterThan(0);
    });

});


describe("reference.js --> Marital Status Items", function() {
	var maritalStatusesStore = null;

    beforeEach(function(){
    	
        if (!maritalStatusesStore) {
        	maritalStatusesStore = Application.getStore('reference.MaritalStatuses');
        }

        expect(maritalStatusesStore).toBeTruthy();
        
        maritalStatusesStore.load();
       	
        waitsFor(
            function(){ return !maritalStatusesStore.isLoading(); },
            "MaritalStatuses Reference Store load never completed",
            4000
        );

    });   
    
    it("MaritalStatuses Reference should have at least one item",function(){
        expect(maritalStatusesStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Student Status Items", function() {
	var studentStatusesStore = null;

    beforeEach(function(){
    	
        if (!studentStatusesStore) {
        	studentStatusesStore = Application.getStore('reference.StudentStatuses');
        }

        expect(studentStatusesStore).toBeTruthy();
        
        studentStatusesStore.load();
       	
        waitsFor(
            function(){ return !studentStatusesStore.isLoading(); },
            "StudentStatuses Reference Store load never completed",
            4000
        );

    });   
    
    it("StudentStatuses Reference should have at least one item",function(){
        expect(studentStatusesStore.getCount()).toBeGreaterThan(0);
    });

});

describe("reference.js --> Veteran Status Items", function() {
	var veteranStatusesStore = null;

    beforeEach(function(){
    	
        if (!veteranStatusesStore) {
        	veteranStatusesStore = Application.getStore('reference.VeteranStatuses');
        }

        expect(veteranStatusesStore).toBeTruthy();
        
        veteranStatusesStore.load();
       	
        waitsFor(
            function(){ return !veteranStatusesStore.isLoading(); },
            "VeteranStatuses Reference Store load never completed",
            4000
        );

    });   
    
    it("VeteranStatuses Reference should have at least one item",function(){
        expect(veteranStatusesStore.getCount()).toBeGreaterThan(0);
    });

});