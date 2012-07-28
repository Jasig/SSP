package org.jasig.ssp.web.api;

import java.util.UUID;

import org.jasig.ssp.factory.AppointmentTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AppointmentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Appointment controller
 */
@Controller
@RequestMapping("/1/person/{personId}/appointment")
public class AppointmentController
		extends AbstractPersonAssocController<Appointment, AppointmentTO> {

	protected AppointmentController() {
		super(Appointment.class, AppointmentTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppointmentController.class);

	@Autowired
	protected transient AppointmentService service;

	@Autowired
	protected transient AppointmentTOFactory factory;

	@Override
	protected AppointmentService getService() {
		return service;
	}

	@Override
	protected TOFactory<AppointmentTO, Appointment> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String permissionBaseName() {
		return "APPOINTMENT";
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public @ResponseBody
	AppointmentTO getCurrentAppointmentForPerson(
			final @PathVariable UUID personId) throws ObjectNotFoundException {

		checkPermissionForOp("READ");

		final Appointment appt = service.getCurrentAppointmentForPerson(
				personService.get(personId));
		if (appt == null) {
			return null;
		}

		return factory.from(appt);
	}

}
