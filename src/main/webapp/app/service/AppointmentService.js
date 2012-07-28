Ext.define('Ssp.service.AppointmentService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appointment: 'currentAppointment',
    	currentPersonAppointment: 'currentPersonAppointment'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAppointment') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },
    
    getCurrentAppointment: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
		var appointment = new Ssp.model.Appointment();
		var personAppointment = new Ssp.model.PersonAppointment();
	    var success = function( response, view ){
	    	var r;
	    	if (response.responseText != "")
	    	{
	    	   r = Ext.decode(response.responseText);
		   		if (r != null)
		   		{
		   			me.currentPersonAppointment.populateFromGenericObject(r);
		   			
		   			if (me.currentPersonAppointment.get('id') != "")
		   			{
		   				me.appointment.populateFromGenericObject({
		   				   "id": me.currentPersonAppointment.get('id'),
		   				   "appointmentDate": Ext.Date.clearTime(me.currentPersonAppointment.get('startTime'), true),
		   				   "startTime": me.currentPersonAppointment.get('startTime').getTime(),
		   				   "endTime": me.currentPersonAppointment.get('endTime').getTime()
		   			   });
		   			}
		   		}
	    	}
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
	    // reset the appointments
	    me.appointment.data = appointment.data;
	    me.currentPersonAppointment.data = personAppointment.data;
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: url + '/current',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    saveAppointment: function( personId, jsonData, callbacks ){
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
    		
    		// save the appointment
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
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save an appointment. Unable to save to appointment.');
    	}  	
    }
});