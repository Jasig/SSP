/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
 // Generated by CoffeeScript 1.4.0
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
