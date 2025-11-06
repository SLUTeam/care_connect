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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Country;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.CountryService;
import com.app.service.MessagePropertyService;
import com.app.service.StateService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/country")
@Api(value = "state Rest API's", produces = "application/json", consumes = "application/json")
public class CountryController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private @NonNull StateService stateService;

	private @NonNull CountryService countryService;


	private @NonNull ResponseGenerator responseGenerator;

	

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = "Allow Create Country List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createCountry(@RequestBody Country country, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("state updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == country) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Country countryobj = countryService.findByCountryName(country.getCountryName());
		if (null != countryobj) {

			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("country.name.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Country countryobject = countryService.findByShortName(country.getShortName());
		if (null != countryobject) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("country.shortName.duplicate"), HttpStatus.BAD_REQUEST);
		}
		
		Country countrycode = countryService.findByCountryCode(country.getCountryCode());
		if (null != countrycode) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("country.code.duplicate"), HttpStatus.BAD_REQUEST);
		}

		try {
			countryService.save(country);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("country.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allows to fetch all country details as a list.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("country updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("country.get"),
					countryService.getCountryList(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating country {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all country details as a list.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("country updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("country.get"),
					countryService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating country {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete country List.", response = Response.class)
	@DeleteMapping(value = "/delete/{countryId}", produces = "application/json")
	public ResponseEntity<?> deleteCountry(@PathVariable("countryId") UUID countryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("country delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			countryService.delete(countryId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("country.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating country {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("country.invalid"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to  Update country List.", response = Response.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCountry(@RequestBody Country country, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("country updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == country.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Country countryObj = countryService.get(country.getId());
		if (null == countryObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Country countryDuplicate = countryService.findByNameExcludeId(country.getCountryName(), country.getId());
		if (null != countryDuplicate) {

			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("country.name.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Country countryCodeDuplicate = countryService.findByCodeExcludeId(country.getCountryCode(), country.getId());
		if (null != countryCodeDuplicate) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("country.code.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {

			countryService.update(country);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("country.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating country {}", e);

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}