Ext.define('Ssp.model.PersonAppointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'},
             {name: 'attended', type: 'boolean'}],  

	setAppointment: function( startDate, endDate ){
		var me=this;
		if (startDate != null && endDate != null)
		{
		   me.set('startTime', startDate);
		   me.set('endTime', endDate);  		
		}
	}

});