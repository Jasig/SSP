/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model.external;

public enum FinancialFileStatus {
	/*
	 * WARNING: Only _append_ enum values to this list! Do not insert new values
	 * in between others or change the order, or existing database values will
	 * become out of sync because the Hibernate mapping uses ordinals (the
	 * integer index of the enum in this list) instead of keys (strings based on
	 * the name of the enum).
	 */
	
	INCOMPLETE,
	PENDING,
	COMPLETE
}
