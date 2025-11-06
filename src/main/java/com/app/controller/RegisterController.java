package com.app.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OtpPhoneRegisterDto;

import com.app.dto.UserUpdateDTO;
import com.app.entity.OTPRegister;
import com.app.entity.User;
import com.app.enumeration.Status;
import com.app.repository.UserRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.OtpRegistrationService;
import com.app.service.RegisterService;
import com.app.service.UserService;
import com.app.util.PasswordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/register")
@Api(value = "Register Rest API's", produces = "application/json", consumes = "application/json")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	private final ResponseGenerator responseGenerator;
	private final RegisterService registerService;
	private final MessagePropertyService messagePropertyService;
	private final OtpRegistrationService otpService;
	private final UserService userService;
	private final UserRepository userRepository;

	@Autowired
	public RegisterController(@NonNull ResponseGenerator responseGenerator, @NonNull RegisterService registerService,
			@NonNull MessagePropertyService messagePropertyService, @NonNull OtpRegistrationService otpService,
			@NonNull UserService userService , @NonNull UserRepository userRepository) {
		this.responseGenerator = responseGenerator;
		this.registerService = registerService;
		this.messagePropertyService = messagePropertyService;
		this.otpService = otpService;
		this.userService = userService;
		this.userRepository = userRepository;
	}


	@PostMapping("/generate-otp")
	public ResponseEntity<?> generateOtp(@RequestBody OtpPhoneRegisterDto request,
			@RequestHeader HttpHeaders httpHeader) {
		logger.info("OTP email generation started at {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			Map<String, Object> responseData = otpService.sendOtpEmail(request.getEmail());
			return responseGenerator.successResponse(context, responseData, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while generating OTP: {}", e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// 🔹 Verify OTP
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody OTPRegister otpData, @RequestHeader HttpHeaders httpHeader) {
		logger.info("OTP verification started at {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			boolean otpVerified = otpService.verifyOtp(otpData.getEmail(), otpData.getOtp());
			if (otpVerified) {
				return responseGenerator.successResponse(context, "OTP Verified Successfully",
						HttpStatus.OK);
			} else {
				return responseGenerator.errorResponse(context, "Invalid or expired OTP", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Error while verifying OTP: {}", e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// 🔹 Update User details
	@ApiOperation(value = "Create or Update user details", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody UserUpdateDTO request, @RequestHeader HttpHeaders httpHeader) {
	    logger.info("User update started at {}", LocalDateTime.now());
	    TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

	    final UUID userId = request.getId();
	    final String email = request.getEmail() == null ? null : request.getEmail().trim().toLowerCase();

	    try {
	        User user;

	        // 1️⃣ If ID is null → new user (create)
	        if (userId == null) {
	            // ✅ Check email duplication
	            if (email != null && userRepository.existsByEmail(email)) {
	                return responseGenerator.errorResponse(context, messagePropertyService.getMessage("user.email.duplicate"),
	                        HttpStatus.BAD_REQUEST);
	            }

	            user = new User();
	            user.setId(UUID.randomUUID()); // assign new ID
	            logger.info("Creating new user with ID {}", user.getId());
	        } 
	        // 2️⃣ Otherwise → update existing user
	        else {
	            Optional<User> userOptional = registerService.findById(userId);
	            if (!userOptional.isPresent()) {
	                return responseGenerator.errorResponse(context, "User not found", HttpStatus.NOT_FOUND);
	            }
	            user = userOptional.get();

	            // Check email duplication (excluding same user)
	            if (email != null && userRepository.existsByEmailAndIdNot(email, userId)) {
	                return responseGenerator.errorResponse(context, messagePropertyService.getMessage("user.email.duplicate"),
	                        HttpStatus.BAD_REQUEST);
	            }
	        }

	        // 3️⃣ Encrypt password if provided
	        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
	            String encryptedPassword = PasswordUtil.getEncryptedPassword(request.getPassword());
	            user.setPassword(encryptedPassword);
	        }

	        // 4️⃣ Update other fields
	        user.setUserName(request.getUserName());
	        user.setEmail(email);
	        user.setFullName(request.getFullName());
	        user.setUserRoleId(request.getUserRoleId());
	        user.setStatus(Status.ACTIVE);

	        // 5️⃣ Save (works for both new and existing users)
	        registerService.update(user);

	        String message = (userId == null)
	                ? "Registered Successfully.!"
	                : "Updated Successfully";

	        return responseGenerator.successResponse(context, message, HttpStatus.OK);

	    } catch (Exception e) {
	        logger.error("Error occurred while saving user: {}", e.getMessage(), e);
	        return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
}