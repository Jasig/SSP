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

	SelfHelpGuideService:
	
		class SelfHelpGuideService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getAll: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getAll" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuides = for selfHelpGuide in result
							mygps.model.SelfHelpGuide.createFromTransferObject( selfHelpGuide )
						callbacks?.result?( selfHelpGuides )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getBySelfHelpGuideGroup: ( selfHelpGuideGroupId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getBySelfHelpGuideGroup?selfHelpGuideGroupId=#{ selfHelpGuideGroupId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuides = for selfHelpGuide in result
							mygps.model.SelfHelpGuide.createFromTransferObject( selfHelpGuide )
						callbacks?.result?( selfHelpGuides )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
							
			getContentById: ( selfHelpGuideId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getContentById?selfHelpGuideId=#{ selfHelpGuideId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuideContent = mygps.model.SelfHelpGuideContent.createFromTransferObject( result )
						callbacks?.result?( selfHelpGuideContent )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
