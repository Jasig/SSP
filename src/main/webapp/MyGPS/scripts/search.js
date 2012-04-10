(function() {
  var context;

  context = window.context || (window.context = {});

  context.sessionService || (context.sessionService = new mygps.service.SessionService("../api/mygps/session"));

  context.taskService || (context.taskService = new mygps.service.TaskService("../api/mygps/task"));

  context.challengeService || (context.challengeService = new mygps.service.ChallengeService("../api/mygps/challenge"));

  context.challengeReferralService || (context.challengeReferralService = new mygps.service.ChallengeReferralService("../api/mygps/challengereferral"));

  context.session || (context.session = new mygps.session.Session(context.sessionService));

  $('#search-page').live('pagecreate', function() {
    var searchPage, viewModel;
    searchPage = this;
    viewModel = new mygps.viewmodel.ChallengeReferralSearchViewModel(context.session, context.taskService, context.challengeService, context.challengeReferralService);
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html",
      tasksTemplate: "templates/tasks.html",
      taskTemplate: "templates/task.html",
      taskDetailTemplate: "templates/taskdetail.html",
      customTaskTemplate: "templates/customtask.html",
      challengeTemplate: "templates/challenge.html",
      referralTemplate: "templates/referral.html"
    }).done(function() {
      ko.applyBindings(viewModel, searchPage);
    });
    $('#search-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load();
    });
  });

}).call(this);
