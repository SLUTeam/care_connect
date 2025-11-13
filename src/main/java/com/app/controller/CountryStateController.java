package com.app.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.CountryService;
import com.app.service.CountryStateService;
import com.app.service.DistrictService;
import com.app.service.MessagePropertyService;
import com.app.service.StateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/countrystate")
@Api(value = "state Rest API's", produces = "application/json", consumes = "application/json")
public class CountryStateController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private @NonNull StateService stateService;

	private @NonNull CountryService countryService;

	private @NonNull DistrictService districtService;

	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull CountryStateService countryStateService;

	@Autowired
	MessagePropertyService messagePropertyService;
	

	
	@ApiOperation(value = "Allows to fetch all district by state id.", response = Response.class)
	@RequestMapping(value = "/district/{stateId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllDistrictByStateId(@PathVariable("stateId") UUID stateId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("district get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("district.get.list"),
					districtService.getDistrictsByStateId(stateId), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all state by country id.", response = Response.class)
	@RequestMapping(value = "/state/{countryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllStateByCountryId(@PathVariable("countryId") UUID countryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("state get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("state.get.list"),
					stateService.getStatesByCountryId(countryId), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
