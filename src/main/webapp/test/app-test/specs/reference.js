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

describe("reference.js --> Educational Goal Items", function() {
	var educationalGoalsStore = null;

    beforeEach(function(){
    	
        if (!educationalGoalsStore) {
        	educationalGoalsStore = Application.getStore('reference.EducationalGoals');
        }

        expect(educationalGoalsStore).toBeTruthy();
        
        educationalGoalsStore.load();
       	
        waitsFor(
            function(){ return !educationalGoalsStore.isLoading(); },
            "Educational Goals Reference Store load never completed",
            4000
        );

    });   
    
    it("Educational Goals Reference should have at least one item",function(){
        expect(educationalGoalsStore.getCount()).toBeGreaterThan(0);
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

describe("reference.js --> Employment Shift Items", function() {
	var employmentShiftsStore = null;

    beforeEach(function(){
    	
        if (!employmentShiftsStore) {
        	employmentShiftsStore = Application.getStore('reference.EmploymentShifts');
        }

        expect(employmentShiftsStore).toBeTruthy();
        
        employmentShiftsStore.load();
       	
        waitsFor(
            function(){ return !employmentShiftsStore.isLoading(); },
            "Employment Shifts Reference Store load never completed",
            4000
        );

    });   
    
    it("Employment Shifts Reference should have at least one item",function(){
        expect(employmentShiftsStore.getCount()).toBeGreaterThan(0);
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

describe("reference.js --> Gender Items", function() {
	var gendersStore = null;

    beforeEach(function(){
    	
        if (!gendersStore) {
        	gendersStore = Application.getStore('reference.Genders');
        }

        expect(gendersStore).toBeTruthy();
        
        gendersStore.load();
       	
        waitsFor(
            function(){ return !gendersStore.isLoading(); },
            "Genders Reference Store load never completed",
            4000
        );

    });   
    
    it("Genders Reference should have at least one item",function(){
        expect(gendersStore.getCount()).toBeGreaterThan(0);
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

describe("reference.js --> States Items", function() {
	var statesStore = null;

    beforeEach(function(){
    	
        if (!statesStore) {
        	statesStore = Application.getStore('reference.States');
        }

        expect(statesStore).toBeTruthy();
        
        statesStore.load();
       	
        waitsFor(
            function(){ return !statesStore.isLoading(); },
            "States Reference Store load never completed",
            4000
        );

    });   
    
    it("States Reference should have at least one item",function(){
        expect(statesStore.getCount()).toBeGreaterThan(0);
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

describe("reference.js --> Yes/No Items", function() {
	var yesNoStore = null;

    beforeEach(function(){
    	
        if (!yesNoStore) {
        	yesNoStore = Application.getStore('reference.YesNo');
        }

        expect(yesNoStore).toBeTruthy();
        
        yesNoStore.load();
       	
        waitsFor(
            function(){ return !yesNoStore.isLoading(); },
            "Yes/No Reference Store load never completed",
            4000
        );

    });   
    
    it("YesNo Reference should have at least one item",function(){
        expect(yesNoStore.getCount()).toBeGreaterThan(0);
    });

});