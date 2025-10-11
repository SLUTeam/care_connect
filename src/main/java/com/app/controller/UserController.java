package com.app.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDTO;
import com.app.dto.UserUpdateDTO;
import com.app.entity.User;
import com.app.enumeration.RequestType;
import com.app.repository.UserRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.security.JwtTokenUtil;
import com.app.service.MessagePropertyService;
import com.app.service.UserService;
import com.app.util.message.ResponseMessage;
import com.app.validator.UserValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/user")
@Api(value = "user Rest API's", produces = "application/json", consumes = "application/json")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private @NonNull ResponseGenerator responseGenerator;
	
	
	private @NonNull UserValidator userValidator;
	
	private @NonNull UserService userService;
	private @NonNull UserRepository userRepository;
	
	private @NonNull  JwtTokenUtil jwtTokenUtil;

	private @NonNull  MessageSource messageSource;
	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create user List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createUser(@RequestBody UserDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("user updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			ValidationResult validationResult = userValidator.validate(RequestType.POST, request);
			userService.saveOrUpdate((User) validationResult.getObject());

			return responseGenerator.successResponse(context, messagePropertyService.getMessage("user.create")
					, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating user {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	@ApiOperation(value = " Get user List.", response = Response.class)
	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getUser (@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("user updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context,messagePropertyService.getMessage("user.get"),userService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating user {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allows to delete use by user id.", response = Response.class)
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("user updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			User userOject = userService.findById(id).get();

			if (null == userOject) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
						HttpStatus.BAD_REQUEST);
			}
			//userOject.setStatus(Status.INACTIVE);
			userService.delete(id);

			return responseGenerator.successResponse(context, messagePropertyService.getMessage("user.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating user {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("user.invalid.delete"),
					HttpStatus.BAD_REQUEST);

		}
		}
	@ApiOperation(value = " Get user List By id.", response = Response.class)
	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ResponseEntity<?> getById (@PathVariable ("id") UUID id,@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("user updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context,messagePropertyService.getMessage("user.get"),userService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating user {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = " Get user List By id.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update (@RequestBody UserUpdateDTO request ,@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("user updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		User userObject =userService.findByUser(request.getUserName(),request.getId());
		if (null != userObject) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("user.name.duplicate"), HttpStatus.BAD_REQUEST);
		}
		User userPhoneObject =userService.findByUserPhone(request.getPhoneNo(),request.getId());
		if (null != userPhoneObject) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("user.mobile.duplicate"), HttpStatus.BAD_REQUEST);
		}
		User userPhoneObjects =userService.findByUserEmail(request.getEmail(),request.getId());
		if (null != userPhoneObjects) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("user.email.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Optional<User> userOptional = userService.findById(request.getId());

		User userObj = userOptional.get();
		try {
			userObj.setId(request.getId());
			userObj.setFullName(request.getFullName());
			userObj.setUserName(request.getUserName());
			userObj.setEmail(request.getEmail());
			userObj.setPhoneNo(request.getPhoneNo());
			userObj.setAddress(request.getAddress());
			userObj.setUserRoleId(request.getUserRoleId());
			userService.update(userObj);
			return responseGenerator.successResponse(context,messagePropertyService.getMessage("user.update"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating user {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

