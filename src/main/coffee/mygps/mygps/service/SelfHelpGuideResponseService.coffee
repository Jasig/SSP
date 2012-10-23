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

namespace 'mygps.service'

	SelfHelpGuideResponseService:
	
		class SelfHelpGuideResponseService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			cancel: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/cancel?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				
			complete: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/complete?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getById: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getById?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuideResponse = mygps.model.SelfHelpGuideResponse.createFromTransferObject( result )
						callbacks?.result?( selfHelpGuideResponse )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			initiate: ( selfHelpGuideId, callbacks ) ->
				$.ajax(
					url: @createURL( "/initiate?selfHelpGuideId=#{ selfHelpGuideId }" )
					dataType: "text"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			answer: ( selfHelpGuideResponseId, selfHelpGuideQuestionId, response, callbacks ) ->
				$.ajax(
					url: @createURL( "/answer?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }&selfHelpGuideQuestionId=#{ selfHelpGuideQuestionId }&response=#{ response }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
