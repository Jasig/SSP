package org.jasig.ssp.web.api.reference; // NOPMD

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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

@Controller
@RequestMapping("/1/reference/challenge")
public class ChallengeController
		extends
		AbstractAuditableReferenceController<Challenge, ChallengeTO> {

	@Autowired
	protected transient ChallengeService service;

	@Autowired
	protected transient ChallengeReferralService challengeReferralService;

	@Override
	protected AuditableCrudService<Challenge> getService() {
		return service;
	}

	@Autowired
	protected transient ChallengeTOFactory factory;

	@Autowired
	protected transient ChallengeReferralTOFactory challengeReferralTOFactory;

	@Override
	protected TOFactory<ChallengeTO, Challenge> getFactory() {
		return factory;
	}

	protected ChallengeController() {
		super(Challenge.class, ChallengeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/{id}/challengeReferral/", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<ChallengeReferralTO> getChallengeReferralsForChallenge(
			@PathVariable final UUID id,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);

		final PagingWrapper<ChallengeReferral> data = challengeReferralService
				.getAllForChallenge(challenge, SortingAndPaging
						.createForSingleSort(status, start, limit, sort,
								sortDirection, null));

		return new PagedResponse<ChallengeReferralTO>(true,
				data.getResults(),
				challengeReferralTOFactory.asTOSet(data.getRows()));
	}

	@RequestMapping(value = "/{id}/challengeReferral", method = RequestMethod.POST)
	public @ResponseBody
	ServiceResponse addChallengeReferralToChallenge(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeReferralId)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);
		final ChallengeReferral referral = challengeReferralService
				.get(challengeReferralId);

		service.addChallengeReferralToChallenge(referral, challenge);

		return new ServiceResponse(true);
	}

	@RequestMapping(value = "/{id}/challenge", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse removeChallengeReferralFromChallenge(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeReferralId)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);
		final ChallengeReferral referral = challengeReferralService
				.get(challengeReferralId);

		service.removeChallengeReferralFromChallenge(referral, challenge);
		return new ServiceResponse(true);
	}
}
