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
package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.reference.TemplateElectiveCourseDetailTOFactory;
import org.jasig.ssp.factory.reference.TemplateElectiveCourseElectiveCourseDetailTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.reference.TemplateElectiveCourseDetailService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.TemplateElectiveCourseElectiveTO;
import org.jasig.ssp.transferobject.reference.TemplateElectiveCourseTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/1/reference/map/electiveCourses")
@PreAuthorize(Permission.SECURITY_REFERENCE_MAP_WRITE)
public class TemplateElectiveCoursesController
        extends AbstractBaseController {

	@Autowired
	protected transient TemplateService templateService;

    @Autowired
    protected transient TemplateElectiveCourseDetailService templateElectiveCourseDetailService;

    @Autowired
    protected transient TemplateElectiveCourseDetailTOFactory templateElectiveCourseDetailTOFactory;

	@Autowired
	protected transient TemplateElectiveCourseElectiveCourseDetailTOFactory templateElectiveCourseElectiveCourseDetailTOFactory;

    protected TemplateElectiveCoursesController() {
        super();
        //super(JournalStep.class, JournalStepTO.class);
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TemplateElectiveCoursesController.class);

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

	@RequestMapping(value = "/{templateCourseId}/electiveCourseDetail", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<TemplateElectiveCourseElectiveTO> getAllElectiveCourseAssociationsForElectiveCourse(
			final @PathVariable UUID templateCourseId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

        TemplateCourse templateCourse = templateService.getTemplateCourse(templateCourseId);
        TemplateElectiveCourse templateElectiveCourse = templateElectiveCourseDetailService.get(templateCourse.getTemplate(), templateCourse.getFormattedCourse());

        if (null!= templateElectiveCourse) {
            final PagingWrapper<TemplateElectiveCourseElective> data =
                    templateElectiveCourseDetailService.getElectiveCourseAssociationsForElectiveCourse(templateElectiveCourse.getId());

            return new PagedResponse<>(true,
                    data.getResults(), templateElectiveCourseElectiveCourseDetailTOFactory
                    .asTOList(data.getRows()));
        } else {
            return new PagedResponse<>(true, 0L, templateElectiveCourseElectiveCourseDetailTOFactory.asTOList(null));
        }
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize(Permission.SECURITY_REFERENCE_READ)
    public @ResponseBody
    PagedResponse<TemplateElectiveCourseTO> getAllForTemplate(
            final @PathVariable UUID id,
            final @RequestParam(required = false) ObjectStatus status,
            final @RequestParam(required = false) Integer start,
            final @RequestParam(required = false) Integer limit,
            final @RequestParam(required = false) String sort,
            final @RequestParam(required = false) String sortDirection)
            throws ObjectNotFoundException {

        List<TemplateCourse> templateCourses = templateService.getUniqueTemplateCourseList(id);

        return new PagedResponse<TemplateElectiveCourseTO>(true,
                (long)templateCourses.size(),  templateElectiveCourseDetailTOFactory.asTOList(templateCourses));
    }

    @RequestMapping(value = "/{templateCourseId}/electiveCourseDetail", method = RequestMethod.DELETE)
    public @ResponseBody
    ServiceResponse removeElectiveCourseDetail(
            @PathVariable final UUID templateCourseId,
            @RequestBody @NotNull final UUID templateElectiveCourseElectiveId)
            throws ObjectNotFoundException {
        LOGGER.debug("ID: " + templateCourseId);

        templateElectiveCourseDetailService.deleteAssociatedElective(templateElectiveCourseElectiveId);
        TemplateCourse templateCourse = templateService.getTemplateCourse(templateCourseId);
        TemplateElectiveCourse templateElectiveCourse = templateElectiveCourseDetailService.get(templateCourse.getTemplate(), templateCourse.getFormattedCourse());
        if (null != templateElectiveCourse) {
            if (templateElectiveCourse.getElectiveCourseElectives().size() == 0) {
                try {
                    templateElectiveCourseDetailService.delete(templateElectiveCourse);
                } catch (HibernateOptimisticLockingFailureException e) {
                    LOGGER.debug("HibernateOptimisticLockingFailureException");
                    //swallow the exception
                }
            }
        }
        return new ServiceResponse(true);
    }

    @RequestMapping(value = "/{templateCourseId}/electiveCourseDetail", method = RequestMethod.POST)
    public @ResponseBody
    ServiceResponse addElectiveCourseDetail(
            @PathVariable final UUID templateCourseId,
            final @Valid @RequestBody TemplateElectiveCourseTO obj)

            throws ObjectNotFoundException, ValidationException {
        TemplateCourse templateCourse = templateService.getTemplateCourse(templateCourseId);
        TemplateElectiveCourse templateElectiveCourse = templateElectiveCourseDetailService.get(templateCourse.getTemplate(), templateCourse.getFormattedCourse());

        if (null == templateElectiveCourse) {
            templateElectiveCourse = new TemplateElectiveCourse();
            templateElectiveCourse.setFormattedCourse(templateCourse.getFormattedCourse());
            templateElectiveCourse.setCourseCode(templateCourse.getCourseCode());
            templateElectiveCourse.setCourseDescription(templateCourse.getCourseDescription());
            templateElectiveCourse.setCourseTitle(templateCourse.getCourseTitle());
            templateElectiveCourse.setCreditHours(templateCourse.getCreditHours());
            templateElectiveCourse.setTemplate(templateCourse.getTemplate());
            templateElectiveCourse.setObjectStatus(ObjectStatus.ACTIVE);
            templateElectiveCourse = templateElectiveCourseDetailService.createTemplateElectiveCourse(templateElectiveCourse);
        }

        TemplateElectiveCourseElective templateElectiveCourseElective = new TemplateElectiveCourseElective();
        templateElectiveCourseElective.setFormattedCourse(obj.getFormattedCourse());
        templateElectiveCourseElective.setCourseCode(obj.getCourseCode());
        templateElectiveCourseElective.setCourseDescription(obj.getCourseDescription());
        templateElectiveCourseElective.setCourseTitle(obj.getCourseTitle());
        templateElectiveCourseElective.setCreditHours(obj.getCreditHours());
        templateElectiveCourseElective.setTemplateElectiveCourse(templateElectiveCourse);
        templateElectiveCourseElective.setObjectStatus(ObjectStatus.ACTIVE);

        templateElectiveCourseDetailService.createTemplateElectiveCourseElective(templateElectiveCourseElective);

        return new ServiceResponse(true);
    }
}
