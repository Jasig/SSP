(function() {
  var context, selfHelpGuideId, viewModel;
  context = window.context || (window.context = {});
  context.sessionService || (context.sessionService = new mygps.service.SessionService("1/session"));
  context.taskService || (context.taskService = new mygps.service.TaskService("1/task"));
  context.selfHelpGuideService || (context.selfHelpGuideService = new mygps.service.SelfHelpGuideService("1/selfhelpguide"));
  context.selfHelpGuideResponseService || (context.selfHelpGuideResponseService = new mygps.service.SelfHelpGuideResponseService("1/selfhelpguideresponse"));
  context.challengeReferralService || (context.challengeReferralService = new mygps.service.ChallengeReferralService("1/challengereferral"));
  context.session || (context.session = new mygps.session.Session(context.sessionService));
  selfHelpGuideId = $.parameter("selfHelpGuideId");
  viewModel = new mygps.viewmodel.SelfHelpGuideViewModel(context.session, context.taskService, context.selfHelpGuideService, context.selfHelpGuideResponseService, context.challengeReferralService);
  viewModel.load(selfHelpGuideId);
  window.viewModel = viewModel;
  $('#guide-introduction-page').live('pagecreate', function() {
    var guideIntroductionPage;
    guideIntroductionPage = this;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html"
    }).done(function() {
      ko.applyBindings(viewModel, guideIntroductionPage);
    });
  });
  $('#guide-question-page').live('pagecreate', function() {
    var guideQuestionPage;
    guideQuestionPage = this;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html",
      questionTemplate: "templates/question.html"
    }).done(function() {
      ko.applyBindings(viewModel, guideQuestionPage);
    });
  });
  $('#guide-referrals-page').live('pagecreate', function() {
    var guideReferralsPage;
    guideReferralsPage = this;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html",
      tasksTemplate: "templates/tasks.html",
      taskTemplate: "templates/task.html",
      taskDetailTemplate: "templates/taskdetail.html",
      challengeTemplate: "templates/challenge.html",
      referralTemplate: "templates/referral.html"
    }).done(function() {
      ko.applyBindings(viewModel, guideReferralsPage);
    });
  });
  $('#guide-summary-page').live('pagecreate', function() {
    var guideSummaryPage;
    guideSummaryPage = this;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html"
    }).done(function() {
      ko.applyBindings(viewModel, guideSummaryPage);
    });
  });
}).call(this);
