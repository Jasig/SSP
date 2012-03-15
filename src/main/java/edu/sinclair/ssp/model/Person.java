package edu.sinclair.ssp.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "person", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends Auditable{

	public static UUID SYSTEM_ADMINISTRATOR_ID = UUID.fromString("58ba5ee3-734e-4ae9-b9c5-943774b4de41");

	@Column(name = "first_name", nullable = false, length=50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String firstName;

	@Column(name = "middle_initial", nullable = true, length=1)
	@Size(max = 1)
	private String middleInitial;

	@Column(name = "last_name", nullable = false, length=50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "primary_email_address", length=100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String primaryEmailAddress;

	@Column(name = "secondary_email_address", length=100)
	@Size(max = 100)
	private String secondaryEmailAddress;

	@Column(length=25)
	@Size(max = 25)
	private String username;

	@Column(name = "home_phone", length=25)
	@Size(max = 25)
	private String homePhone;

	@Column(name = "work_phone", length=25)
	@Size(max = 25)
	private String workPhone;

	@Column(name = "cell_phone", length=25)
	@Size(max = 25)
	private String cellPhone;

	@Column(name = "address_line_1", length=50)
	@Size(max = 50)
	private String addressLine1;

	@Column(name = "address_line_2", length=50)
	@Size(max = 50)
	private String addressLine2;

	@Column(length=50)
	@Size(max = 50)
	private String city;

	@Column(length=2)
	@Size(max = 2)
	private String state;

	@Column(name = "zip_code", length=10)
	@Size(max = 10)
	private String zipCode;

	@Column(name = "photo_url", length=100)
	@Size(max = 100)
	private String photoUrl;

	@Column(name = "school_id", length=50)
	@Size(max = 50)
	private String schoolId;

	private boolean enabled;

	public Person(){}
	public Person(UUID id){
		super(id);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
