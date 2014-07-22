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
