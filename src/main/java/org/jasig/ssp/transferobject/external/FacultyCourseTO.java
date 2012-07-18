package org.jasig.ssp.transferobject.external;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.external.FacultyCourse;

import com.google.common.collect.Lists;

public class FacultyCourseTO implements ExternalDataTO<FacultyCourse> {

	private String facultySchoolId;

	private String termCode;

	private String formattedCourse;

	private String title;

	public FacultyCourseTO() {
		super();
	}

	public FacultyCourseTO(final FacultyCourse model) {
		super();
		from(model);
	}

	@Override
	public final void from(final FacultyCourse model) {
		facultySchoolId = model.getFacultySchoolId();
		termCode = model.getTermCode();
		formattedCourse = model.getFormattedCourse();
		title = model.getTitle();
	}

	public static List<FacultyCourseTO> toTOList(
			final Collection<FacultyCourse> models) {
		final List<FacultyCourseTO> tObjects = Lists.newArrayList();
		for (final FacultyCourse model : models) {
			tObjects.add(new FacultyCourseTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	public void setFacultySchoolId(@NotNull final String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(@NotNull final String termCode) {
		this.termCode = termCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public final void setFormattedCourse(@NotNull final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getTitle() {
		return title;
	}

	public final void setTitle(@NotNull final String title) {
		this.title = title;
	}
}