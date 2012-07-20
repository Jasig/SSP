Ext.define('Ssp.service.PersonProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personProgramStatus') );
    	baseUrl = baseUrl.replace("{id}",personId);
		return baseUrl;
    },

    savePersonProgramStatus: function( personId, jsonData, callbacks ){
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
		
    	if (personId != "")
    	{
    		id = jsonData.id;

    		// save the program status
    		if (id=="")
    		{				
    			me.apiProperties.makeRequest({
        			url: url,
        			method: 'POST',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});				
    		}else{
    			// update
        		me.apiProperties.makeRequest({
        			url: url+"/"+id,
        			method: 'PUT',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});	
    		}     		
    	}else{
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save a Program Status. Unable to save Program Status. See your system administrator for assistance.');
    	}  	
    }   
});