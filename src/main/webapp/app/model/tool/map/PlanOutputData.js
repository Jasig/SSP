Ext.define('Ssp.model.tool.map.PlanOutputData', {
	extend: 'Ext.data.Model',
    fields: [{name:'outputFormat', type:'string'},
             {name:'includeCourseDescription', type:'boolean'},
             {name:'includeHeaderFooter', type:'boolean'},
             {name: 'includeTotalTimeExpected', type: 'boolean'},
             {name: 'includeFinancialAidInformation', type: 'boolean'},             
             {name:'emailTo', type:'string'},
             {name:'emailCC', type:'string'},
             {name:'notes', type:'string'},
             {name:'plan', type:'auto'}
             ]
});