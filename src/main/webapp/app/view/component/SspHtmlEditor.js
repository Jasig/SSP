/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// An Html Editor field that supports the allowBlank property and
// works around encoding problems with the OOTB defaultValue.
//
// Basic code structure and the allowBlank support (SSP-2828) was copy/pasted nearly
// verbatim from
// http://www.sencha.com/forum/showthread.php?283186-HtmlEditor-Validation-if-field-have-blank-value
//
// Key changes from the original are commented on inline.
//
// The defaultValue encoding workaround (override to cleanHtml for SSP-2769) is from
// http://www.sencha.com/forum/showthread.php?73293-FIXED-87-3.0RC3-HTMLEditor-prepends-to-message&p=572631&viewfull=1#post572631
Ext.define('Ssp.view.component.SspHtmlEditor', {
    extend: 'Ext.form.field.HtmlEditor',
    alias: 'widget.ssphtmleditor',
    allowBlank: true,
    blankText: 'This field is required',
    defaultValue: '<!-- Will be removed by the editor -->',
    cleanDefaultValue: true,
    enableFont: false,

    constructor: function () {
        var me = this;
        me.callParent(arguments);
        return me;
    },

    // Copied nearly verbatim from the third-party allowBlank patch, but added our
    // Ssp.util.Util.decorateFormField(this) call else instances of this component won't get our usual
    // required field styling b/c they technically aren't Fields.
    initComponent: function (arguments) {
        var me = this,
            ext = Ext;

        ext.apply(me, me.initialConfig);
        me.callParent(arguments);
        Ssp.util.Util.decorateFormField(this);
    },

    // Not sure why we need this. Was in the third-party allowBlank patch, so kept it.
    initEvents: function () {
        var me = this;
        me.callParent(arguments);
    },

    // Overridden to work around defaultValue encoding problem (SSP-2769)
    cleanHtml: function(html) {
        if(Ext.isWebKit){
            html = html.replace(/\sclass="(?:Apple-style-span|khtml-block-placeholder)"/gi, '');
        }
        if(this.cleanDefaultValue){
            html = html.replace(new RegExp(this.defaultValue), '');
        }
        return html;
    },

    // Critical change to the third-party patch for allowBlank support (SSP-2828).
    // Without this override, the HtmlEditor will be flagged as invalid when a blank model is
    // bound into it because undefined (the value when the component is initialized)
    // and the empty string (the default value of the empty model) will be considered
    // unequal. During the model bind, this will cause Field.checkChange() to call
    // onChange(), which calls validate(), which calls isValid(). Note that the OOTB
    // Text form field component has this override, which is why for SSP-2828 we
    // we not having problems with the required 'Subject' field marking itself invalid
    // on load, but the email body field *was* marking itself invalid on load.
    isEqual: function(value1, value2) {
        return this.isEqualAsString(value1, value2);
    },

    // Preserved verbatim from third-party allowBlank patch, with minor whitespace formatting
    // changes. This is an override of Field.isValid() with a copy/paste of the implementation
    // from Ext.form.field.Base, which Ext.form.field.Text extends. So we get parity with
    // Text behavior, which is good, even though the implementation does have side-effects,
    // i.e. if it returns false, error messages/styling will have been displayed, which
    // violates the contract of Field.isValid() which says:
    //
    //    Implementations are encouraged to ensure that this method does not have side-effects such as triggering error
    //    message display.
    isValid: function () {
        var me = this,
            disabled = me.disabled,
            validate = me.forceValidation || !disabled;

        return validate ? me.validateValue(me.processRawValue(me.getValue())) : disabled;
    },

    // Preserved nearly verbatim from third-party allowBlank patch, with minor whitespace formatting
    // changes and removal of logic which treated nulls and undefineds as valid because it conflicted
    // with logic in getErrors() which treated anything falsy as invalid.
    // Also see comments in isValid() re side-effects.
    validateValue: function (value) {
        var me = this,
            errors = me.getErrors(value),
            isValid = Ext.isEmpty(errors);

        if (!me.preventMark) {
            if (isValid) {
                me.clearInvalid();
            } else {
                me.markInvalid(errors);
            }
        }

        return isValid;
    },

    // Lightly customized version of the same method from the third-party allowBlank patch to integrate the
    // latter with our defaultValue encoding patch. Here we strip that defaultValue out of the given
    // value before stripping anything else out, especially whitespace (which, if stripped first, would prevent
    // matching and stripping defaultValue). Also broadened the whitespace stripping to match any whitespace as
    // defined by \s. Original just stripped spaces. And guards against null/undefined.
    processRawValue: function (value) {
        if ( value === null || value === undefined ) {
            return value;
        }
        var ret = value;

        ret = ret.replace(new RegExp(this.defaultValue), '');
        ret = ret.replace('<br>', '');
        ret = ret.replace('<br/>', '');
        ret = ret.replace('<p>', '');
        ret = ret.replace('</p>', '');
        ret = ret.replace(/\s/g, '');

        return ret;
    },

    // Lightly customized version of the same method from the third-party allowBlank patch to respect
    // the disabled and forceValidation flags. Also narrowed the allowBlank tests to allow falsy values
    // except for null, undefined, and the empty string. Also delegates to parent impl to get the initial
    // errors collection.
    getErrors: function (value) {
        var me = this,
            errors = me.callParent(arguments),
            disabled = me.disabled,
            validate = me.forceValidation || !disabled;

        if ( validate ) {
            if (!(this.allowBlank) && me.isEqual(value, '')) { // NB isEqual is overridden above
                errors.push(this.blankText);
            }
        }
        return errors;
    },

    // Unmodified from third-party allowBlank patch except for formatting. Orig was a near copy/paste
    // of Base.markInvalid(). Not sure if differences, were intentional or a result of changes in
    // later versions of Ext.js. They don't have a functional impact that I can detect, though.
    markInvalid: function (errors) {
        var me = this,
            oldMsg = me.getActiveError();
        me.setActiveErrors(Ext.Array.from(errors));
        if (oldMsg !== me.getActiveError()) {
            me.updateLayout();
        }
    },

    // Unmodified from third-party allowBlank patch except for formatting. Orig was a near copy/paste
    // of Base.renderActiveError().
    renderActiveError: function () {
        var me = this,
            hasError = me.hasActiveError();
        if (me.inputEl) {
            // Add/remove invalid class
            me.bodyEl[hasError ? 'addCls' : 'removeCls'](me.invalidCls + '-field');
        }
        me.mixins.labelable.renderActiveError.call(me);
    },

    // Unmodified from third-party allowBlank patch except for formatting. Orig was a near copy/paste
    // of Base.clearInvalid(). Not sure if differences, which have to do with deleting 'me.needsValidateOnEnable' were
    // intentional or a result of changes in later versions of Ext.js.
    clearInvalid: function () {
        var me = this,
            hadError = me.hasActiveError();
        me.unsetActiveError();
        if (hadError) {
            me.updateLayout();
        }
    }
});
