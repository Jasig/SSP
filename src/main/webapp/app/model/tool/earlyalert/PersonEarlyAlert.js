Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlert', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'courseName', type:'string'},
             {name:'courseTitle', type:'string'},
             {name:'emailCC', type:'string'},
             {name:'campusId', type:'string'},
             {name:'earlyAlertReasonIds', type:'auto'},
             {name:'earlyAlertReasonOtherDescription', type:'string'},
             {name:'earlyAlertSuggestionIds', type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription', type:'string'},
             {name:'comment', type:'string'},
             {name:'closedDate', type: 'date', dateFormat: 'time'},
             {name:'closedById', type:'string'}]
});