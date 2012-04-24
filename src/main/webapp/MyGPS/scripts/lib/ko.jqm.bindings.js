/*
 * ko.jqm.bindings v1.0.2
 * Copyright (c) 2011 CodeCatalyst, LLC.
 * Open source under the MIT License.
 */
(function() {
  var checkedBindingInitHandler, checkedBindingUpdateHandler, enableBindingUpdateHandler, keepNative, refreshElement, templateBindingUpdateHandler, valueBindingUpdateHandler;
  keepNative = ":jqmData(role='none'), :jqmData(role='nojs')";
  refreshElement = function(element, method) {
    var $element;
    $element = $(element);
    if (!$element.is(keepNative)) {
      try {
        return $element[method]("refresh");
      } catch (error) {

      }
    }
  };
  valueBindingUpdateHandler = ko.bindingHandlers['value']['update'];
  ko.bindingHandlers['value']['update'] = function(element, valueAccessor) {
    valueBindingUpdateHandler(element, valueAccessor);
    if (element.tagName === "SELECT") {
      refreshElement(element, "selectmenu");
    } else if (element.type === "range") {
      refreshElement(element, "slider");
    }
  };
  checkedBindingInitHandler = ko.bindingHandlers['checked']['init'];
  ko.bindingHandlers['checked']['init'] = function(element, valueAccessor) {
    var updateHandler;
    checkedBindingInitHandler(element, valueAccessor);
    updateHandler = function() {
      var allBindings, existingEntryIndex, modelValue, valueToWrite;
      if (element.type === "checkbox") {
        valueToWrite = element.checked;
      } else if ((element.type === "radio") && element.checked) {
        valueToWrite = element.value;
      } else {
        return;
      }
      modelValue = valueAccessor();
      if ((element.type === "checkbox") && (ko.utils.unwrapObservable(modelValue) instanceof Array)) {
        existingEntryIndex = ko.utils.arrayIndexOf(ko.utils.unwrapObservable(modelValue), element.value);
        if (element.checked && (existingEntryIndex < 0)) {
          return modelValue.push(element.value);
        } else if ((!element.checked) && (existingEntryIndex >= 0)) {
          return modelValue.splice(existingEntryIndex, 1);
        }
      } else if (ko.isWriteableObservable(modelValue)) {
        if (modelValue() !== valueToWrite) {
          return modelValue(valueToWrite);
        }
      } else {
        allBindings = allBindingsAccessor();
        if (allBindings["_ko_property_writers"] && allBindings["_ko_property_writers"]["checked"]) {
          return allBindings["_ko_property_writers"]["checked"](valueToWrite);
        }
      }
    };
    if (!$(element).is(keepNative)) {
      ko.utils.registerEventHandler(element, "change", updateHandler);
    }
  };
  checkedBindingUpdateHandler = ko.bindingHandlers['checked']['update'];
  ko.bindingHandlers['checked']['update'] = function(element, valueAccessor) {
    checkedBindingUpdateHandler(element, valueAccessor);
    if (element.type === "radio" || element.type === "checkbox") {
      refreshElement(element, "checkboxradio");
    }
  };
  enableBindingUpdateHandler = ko.bindingHandlers['enable']['update'];
  ko.bindingHandlers['enable']['update'] = function(element, valueAccessor) {
    enableBindingUpdateHandler(element, valueAccessor);
    if (element.tagName === "SELECT") {
      refreshElement(element, "selectmenu");
    } else {
      switch (element.type) {
        case "checkbox":
        case "radio":
          refreshElement(element, "checkboxradio");
          break;
        case "range":
          refreshElement(element, "slider");
      }
    }
  };
  templateBindingUpdateHandler = ko.bindingHandlers['template']['update'];
  ko.bindingHandlers['template']['update'] = function(element, valueAccessor, allBindingsAccessor, viewModel) {
    var previousTemplateSubscription, refreshTemplate, renderTemplateSubscription, renderTemplateSubscriptionDomDataKey, templateSubscription, templateSubscriptionDomDataKey;
    refreshTemplate = function() {
      var $element;
      $element = $(element);
      if ($element.jqmData("role") === "listview") {
        refreshElement(element, "listview");
      }
      if ($element.closest('html').length > 0) {
        $element.trigger('create');
      }
    };
    templateSubscriptionDomDataKey = '__ko__templateSubscriptionDomDataKey__';
    renderTemplateSubscriptionDomDataKey = '__kojqm__renderTemplateSubscriptionDomDataKey__';
    previousTemplateSubscription = ko.utils.domData.get(element, renderTemplateSubscriptionDomDataKey);
    if (previousTemplateSubscription != null) {
      previousTemplateSubscription.dispose();
    }
    templateBindingUpdateHandler(element, valueAccessor, allBindingsAccessor, viewModel);
    refreshTemplate();
    templateSubscription = ko.utils.domData.get(element, templateSubscriptionDomDataKey);
    if (templateSubscription != null) {
      renderTemplateSubscription = templateSubscription.subscribe(refreshTemplate);
    }
    ko.utils.domData.set(element, renderTemplateSubscriptionDomDataKey, renderTemplateSubscription);
  };
  ko.utils.setDomNodeChildren;
}).call(this);
