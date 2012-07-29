Ext.define('Ssp.controller.tool.sis.TranscriptViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

    	// hide the loader
    	me.getView().setLoading( true );
    	
		me.service.getAll( personId, {
			success: me.getTranscriptSuccess,
			failure: me.getTranscriptFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getTranscriptSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    	
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});