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
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
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
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

//	@Autowired
//	protected transient JournalStepDetailDao journalStepDetailDao;

//	@Autowired
//	protected transient JournalStepTOFactory factory;

    @Autowired
    protected transient TemplateElectiveCourseDetailTOFactory templateElectiveCourseDetailTOFactory;

	@Autowired
	protected transient TemplateElectiveCourseElectiveCourseDetailTOFactory templateElectiveCourseElectiveCourseDetailTOFactory;

//	@Override
//	protected TOFactory<JournalStepTO, JournalStep> getFactory() {
//		return factory;
//	}

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

	@RequestMapping(value = "/{electiveCourseId}/electiveCourseDetail", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<TemplateElectiveCourseElectiveTO> getAllElectiveCourseAssociationsForElectiveCourse(
			final @PathVariable UUID electiveCourseId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final PagingWrapper<TemplateElectiveCourseElective> data =
                templateElectiveCourseDetailService.getElectiveCourseAssociationsForElectiveCourse(electiveCourseId,
				SortingAndPaging.createForSingleSortWithPaging(status, start,
						limit, sort, sortDirection, null));

		return new PagedResponse<TemplateElectiveCourseElectiveTO>(true,
				data.getResults(), templateElectiveCourseElectiveCourseDetailTOFactory
					.asTOList(data.getRows()));
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

//		final JournalStep journalStep = getService()
//				.get(id);

        Template template = templateService.get(id);

//        final PagingWrapper<TemplateElectiveCourse> data = templateElectiveCourseDetailService
//                .getAllForTemplate(template,
//                        SortingAndPaging.createForSingleSortWithPaging(status, start,
//                                limit, sort, sortDirection, null));
        return new PagedResponse<TemplateElectiveCourseTO>(true,
                (long)template.getCourses().size(),  templateElectiveCourseDetailTOFactory.asTOList((List<TemplateCourse>) template.getCourses()));
    }
//
//	@RequestMapping(value = "/{id}/journalStepDetail", method = RequestMethod.POST)
//	public @ResponseBody
//	ServiceResponse addJournalStepDetailToJournalStep(
//			@PathVariable final UUID id,
//			@RequestBody @NotNull final JournalAssociationTO journalAssociation)
//			throws ObjectNotFoundException {
//
//		final JournalStepDetail journalStepDetail = electiveCourseDetailService
//				.get(journalAssociation.getId());
//		final JournalStep journalStep = service.get(id);
//		for(JournalStepJournalStepDetail detail:journalStep.getJournalStepJournalStepDetails())
//		{
//			if(detail.getJournalStepDetail().getId().equals(journalStepDetail.getId())){
//				service.removeJournalStepDetailFromJournalStep(journalStepDetail, journalStep);
//				break;
//			}
//		}
//		service.addJournalStepDetailToJournalStep(journalStepDetail,
//				journalStep,journalAssociation.getSortOrder());
//
//		return new ServiceResponse(true);
//	}

    @RequestMapping(value = "/{id}/electiveCourse", method = RequestMethod.DELETE)
    public @ResponseBody
    ServiceResponse removeElectiveCourseDetail(
            @PathVariable final UUID id,
            @RequestBody @NotNull final UUID templateElectiveCourseId)
            throws ObjectNotFoundException {

//		final JournalStepDetail journalStepDetail = journalStepDetailDao
//				.load(journalStepDetailId);
//		final JournalStep journalStep = service.get(id);
//
//		service.removeJournalStepDetailFromJournalStep(journalStepDetail,
//				journalStep);

        return new ServiceResponse(true);
    }

    /**
     * Persist a new instance of the specified object.
     * <p>
     * Must not include an id.
     *
     * @param obj
     *            New instance to persist.
     * @return Original instance plus the generated id.
     * @throws ObjectNotFoundException
     *             If specified object could not be found.
     * @throws ValidationException
     *             If the specified data contains an id (since it shouldn't).
     */
//	@Override
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody TemplateElectiveCourseTO create(@Valid @RequestBody final TemplateElectiveCourseTO obj)
            throws ObjectNotFoundException,	ValidationException {
//		return super.create(obj);
        return null;
    }

    /**
     * Persist any changes to the specified instance.
     *
     * @param id
     *            Explicit id to the instance to persist.
     * @param obj
     *            Full instance to persist.
     * @return The update data object instance.
     * @throws ObjectNotFoundException
     *             If specified object could not be found.
     * @throws ValidationException
     *             If the specified id is null.
     */
//	@Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody TemplateElectiveCourseTO save(@PathVariable final UUID id, @Valid @RequestBody final TemplateElectiveCourseTO obj)
            throws ValidationException, ObjectNotFoundException {
//		return super.save(id, obj);
        return null;
    }

    /**
     * Marks the specified data instance with a status of
     * {@link ObjectStatus#INACTIVE}.
     *
     * @param id
     *            The id of the data instance to mark deleted.
     * @return Success boolean.
     * @throws ObjectNotFoundException
     *             If specified object could not be found.
     */
//	@Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ServiceResponse delete(@PathVariable final UUID id)
            throws ObjectNotFoundException {
//		return super.delete(id);
        return null;
    }

}
