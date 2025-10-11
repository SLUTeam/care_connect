package com.app.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ErrorDto;
import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.OtpDTO;
import com.app.dto.PasswordSendEvent;
import com.app.dto.PasswordUpdateDTO;
import com.app.entity.User;
import com.app.enumeration.Status;
import com.app.repository.UserRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.security.JwtTokenUtil;
import com.app.service.MessagePropertyService;
import com.app.service.UserService;
import com.app.util.PasswordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AuthenticationController {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull UserRepository userRepository;
	
	private @NonNull UserService userService;
	
	private JavaMailSender emailSender;
	 
	private @NonNull JwtTokenUtil jwtTokenUtil;

	private @NonNull MessageSource messageSource;
	
	//private @NonNull RoleRepository roleRepository;
	
	private @NonNull MessagePropertyService messagePropertySource;

	private @NonNull ApplicationEventPublisher applicationEventPublisher;

	@ApiOperation(value = "Logs the user in to the system and return the auth tokens")
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@ApiParam(value = "The LoginRequest payload") @RequestBody LoginRequest request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (null == request) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid Request Payload.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
		if (!userOptional.isPresent()) {
		    errorDto = new ErrorDto();
		    errorDto.setCode("400");
		    errorDto.setMessage("Invalid Username.!");
		    response.put("status", 0);
		    response.put("error", errorDto);
		    return ResponseEntity.badRequest().body(response);
		} else {
		    User user = userOptional.get();
		    String usernameFromDb = user.getUserName();
		    String requestedUsername = request.getUserName();
		    if (!usernameFromDb.equals(requestedUsername)) {
		        errorDto = new ErrorDto();
		        errorDto.setCode("400");
		        errorDto.setMessage("Invalid Username.!");
		        response.put("status", 0);
		        response.put("error", errorDto);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		User user = userOptional.get();
		if (user.getStatus() != Status.ACTIVE) {
		    errorDto = new ErrorDto();
		    errorDto.setCode("400");
		    errorDto.setMessage("User is not active. Please contact the administrator.");
		    response.put("status", 0);
		    response.put("error", errorDto);
		    return ResponseEntity.badRequest().body(response);
		}

		String encryptedPassword = PasswordUtil.getEncryptedPassword(request.getPassword());
		if (!user.getPassword().equals(encryptedPassword)) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Password is wrong.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		final String token = jwtTokenUtil.generateToken(user);
		response.put("status", 1);
		response.put("message", "Logged in Successfully.!");
		response.put("jwt", token);
		response.put("isOtpVerified", true);
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		/*
		 * logger.info(response.toString()); return
		 * ResponseEntity.accepted().body(response);
		 */
	}

	@ApiOperation(value = "Get logged in user information based from JWT")
	@RequestMapping(value = "/user-details", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserDetails(Principal principal, @RequestHeader HttpHeaders httpHeader,
			Principal principle) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		Optional<User> userOptional = userRepository.findByUserName(principle.getName());
		User user = userOptional.get();
//	Role roles =roleRepository.findRoleNameById(user.getUserRoleId());
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setFullName(user.getFullName());
		loginResponse.setPhone(user.getPhoneNo());
		loginResponse.setUserId(user.getId());
		loginResponse.setUserName(user.getUserName());
		loginResponse.setChangePassword(user.getChangePassword());
		loginResponse.setAddress(user.getAddress());
		loginResponse.setRoleId(user.getUserRoleId());
//		loginResponse.setRoleName(roles.getRoleName());
		response.put("loginObj", loginResponse);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		/*
		 * logger.info(response.toString()); return
		 * ResponseEntity.accepted().body(response);
		 */
	}
	
	@ApiOperation(value = "Allows to change current password", response = Response.class)
	@PutMapping(value = "/change/password", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "Payload for change current password ") @RequestBody PasswordUpdateDTO request,
			@RequestHeader HttpHeaders httpHeaders,Principal principle) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeaders);		
		String userName = principle.getName();	
		
		Optional<User> userOptional = userService.findByPassword(
				PasswordUtil.getEncryptedPassword(request.getOldPassword()),userName);
		
		if (!userOptional.isPresent()) {
			return responseGenerator.errorResponse(context, messagePropertySource.getMessage("invalid.user.password"),
					HttpStatus.BAD_REQUEST);
		}
		User userObj = userOptional.get();
		
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			return responseGenerator.errorResponse(context,
					messagePropertySource.getMessage("invalid.new.and.confirm.password"), HttpStatus.BAD_REQUEST);
		}
		
		 if (request.getConfirmPassword().equals(request.getOldPassword() )){
			return responseGenerator.errorResponse(context,
					messagePropertySource.getMessage("invalid.old.and.new.password"), HttpStatus.BAD_REQUEST);
		}
		 
		 
		userObj.setPassword(PasswordUtil.getEncryptedPassword(request.getNewPassword()));
		userObj.setModifiedOn(new Date());	
		userObj.setChangePassword(true);
		userService.saveOrUpdate(userObj);
		try {
			return responseGenerator.successResponse(context, messagePropertySource.getMessage("password.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "send new password for user", response = Response.class)
	@PutMapping(value = "/send/password", produces = "application/json")
	public ResponseEntity<?> sendPassword(@ApiParam(value = "otpDto request payload") @RequestBody OtpDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (request.getUserNameOrEmail() == null || request.getUserNameOrEmail().isEmpty()) {
			return responseGenerator.errorResponse(context, messagePropertySource.getMessage("payload.invalid"),
					HttpStatus.BAD_REQUEST);
		}

		User userObj = userService.getUserByUserNameOrEmail(request.getUserNameOrEmail());
		if (userObj == null) {
			return responseGenerator.errorResponse(context, messagePropertySource.getMessage("username.email.invalid"),
					HttpStatus.BAD_REQUEST);
		}

		String passowrd1 = RandomString.make(4);

		Random r = new Random();
		Integer password2 = 100000 + r.nextInt(900000);
		String password = passowrd1 + password2.toString();

		// send email
		SimpleMailMessage message = new SimpleMailMessage();
		PasswordSendEvent mailEvent = new PasswordSendEvent(this, password);
		mailEvent.setEmailTo(userObj.getEmail());
		applicationEventPublisher.publishEvent(mailEvent);
		message.setTo(userObj.getEmail());
		message.setSubject("OTP");
		message.setText(password);
		emailSender.send(message);
		System.out.println(password);
		userObj.setPassword(PasswordUtil.getEncryptedPassword(password));
		userObj.setForcePasswordChange(true);
		userService.update(userObj);

		try {
			return responseGenerator.successResponse(context, messagePropertySource.getMessage("password.send"),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
