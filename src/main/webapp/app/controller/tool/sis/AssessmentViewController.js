Ext.define('Ssp.controller.tool.sis.AssessmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'assessmentService',
        personLite: 'personLite'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

    	// hide the loader
    	me.getView().setLoading( true );
    	
		me.service.getAll( personId, {
			success: me.getAssessmentSuccess,
			failure: me.getAssementFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getAssessmentSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    	
    },
    
    getAssementFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});