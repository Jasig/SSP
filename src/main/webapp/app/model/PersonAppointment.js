Ext.define('Ssp.model.PersonAppointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'startDate', type: 'date', dateFormat: 'time'},
             {name: 'endDate', type: 'date', dateFormat: 'time'}],  

	setAppointment: function( startDate, endDate ){
		var me=this;
		if (startDate != null && endDate != null)
		{
		   me.set('startDate', startDate);
		   me.set('endDate', endDate);  		
		}
	}

});