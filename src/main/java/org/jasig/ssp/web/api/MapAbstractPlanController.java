package org.jasig.ssp.web.api;

import com.google.common.collect.Lists;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.PlanLiteTOFactory;
import org.jasig.ssp.factory.reference.TemplateLiteTOFactory;
import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.security.permissions.ServicePermissions;
import org.jasig.ssp.service.MapAbstractPlanService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.TemplateLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping({ "/1/map" })
public class MapAbstractPlanController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapAbstractPlanController.class);

	@Autowired
	private MapAbstractPlanService mapAbstractPlanService;

	@Autowired
	private TemplateLiteTOFactory templateLiteTOFactory;

	@Autowired
	private PlanLiteTOFactory planLiteTOFactory;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
	public @ResponseBody
	PagedResponse<AbstractPlanTO<AbstractPlan>> getAll(
			final @RequestParam(required = false) ObjectStatus objectStatus,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {
		final PagingWrapper<AbstractPlan> data = mapAbstractPlanService.getAll(SortingAndPaging.createForSingleSortWithPaging(
				objectStatus == null ? ObjectStatus.ALL : objectStatus, 1,
				2, null, null, null));
		if ( data == null ) {
			return null;
		}
		return new PagedResponse<AbstractPlanTO<AbstractPlan>>(true, data.getResults(),
				new TOFactory<AbstractPlanTO<AbstractPlan>, AbstractPlan>() {
					@Override
					public AbstractPlanTO<AbstractPlan> from(AbstractPlan model) {
						if ( model instanceof Plan) {
							return (AbstractPlanTO) planLiteTOFactory.from((Plan)model);
						} else if ( model instanceof Template ) {
							return (AbstractPlanTO) templateLiteTOFactory.from((Template)model);
						} else {
							throw new IllegalArgumentException("Unmappable type: [" +
									(model == null ? null : model.getClass().getName()) + "]");
						}
					}

					@Override
					public AbstractPlan from(AbstractPlanTO<AbstractPlan> abstractPlanAbstractPlanTO) throws ObjectNotFoundException {
						throw new UnsupportedOperationException();
					}

					@Override
					public AbstractPlan from(UUID id) throws ObjectNotFoundException {
						throw new UnsupportedOperationException();
					}

					@Override
					public List<AbstractPlanTO<AbstractPlan>> asTOList(Collection<AbstractPlan> models) {
						final List<AbstractPlanTO<AbstractPlan>> tos = Lists.newArrayList();

						if ((models != null) && !models.isEmpty()) {
							for (AbstractPlan model : models) {
								tos.add(from(model));
							}
						}

						return tos;
					}

					@Override
					public Set<AbstractPlanTO<AbstractPlan>> asTOSet(Collection<AbstractPlan> models) {
						throw new UnsupportedOperationException();
					}

					@Override
					public Set<AbstractPlanTO<AbstractPlan>> asTOSetOrdered(Collection<AbstractPlan> models) {
						throw new UnsupportedOperationException();
					}

					@Override
					public Set<AbstractPlan> asSet(Collection<AbstractPlanTO<AbstractPlan>> abstractPlanTOs) throws ObjectNotFoundException {
						throw new UnsupportedOperationException();
					}

				}.asTOList(data.getRows()));
	}

}
