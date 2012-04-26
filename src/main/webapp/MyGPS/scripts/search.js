(function() {
  var $, context;

  $ = jQuery;

  context = window.context || (window.context = {});

  context.sessionService || (context.sessionService = new mygps.service.SessionService("../api/1/session"));

  context.taskService || (context.taskService = new mygps.service.TaskService("../api/1/mygps/task"));

  context.challengeService || (context.challengeService = new mygps.service.ChallengeService("../api/1/mygps/challenge"));

  context.challengeReferralService || (context.challengeReferralService = new mygps.service.ChallengeReferralService("../api/1/mygps/challengereferral"));

  context.session || (context.session = new mygps.session.Session(context.sessionService));

  $('#search-page').live('pagecreate', function() {
    var searchPage, viewModel;
    searchPage = this;
    viewModel = new mygps.viewmodel.ChallengeReferralSearchViewModel(context.session, context.taskService, context.challengeService, context.challengeReferralService);
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "/ssp/MyGPS/templates/banner.html",
      footerTemplate: "/ssp/MyGPS/templates/footer.html",
      tasksTemplate: "/ssp/MyGPS/templates/tasks.html",
      taskTemplate: "/ssp/MyGPS/templates/task.html",
      taskDetailTemplate: "/ssp/MyGPS/templates/taskdetail.html",
      customTaskTemplate: "/ssp/MyGPS/templates/customtask.html",
      challengeTemplate: "/ssp/MyGPS/templates/challenge.html",
      referralTemplate: "/ssp/MyGPS/templates/referral.html"
    }).done(function() {
      ko.applyBindings(viewModel, searchPage);
    });
    $('#search-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load();
    });
  });

}).call(this);
