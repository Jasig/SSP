Ext.define('Ssp.controller.tool.sis.TranscriptViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        person: 'currentPerson'
    },
    config: {
    	transcriptUrl: null
    },
	init: function() {
		var me=this;
		var personId = me.person.get('id');
		
		me.transcriptUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTranscript') );
		me.transcriptUrl = me.transcriptUrl.replace('{id}',personId);
		
		me.getTranscript();
		
		return this.callParent(arguments);
    },
    
    getTranscript: function(){
    	var me=this;
    	
    	// hide the loader
    	me.getView().setLoading( true );
    	
    	me.apiProperties.makeRequest({
			url: me.getTranscriptUrl()+'/full',
			method: 'GET',
			failure: me.getTranscriptFailure,
			successFunc: me.getTranscriptSuccess,
			scope: me
		});
    },
    
    getTranscriptSuccess: function( response, view){
    	var me=this;
    	var r = Ext.decode(response.responseText);
    	console.log( 'TranscriptViewController->init->getTranscriptSuccess' );
  	
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if (r != null)
    	{
    		console.log( r );
    	}    	
    },
    
    getTranscriptFailure: function( response ){
    	var me=this;
    	
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	me.apiProperties.handleError( response );    	
    }
});