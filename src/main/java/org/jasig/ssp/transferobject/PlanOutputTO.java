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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.reference.MessageTemplate;

import java.util.UUID;

/** PlanOutputTO is strictly for bringing back data from client
 * for printing and email purposes
 * 
 * @author jamesstanley
 *
 */
public class PlanOutputTO extends AbstractPlanOutputTO<Plan,PlanTO>{
	
	private UUID messageTemplateMatrixId = MessageTemplate.OUTPUT_MAP_PLAN_MATRIX_ID;

	private UUID messageTemplateShortMatrixId = MessageTemplate.OUTPUT_MAP_PLAN_SHORT_MATRIX_ID;

	private UUID messageTemplateFullId = MessageTemplate.OUTPUT_MAP_PLAN_FULL_ID;
    
    public PlanOutputTO(){
    	super();
    }
    
    public void setPlan(PlanTO plan){
    	setNonOuputTO(plan);
    }
   
    public PlanTO getPlan() {
    	return getNonOutputTO();
    }
    
    @Override
	public UUID getMessageTemplateMatrixId() {
		return messageTemplateMatrixId;
	}

    @Override
	public void setMessageTemplateId(UUID messageTemplateMatrixId) {
		this.messageTemplateMatrixId = messageTemplateMatrixId;
	}
	
    @Override
	public UUID getMessageTemplateFullId() {
		return messageTemplateFullId;
	}

    @Override
	public void setMessageTemplateFullId(UUID messageTemplateFullId) {
		this.messageTemplateFullId = messageTemplateFullId;
	}

	public UUID getMessageTemplateShortMatrixId() {
		return messageTemplateShortMatrixId;
	}

	public void setMessageTemplateShortMatrixId(UUID messageTemplateShortMatrixId) {
		this.messageTemplateShortMatrixId = messageTemplateShortMatrixId;
	}
}
