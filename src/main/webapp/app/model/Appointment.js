Ext.define('Ssp.model.Appointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'appointmentDate', type: 'date', dateFormat: 'time'},
             {name: 'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'}],        
             
    getStartDate: function(){
		var me=this;
    	var startDate = Ext.Date.clearTime( me.get('appointmentDate'), true );
    	startDate.setHours( me.get('startTime').getHours() );
		startDate.setMinutes( me.get('startTime').getMinutes() );
		return startDate;
    },
    
    getEndDate: function(){
    	var me=this;
    	var endDate = Ext.Date.clearTime( me.get('appointmentDate'), true );
		endDate.setHours( me.get('endTime').getHours() );
		endDate.setMinutes( me.get('endTime').getMinutes() );
		return endDate;    	
    }
});