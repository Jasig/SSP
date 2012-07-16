Ext.define('Ssp.service.AppointmentService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    config: {
    	personAppointmentUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    buildUrl: function( id ){
		var me=this;
    	me.personAppointmentUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAppointment') );
	    me.personAppointmentUrl = me.personAppointmentUrl.replace('{id}', id);	    
    },
    
    getCurrentAppointment: function( personId, callbacks ){
		var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };

	    me.buildUrl( personId );
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.personAppointmentUrl+'/current',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    saveAppointment: function( personId, jsonData, callbacks ){
		var me=this;
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
    	    me.buildUrl( personId );
    		
    		id = jsonData.id;

    		// save the appointment
    		if (id=="")
    		{				
    			me.apiProperties.makeRequest({
        			url: me.appointmentUrl,
        			method: 'POST',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});				
    		}else{
    			// update
        		me.apiProperties.makeRequest({
        			url: me.appointmentUrl+"/"+id,
        			method: 'PUT',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});	
    		}     		
    	}else{
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save an appointment. Unable to save to appointment.');
    	}  	
    }
});