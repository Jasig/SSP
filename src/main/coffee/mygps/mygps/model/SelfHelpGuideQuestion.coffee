#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

namespace 'mygps.model'

	SelfHelpGuideQuestion:
	
		class SelfHelpGuideQuestion
		
			constructor: ( id, headingText, descriptionText, questionText, mandatory ) ->
				@id = ko.observable( id )
				@headingText = ko.observable( headingText )
				@descriptionText = ko.observable( descriptionText )
				@questionText = ko.observable( questionText )
				@mandatory = ko.observable( mandatory )
				
				@response = ko.observable( null )
				
			@createFromTransferObject: ( selfHelpGuideQuestionTO ) ->
				return new SelfHelpGuideQuestion( selfHelpGuideQuestionTO.id, selfHelpGuideQuestionTO.headingText, selfHelpGuideQuestionTO.challenge.selfHelpGuideDescription, selfHelpGuideQuestionTO.challenge.selfHelpGuideQuestion, selfHelpGuideQuestionTO.mandatory )