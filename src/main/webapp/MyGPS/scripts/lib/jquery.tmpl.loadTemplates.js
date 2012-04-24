/*
 * jquery.tmpl.loadTemplates v1.2.0
 * Copyright (c) 2011 CodeCatalyst, LLC.
 * Open source under the MIT License.
 */
(function($) {
  var TEMPLATE_NAME_EXPRESSION;
  TEMPLATE_NAME_EXPRESSION = /\/*([\w]*).html$/;
  $.extend({
    loadTemplates: function(templates, templateProcessorCallback) {
      var deferred, loadedTemplates, templateCount;
      if (templateProcessorCallback == null) {
        templateProcessorCallback = null;
      }
      if (typeof templates === "string") {
        templates = [templates];
      }
      deferred = new $.Deferred();
      templateCount = 0;
      loadedTemplates = [];
      $.each(templates, function(templateName, template) {
        templateCount++;
        if (typeof templateName === "number") {
          templateName = TEMPLATE_NAME_EXPRESSION.exec(template)[1];
        }
        return $.get(template, function(templateContent) {
          if (templateProcessorCallback != null) {
            templateContent = templateProcessorCallback(templateName, templateContent);
          }
          if (templateContent != null) {
            return $.template(templateName, templateContent);
          }
        }, "html").success(function() {
          loadedTemplates.push(template);
          if (loadedTemplates.length === templateCount) {
            return deferred.resolve();
          }
        }).error(function(error) {
          return deferred.reject(error);
        });
      });
      return deferred.promise();
    }
  });
  $.fn.loadTemplates = function(templates, templateProcessorCallback, compile) {
    var promise, selectedElement;
    if (templateProcessorCallback == null) {
      templateProcessorCallback = null;
    }
    if (compile == null) {
      compile = false;
    }
    selectedElement = this;
    promise = $.loadTemplates(templates, function(templateName, templateContent) {
      if (templateProcessorCallback != null) {
        templateContent = templateProcessorCallback(templateName, templateContent);
      }
      if ((templateContent != null) && (document.getElementById(templateName) == null)) {
        selectedElement.append("<script id=\"" + templateName + "\" type=\"text/x-jquery-tmpl\">" + templateContent + "</script>");
      }
      if (compile) {
        return templateContent;
      } else {
        return null;
      }
    });
    return promise;
  };
}).call(this, jQuery);
