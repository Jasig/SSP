(function() {
  var context;

  context = window.context || (window.context = {});

  context.sessionService || (context.sessionService = new mygps.service.SessionService("../api/session"));

  context.messageService || (context.messageService = new mygps.service.MessageService("../api/mygps/message"));

  context.session || (context.session = new mygps.session.Session(context.sessionService));

  $('#contact-page').live('pagecreate', function() {
    var contactPage, viewModel;
    contactPage = this;
    viewModel = new mygps.viewmodel.ContactViewModel(context.session, context.messageService);
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html"
    }).done(function() {
      ko.applyBindings(viewModel, contactPage);
    });
    $('#contact-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load();
    });
  });

}).call(this);
