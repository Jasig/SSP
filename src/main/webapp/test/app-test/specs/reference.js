describe("Reference Items", function() {
    var challengesStore = null;
    var childCareArrangementsStore = null;
    var citizenshipsStore = null;
    var educationalGoalsStore = null;
    var educationLevelsStore = null;
    var employmentShiftsStore = null;
    var ethnicitiesStore = null;
    var fundingSourcesStore = null;
    var gendersStore = null;
    var maritalStatusesStore = null;
    var statesStore = null;
    var studentStatusesStore = null;
    var veteranStatusesStore = null;
    var yesNoStore = null;
    
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


    it("Challenge Reference should have model.reference.ChallengeTOs",function(){
        expect(challengesStore.getCount()).toBeGreaterThan(1);
    });

});