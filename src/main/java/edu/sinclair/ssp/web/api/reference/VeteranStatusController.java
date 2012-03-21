package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.factory.TransferObjectListFactory;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.reference.VeteranStatusService;
import edu.sinclair.ssp.transferobject.ServiceResponse;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;
import edu.sinclair.ssp.web.api.RestController;
import edu.sinclair.ssp.web.api.validation.ValidationException;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/veteranStatus")
public class VeteranStatusController extends RestController<VeteranStatusTO> {

	private static final Logger logger = LoggerFactory
			.getLogger(VeteranStatusController.class);

	@Autowired
	private VeteranStatusService service;

	private TransferObjectListFactory<VeteranStatusTO, VeteranStatus> listFactory = new TransferObjectListFactory<VeteranStatusTO, VeteranStatus>(
			VeteranStatusTO.class);

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	List<VeteranStatusTO> getAll(
			@RequestParam(required = false) ObjectStatus status)
			throws Exception {
		if (status == null) {
			status = ObjectStatus.ACTIVE;
		}
		return listFactory.toTOList(service.getAll(status));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	VeteranStatusTO get(@PathVariable UUID id) throws Exception {
		VeteranStatus model = service.get(id);
		if (model != null) {
			return new VeteranStatusTO(model);
		} else {
			return null;
		}
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	VeteranStatusTO create(@Valid @RequestBody VeteranStatusTO obj)
			throws Exception {
		if (obj.getId() != null) {
			throw new ValidationException(
					"You submitted a veteranStatus with an id to the create method.  Did you mean to save?");
		}

		VeteranStatus model = obj.asModel();

		if (null != model) {
			VeteranStatus createdModel = service.create(model);
			if (null != createdModel) {
				return new VeteranStatusTO(createdModel);
			}
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	VeteranStatusTO save(@PathVariable UUID id,
			@Valid @RequestBody VeteranStatusTO obj) throws Exception {
		if (id == null) {
			throw new ValidationException(
					"You submitted a veteranStatus without an id to the save method.  Did you mean to create?");
		}

		VeteranStatus model = obj.asModel();
		model.setId(id);

		VeteranStatus savedVeteranStatus = service.save(model);
		if (null != savedVeteranStatus) {
			return new VeteranStatusTO(savedVeteranStatus);
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
		return new ServiceResponse(true);
	}

	@Override
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
