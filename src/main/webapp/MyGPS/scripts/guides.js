(function() {
  var $, context, selfHelpGuideGroupId;

  $ = jQuery;

  context = window.context || (window.context = {});

  context.sessionService || (context.sessionService = new mygps.service.SessionService("../api/1/session"));

  context.selfHelpGuideService || (context.selfHelpGuideService = new mygps.service.SelfHelpGuideService("../api/1/mygps/selfhelpguide"));

  context.session || (context.session = new mygps.session.Session(context.sessionService));

  selfHelpGuideGroupId = $.parameter("selfHelpGuideGroupId");

  $('#guides-page').live('pagecreate', function() {
    var guidesPage, viewModel;
    guidesPage = this;
    viewModel = new mygps.viewmodel.SelfHelpGuidesViewModel(context.session, context.selfHelpGuideService);
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "/ssp/MyGPS/templates/banner.html",
      footerTemplate: "/ssp/MyGPS/templates/footer.html",
      guideTemplate: "/ssp/MyGPS/templates/guide.html"
    }).done(function() {
      ko.applyBindings(viewModel, guidesPage);
    });
    $('#guides-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load(selfHelpGuideGroupId);
    });
  });

}).call(this);
