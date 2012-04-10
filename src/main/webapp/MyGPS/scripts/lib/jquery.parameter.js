/*
 * jquery.parameter v0.5.0
 * Copyright (c) 2011 CodeCatalyst, LLC.
 * Open source under the MIT License.
 */
(function() {
  $.parameter = function(name) {
    var matches;
    matches = new RegExp("[\\?&]" + name + "=([^&#]*)").exec(window.location.href);
    return matches != null ? matches[1] : void 0;
  };
}).call(this);
