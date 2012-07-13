Ext.define('Ssp.controller.tool.profile.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	person: 'currentPerson'
    },
    config: {
    	personViewHistoryUrl: '',
    },
    control: {
    	'viewHistoryButton': {
			click: 'onViewHistoryClick'
		},
		
		'studentTransitionButton': {
			click: 'onStudentTransitionClick'
		}
    },
	init: function() {
		var me=this;
		var personId = me.person.get('id');
		me.personViewHistoryUrl = me.apiProperties.getItemUrl('personViewHistory');
		me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
		
		return this.callParent(arguments);
    },
    
    onViewHistoryClick: function(button){
		var me=this;
		me.apiProperties.getReporter().load({
  		  url: me.personViewHistoryUrl,
  		  params: ""
  		});
    },

    onStudentTransitionClick: function(button){
      	 Ext.Msg.alert('Attention','This feature is not yet active.');
    },
});