Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlertTree', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'courseName',type:'string'},
             {name:'courseTitle',type:'string'},
             {name:'emailCC',type:'string'},
             {name:'campusId',type:'string'},
             {name:'earlyAlertReasonIds',type:'auto'},
             {name:'earlyAlertReasonOtherDescription',type:'string'},
             {name:'earlyAlertSuggestionIds',type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription',type:'string'},
             {name:'comment',type:'string'},
             {name:'closedDate',type:'time'},
             {name:'closedById',type:'string'},
             /*{name:'iconCls',type:'string',defaultValue:'iconFolder'},*/
             {name:'leaf',type:'boolean',defaultValue: false},
             {name:'text',type: 'string'},
             {name:'nodeType',type:'string',defaultValue:'early alert'},
             {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});