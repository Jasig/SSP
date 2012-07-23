Ext.define('Ssp.model.Appointment', {
    extend: 'Ssp.model.AbstractBase',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	formUtils: 'formRendererUtils'
    },    
    fields: [{name:'appointmentDate', type: 'date', dateFormat: 'time'},
             {name: 'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'}],        
             
    getStartDate: function(){
		var me=this;
    	var startDate = me.formUtils.fixDateOffset( me.get('appointmentDate'), true );
    	startDate.setHours( me.get('startTime').getHours() );
		startDate.setMinutes( me.get('startTime').getMinutes() );
		return me.formUtils.fixDateOffsetWithTime( startDate );
    },
    
    getEndDate: function(){
    	var me=this;
    	var endDate = me.formUtils.fixDateOffset( me.get('appointmentDate'), true );
		endDate.setHours( me.get('endTime').getHours() );
		endDate.setMinutes( me.get('endTime').getMinutes() );
		return me.formUtils.fixDateOffsetWithTime( endDate );    	
    }
});