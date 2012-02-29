describe("Search Results", function() {
    var studentstore = null;

    beforeEach(function(){
    	
        if (!studentstore) {
            studentstore = Application.getStore('Students');
        }

        expect(studentstore).toBeTruthy();
        
        studentstore.load();

        waitsFor(
            function(){ return !studentstore.isLoading(); },
            "load never completed",
            4000
        );
    });

    it("Search Results Store should have model.StudentTOs",function(){
        expect(studentstore.getCount()).toBeGreaterThan(1);
    });

});