Ext.define('Ssp.view.admin.forms.ColorPicker', {
    extend: 'Ext.form.field.Trigger',
    alias: 'widget.sspcolorpicker',

    // override onTriggerClick
    onTriggerClick: function (e) {
        var triggerField = this;
        var colorPicker = Ext.create('Ext.picker.Color', {
            style: {
                backgroundColor: "#fff"
            },
            listeners: {
                select: function (picker, selColor) {
                    triggerField.setValue(selColor);
                    window.close();
                }
            }
        });
        var window = Ext.create('Ext.window.Window', {
            title: 'Select Color',
            resizable: false,
            draggable: false,
            closeAction: 'hide',
            width: 150,
            height: 135,
            border: false,
            hidden: true,
            layout: 'fit',
            floating: true,
            items: [colorPicker]
        });
        window.show();
    }
});