Ext.define('Ssp.util.MyDatePicker', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.mydatefield',

    initComponent : function(){
        this.callParent(arguments);
    },

    createPicker: function() {
//        var picker = this.callParent(arguments);
//        picker.todayText = 'foo';
//        return picker;
        var me = this,
            format = Ext.String.format;

        var picker = new Ext.picker.Date({
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            todayText:'foo',
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
      console.log('cells: ', picker.cells);
        var cLen = picker.cells.elements.length;
        console.log("cLen: ", cLen);
        for (c = 0; c < cLen; c++) {
            console.log('title: ', picker.cells.elements[c].title);
            picker.cells.elements[c].title = 'foo'+c; // this works, but you gotta override "fullUpdate" for this to work. else swicthing monhs will break it
        }
        return picker;
    }
});