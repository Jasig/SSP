(function() {
  var context;

  context = window.context || (window.context = {});

  context.sessionService || (context.sessionService = new mygps.service.SessionService("../api/mygps/session"));

  context.studentIntakeService || (context.studentIntakeService = new mygps.service.StudentIntakeService("../api/mygps/intake"));

  context.session || (context.session = new mygps.session.Session(context.sessionService));

  $('#intake-page').live('pagecreate', function(event) {
    var intakePage, viewModel;
    intakePage = this;
    viewModel = new mygps.viewmodel.StudentIntakeViewModel(context.session, context.studentIntakeService);
    window.viewModel = viewModel;
    $("body").loadTemplates({
      bannerTemplate: "templates/banner.html",
      footerTemplate: "templates/footer.html",
      sectionTemplate: "templates/form/section.html",
      agreementTemplate: "templates/form/question/agreement.html",
      textareaTemplate: "templates/form/question/textarea.html",
      textinputTemplate: "templates/form/question/textinput.html",
      selectTemplate: "templates/form/question/select.html",
      checklistTemplate: "templates/form/question/checklist.html",
      radiolistTemplate: "templates/form/question/radiolist.html",
      selectOptionTemplate: "templates/form/question/option/select.html",
      checkOptionTemplate: "templates/form/question/option/check.html",
      radioOptionTemplate: "templates/form/question/option/radio.html"
    }).done(function() {
      ko.applyBindings(viewModel, intakePage);
    });
    $('#intake-page').live('pagebeforeshow', function() {
      window.viewModel = viewModel;
      viewModel.load();
    });
  });

}).call(this);
