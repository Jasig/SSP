Ext.define('Ssp.controller.tool.profile.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	personLite: 'personLite'
    },
    config: {
    	personViewHistoryUrl: '',
    	printConfidentialityAgreementUrl: ''
    },
    control: {
    	'viewHistoryButton': {
			click: 'onViewHistoryClick'
		},
		
		'studentTransitionButton': {
			click: 'onStudentTransitionClick'
		},
		
		'printConfidentialityAgreementButton': {
			click: 'printConfidentialityAgreement'
		}
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');
		me.personViewHistoryUrl = me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory');
		me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
		me.printConfidentialityAgreementUrl = me.apiProperties.getContext() + me.apiProperties.getItemUrl('printConfidentialityDisclosureAgreement');
		return this.callParent(arguments);
    },
    
	printConfidentialityAgreement: function(button){
		var me=this;
		me.apiProperties.getReporter().loadBlankReport( me.printConfidentialityAgreementUrl );
	},   
    
    onViewHistoryClick: function(button){
		var me=this;
		me.apiProperties.getReporter().load({
			url:me.personViewHistoryUrl,
			params: ""
		});
    },

    onStudentTransitionClick: function(button){
      	 Ext.Msg.alert('Attention','This feature is not yet active.');
    },
});