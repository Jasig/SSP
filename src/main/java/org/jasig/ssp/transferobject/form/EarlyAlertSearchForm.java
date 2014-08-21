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
package org.jasig.ssp.transferobject.form;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class EarlyAlertSearchForm {

	private Person author;
	
	private Person student;
	
	private SortingAndPaging sortAndPage;
	
	public EarlyAlertSearchForm() {
	}

	public EarlyAlertSearchForm(Person author, Person student,
			SortingAndPaging sortAndPage) {
		super();
		this.author = author;
		this.student = student;
		this.sortAndPage = sortAndPage;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person authorId) {
		this.author = authorId;
	}

	public Person getStudent() {
		return student;
	}

	public void setStudent(Person student) {
		this.student = student;
	}

	public SortingAndPaging getSortAndPage() {
		return sortAndPage;
	}

	public void setSortAndPage(SortingAndPaging sortAndPage) {
		this.sortAndPage = sortAndPage;
	}
}
