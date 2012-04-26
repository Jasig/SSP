(function() {
  var $, context;

  $ = jQuery;

  context = window.context || (window.context = {});

  $('#login-page').live('pagecreate', function(event) {
    var loginPage, viewModel;
    loginPage = this;
    viewModel = new mygps.viewmodel.LoginViewModel();
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "/ssp/MyGPS/templates/banner.html",
      footerTemplate: "/ssp/MyGPS/templates/footer.html"
    }).done(function() {
      ko.applyBindings(viewModel, loginPage);
    });
    $('#login-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load();
    });
    $('#login-page').live('pageshow', function() {
      $('#login-page').ready(function() {
        return $('#j_username').focus();
      });
    });
  });

}).call(this);
