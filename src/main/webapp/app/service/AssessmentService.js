Ext.define('Ssp.service.AssessmentService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAssessment') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },
    
    getAll: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    }
});