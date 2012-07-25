package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Person external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_person")
public class ExternalPerson extends AbstractExternalData implements
		Serializable, ExternalData {

	private static final long serialVersionUID = 7972452789881520560L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId, firstName, lastName;

	@Column(length = 50)
	@Size(max = 50)
	private String middleName;

	private Date birthDate;

	@Column(length = 100)
	@Size(max = 100)
	private String primaryEmailAddress;

	@Column(length = 50)
	@Size(max = 50)
	private String addressLine1, addressLine2, city;

	@Column(length = 2)
	@Size(max = 2)
	private String state;

	@Column(length = 10)
	@Size(max = 10)
	private String zipCode;

	@Column(length = 25)
	@Size(max = 25)
	private String homePhone, workPhone;

	@Column(length = 50)
	@Size(max = 50)
	private String officeLocation, officeHours;

	@Column(length = 100)
	@Size(max = 100)
	private String departmentName;

	@Column(length = 20)
	@Size(max = 20)
	private String actualStartTerm;

	private Integer actualStartYear;

	@Column(length = 10)
	@Size(max = 10)
	private String maritalStatus, ethnicity;

	@Column(length = 1)
	@Size(max = 1)
	private String gender, isLocal;

	private Number balanceOwed;

	@Column(length = 50)
	@Size(max = 50)
	private String coachSchoolId;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return (birthDate == null) ? null : (new Date(birthDate.getTime()));
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = (birthDate == null) ? null : (new Date(
				birthDate.getTime()));
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(final String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(final String workPhone) {
		this.workPhone = workPhone;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(final String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(final String officeHours) {
		this.officeHours = officeHours;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}

	public String getActualStartTerm() {
		return actualStartTerm;
	}

	public void setActualStartTerm(final String actualStartTerm) {
		this.actualStartTerm = actualStartTerm;
	}

	public Integer getActualStartYear() {
		return actualStartYear;
	}

	public void setActualStartYear(final Integer actualStartYear) {
		this.actualStartYear = actualStartYear;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(final String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(final String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(final String isLocal) {
		this.isLocal = isLocal;
	}

	public Number getBalanceOwed() {
		return balanceOwed;
	}

	public void setBalanceOwed(final Number balanceOwed) {
		this.balanceOwed = balanceOwed;
	}

	public String getCoachSchoolId() {
		return coachSchoolId;
	}

	public void setCoachSchoolId(final String coachSchoolId) {
		this.coachSchoolId = coachSchoolId;
	}

}
