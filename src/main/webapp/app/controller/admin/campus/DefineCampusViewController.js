Ext.define('Ssp.controller.admin.campus.DefineCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	panelLayout: null,
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin'
    },
    control: {  	
    	prevBtn: '#prevButton',
    	nextBtn: '#nextButton',
    	finishBtn: '#finishButton',
    	
    	'nextButton': {
			click: 'onNextClick'
		},
		
		'prevButton': {
			click: 'onPrevClick'
		},
		
		'finishButton': {
			click: 'onFinishClick'
		}
    },
	init: function() {
		this.store.load();
		this.panelLayout = this.getView().getLayout();
		this.enableButtons();
		return this.callParent(arguments);
    },

    navigate: function( direction ){
        this.panelLayout[direction]();
        this.enableButtons();
    },
    
    enableButtons: function(){
    	var layout = this.panelLayout;
    	this.getPrevBtn().setDisabled(!layout.getPrev());
        this.getNextBtn().setDisabled(!layout.getNext());
        if (!layout.getNext())
        {
        	this.getFinishBtn().setDisabled(false);
        }else{
        	this.getFinishBtn().setDisabled(true);
        }
    },
    
	onNextClick: function(button) {
		this.navigate("next");
	},
	
	onPrevClick: function(button){
		this.navigate("prev");
	},
	
	onFinishClick: function(button){
		console.log('DefineCampusViewController->onFinishClick');
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});	
	}
});