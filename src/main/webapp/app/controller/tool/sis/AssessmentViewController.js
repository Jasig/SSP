Ext.define('Ssp.controller.tool.sis.AssessmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        person: 'currentPerson'
    },
    config: {
    	assessmentUrl: null
    },
	init: function() {
		var me=this;
		var personId = me.person.get('id');
		
		me.assessmentUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAssessment') );
		me.assessmentUrl = me.assessmentUrl.replace('{id}',personId);
		
		me.getAssessments();
		
		return this.callParent(arguments);
    },
    
    getAssessments: function(){
    	var me=this;
    	
    	// hide the loader
    	me.getView().setLoading( true );
    	
    	me.apiProperties.makeRequest({
			url: me.getAssessmentUrl(),
			method: 'GET',
			failure: me.getAssementFailure,
			successFunc: me.getAssessmentSuccess,
			scope: me
		});
    },
    
    getAssessmentSuccess: function( response, view){
    	var me=this;
    	var r = Ext.decode(response.responseText);
    	console.log( 'AssessmentViewController->init->getAssessmentSuccess' );
  	
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if (r != null)
    	{
    		console.log( r );
    	}    	
    },
    
    getAssementFailure: function( response ){
    	var me=this;
    	
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	me.apiProperties.handleError( response );    	
    }
});