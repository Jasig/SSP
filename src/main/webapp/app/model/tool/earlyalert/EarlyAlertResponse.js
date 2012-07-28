Ext.define('Ssp.model.tool.earlyalert.EarlyAlertResponse', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});