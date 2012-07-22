Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlertTree', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'courseName',type:'string'},
             {name:'courseTitle',type:'string'},
             {name:'emailCC',type:'string'},
             {name:'campusId',type:'string'},
             {name:'earlyAlertReasonId',type:'string'},
             {name:'earlyAlertReasonOtherDescription',type:'string'},
             {name:'earlyAlertSuggestionIds',type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription',type:'string'},
             {name:'comment',type:'string'},
             {name:'closedDate',type:'time'},
             {name:'closedById',type:'string'},
             
             /* props for tree manipulation */
             {name:'iconCls',type:'string'},
             {name:'leaf',type:'boolean',defaultValue: false},
             {name:'expanded',type:'boolean'},
             {name:'text',type: 'string'},
             {name:'nodeType',type:'string',defaultValue:'early alert'},
             {name:'gridDisplayDetails', type:'string'},
             /* end props for tree manipulation */
             
             {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});