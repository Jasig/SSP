Ext.define('Ssp.store.Coaches', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Coach',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{coachId: '1', fullName:'Shawn Gormley', emailAddress:'shawn.gormley@sinclair.edu', office: '13023S', phone:'937-512-2293', department: 'Web Systems'},
           {coachId: '2', fullName:'Russ Little', emailAddress:'russ.little@sinclair.edu', office: '13023Q', phone:'937-512-2296', department: 'Web Systems'}]
});