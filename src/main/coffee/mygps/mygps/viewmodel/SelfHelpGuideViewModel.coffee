namespace 'mygps.viewmodel'

	SelfHelpGuideViewModel:
		
		class SelfHelpGuideViewModel extends mygps.viewmodel.AbstractTasksViewModel
			
			constructor: ( session, taskService, selfHelpGuideService, selfHelpGuideResponseService, challengeReferralService ) ->
				super( session, taskService )
				@selfHelpGuideService = selfHelpGuideService
				@selfHelpGuideResponseService = selfHelpGuideResponseService
				@challengeReferralService = challengeReferralService
				
				@selfHelpGuideContent = ko.observable( null )
				@selfHelpGuideResponseId = ko.observable( null )
				@selfHelpGuideResponse = ko.observable( null )
				@name = ko.dependentObservable( @evaluateName, this )
				@introductoryText = ko.dependentObservable( @evaluateIntroductoryText, this )
				@currentQuestionIndex = ko.observable( 0 )
				@currentQuestion = ko.dependentObservable( @evaluateCurrentQuestion, this )
				@hasPreviousQuestion = ko.dependentObservable( @evaluateHasPreviousQuestion, this )
				@hasNextQuestion = ko.dependentObservable( @evaluateHasNextQuestion, this )
				@canSkipQuestion = ko.dependentObservable( @evaluateCanSkipQuestion, this )
				@progressText = ko.dependentObservable( @evaluateProgressText, this )
				@challenges = ko.dependentObservable( @evaluateChallenges, this )
				@selectedChallenge = ko.observable( null )
				@selectedChallengeName = ko.dependentObservable( @evaluateSelectedChallengeName, this )
				@referrals  = ko.dependentObservable( @evaluateReferrals, this )
				@filteredReferrals = ko.dependentObservable( @filterReferrals, this )
				@summaryText = ko.dependentObservable( @evaluateSummaryText, this )
			
			load: ( selfHelpGuideId ) ->
				super()
				@initiateSelfHelpGuideResponse( selfHelpGuideId )
				return
				
			#createTaskForChallengeReferral: ( challenge, challengeReferral ) ->
			#	super( challenge, challengeReferral )
			#	@refresh()
			#	return


			createTaskForChallengeReferral: ( challenge, challengeReferral ) ->
				super(
					challenge, 
					challengeReferral, 
					result: ( result ) =>
						@refresh()
					fault: ( fault ) =>
						alert( fault.responseText )
				)				
				
				return

			
			markTask: ( task, complete ) ->
				super( task, complete )
				@refresh()
				return
				
			deleteTask: ( task ) ->
				super( task )
				@refresh()
				return
			
			evaluateName: () ->
				return @selfHelpGuideContent()?.name();
			
			evaluateIntroductoryText: () ->
				return @selfHelpGuideContent()?.introductoryText();
			
			evaluateCurrentQuestion: () ->
				return @selfHelpGuideContent()?.questions()?[ @currentQuestionIndex() ]
			
			evaluateHasPreviousQuestion: () ->
				if @selfHelpGuideContent()?
					return @currentQuestionIndex() > 0
				return false
			
			evaluateHasNextQuestion: () ->
				if @selfHelpGuideContent()?
					return @currentQuestionIndex() < @selfHelpGuideContent().questions().length - 1
				return false
			
			evaluateCanSkipQuestion: () ->
				if  @currentQuestion()?
					return not @currentQuestion().mandatory()
				return true
			
			evaluateProgressText: () ->
				if @selfHelpGuideContent()?
					return "#{ @currentQuestionIndex() + 1 } of #{ @selfHelpGuideContent().questions().length }" 
				else
					return ""
			
			evaluateChallenges: () ->
				return @selfHelpGuideResponse()?.challengesIdentified() or []
			
			evaluateSelectedChallengeName: () ->
				return @selectedChallenge()?.name()
			
			evaluateReferrals: () ->
				return @selectedChallenge()?.challengeReferrals() or []
				
			evaluateSummaryText: () ->
				return @selfHelpGuideResponse()?.summaryText()
			
			filterReferrals: () ->
				return _.select(
					@referrals(),
					( referral ) =>
						return not _.any( 
							@tasks(), 
							( task ) ->
								return task.challengeReferralId() is referral.id() and not task.completed()
						)
				)
			
			loadSelfHelpGuideContent: ( selfHelpGuideId ) ->
				@selfHelpGuideService.getContentById(
					selfHelpGuideId,
					result: ( result ) =>
						@selfHelpGuideContent( result )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			initiateSelfHelpGuideResponse: ( selfHelpGuideId ) ->
				@selfHelpGuideResponseService.initiate(
					selfHelpGuideId,
					result: ( result ) =>
						@selfHelpGuideResponseId( result )
						@loadSelfHelpGuideContent( selfHelpGuideId )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			answerQuestion: ( selfHelpGuideQuestion, response ) ->
				@selfHelpGuideResponseService.answer(
					@selfHelpGuideResponseId(),
					selfHelpGuideQuestion.id(),
					response,
					result: ( result ) =>
						selfHelpGuideQuestion.response( response )
						@moveToNextQuestion()
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			skipToNextQuestion: () ->	
				if not @currentQuestion().mandatory()
					@moveToNextQuestion()
				
			moveToPreviousQuestion: () ->
				if @hasPreviousQuestion()
					@currentQuestionIndex( @currentQuestionIndex() - 1 )
				return
				
			moveToNextQuestion: () ->
				if ( @hasNextQuestion() )
					@currentQuestionIndex( @currentQuestionIndex() + 1 )
				else
					@complete()
				return
				
			cancel: () ->
				@selfHelpGuideResponseService.cancel(
					@selfHelpGuideResponse().id(),
					result: ( result ) =>
						@selfHelpGuideResponse().cancelled( true )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			complete: () ->
				@selfHelpGuideResponseService.complete(
					@selfHelpGuideResponseId(),
					result: ( result ) =>
						@selfHelpGuideResponseService.getById(
							@selfHelpGuideResponseId(),
							result: ( result ) =>
								@selfHelpGuideResponse( result )
							fault: ( fault ) =>
								alert( fault.responseText )
						)
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			refresh: () ->
				@selfHelpGuideResponseService.getById(
					@selfHelpGuideResponseId(),
					result: ( result ) =>
						@selfHelpGuideResponse( result )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				
			selectChallenge: ( challenge ) ->
				@selectedChallenge( challenge )
				@challengeReferralService.getByChallengeId(
					challenge.id(),
					result: ( result ) =>
						challenge.challengeReferrals( result )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
