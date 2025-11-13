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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.StateEntity;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.StateService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/state")
@Api(value = "state Rest API's", produces = "application/json", consumes = "application/json")
public class StateController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private @NonNull StateService stateService;

	private @NonNull ResponseGenerator responseGenerator;


	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create state List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createState(@RequestBody StateEntity state, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("state updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == state) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		StateEntity stateobj = stateService.findByStateName(state.getStateName());
		if (null != stateobj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("state.duplicate"), HttpStatus.BAD_REQUEST);
		}
		StateEntity stateobject = stateService.findByShortName(state.getShortName());
		if (null != stateobject) {
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("state.shortName.duplicate"),
					HttpStatus.BAD_REQUEST);
		}
		stateService.save(state);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("state.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to  Update state List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateState(@RequestBody StateEntity state, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("State updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == state.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		StateEntity stateObj = stateService.get(state.getId());
		if (null == stateObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		StateEntity stateDuplicate = stateService.findByNameExcludeId(state.getStateName(), state.getId());
		if (null != stateDuplicate) {
			String[] params = new String[] { state.getStateName() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("state.duplicate", params), HttpStatus.BAD_REQUEST);
		}
		
		try {

			stateService.update(state);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("state.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete State List.", response = Response.class)
	@DeleteMapping(value = "/delete/{stateId}", produces = "application/json")
	public ResponseEntity<?> deleteState(@PathVariable("stateId") UUID stateId,@RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("State delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			stateService.delete(stateId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("state.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating State {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("state.invalid"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all State details as a list.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllState(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("State updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("state.get"),
					stateService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all State details as a list.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getStateById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("State updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("state.get"),
					stateService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating state {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
