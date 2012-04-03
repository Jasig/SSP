package edu.sinclair.ssp.transferobject;

import java.util.Date;

import edu.sinclair.ssp.model.Person;

public class PersonTO extends AuditableTO<Person> implements
		TransferObject<Person> {

	private String firstName, middleInitial, lastName;
	private Date birthDate;
	private String primaryEmailAddress, secondaryEmailAddress;
	private String username;
	private String homePhone, workPhone, cellPhone;
	private String addressLine1, addressLine2, city, state, zipCode;
	private String photoUrl;
	private String schoolId;
	private boolean enabled;

	public PersonTO() {
		super();
	}

	public PersonTO(Person model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(Person model) {
		super.fromModel(model);

		setFirstName(model.getFirstName());
		setMiddleInitial(model.getMiddleInitial());
		setLastName(model.getLastName());
		setBirthDate(model.getBirthDate());
		setPrimaryEmailAddress(model.getPrimaryEmailAddress());
		setSecondaryEmailAddress(model.getSecondaryEmailAddress());
		setUsername(model.getUsername());
		setHomePhone(model.getHomePhone());
		setWorkPhone(model.getWorkPhone());
		setCellPhone(model.getCellPhone());
		setAddressLine1(model.getAddressLine1());
		setAddressLine2(model.getAddressLine2());
		setCity(model.getCity());
		setState(model.getState());
		setZipCode(model.getZipCode());
		setPhotoUrl(model.getPhotoUrl());
		setSchoolId(model.getSchoolId());
		setEnabled(model.isEnabled());
	}

	@Override
	public Person pushAttributesToModel(Person model) {
		super.addToModel(model);
		model.setFirstName(getFirstName());
		model.setMiddleInitial(getMiddleInitial());
		model.setLastName(getLastName());
		model.setBirthDate(getBirthDate());
		model.setPrimaryEmailAddress(getPrimaryEmailAddress());
		model.setSecondaryEmailAddress(getSecondaryEmailAddress());
		model.setUsername(getUsername());
		model.setHomePhone(getHomePhone());
		model.setWorkPhone(getWorkPhone());
		model.setCellPhone(getCellPhone());
		model.setAddressLine1(getAddressLine1());
		model.setAddressLine2(getAddressLine2());
		model.setCity(getCity());
		model.setState(getState());
		model.setZipCode(getZipCode());
		model.setPhotoUrl(getPhotoUrl());
		model.setSchoolId(getSchoolId());
		model.setEnabled(isEnabled());

		return model;
	}

	@Override
	public Person asModel() {
		return pushAttributesToModel(new Person());
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
		return birthDate == null ? null : new Date(birthDate.getTime());
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate == null ? null : new Date(
				birthDate.getTime());
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
