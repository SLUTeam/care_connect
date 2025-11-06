package com.app.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.*;

import com.app.dto.ErrorDto;
import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.OtpDTO;
import com.app.dto.PasswordSendEvent;
import com.app.dto.PasswordUpdateDTO;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.enumeration.Status;
import com.app.enumeration.UserType;
import com.app.repository.RoleRepository;
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
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "Authorization Rest API", 
     description = "Defines endpoints for unauthenticated access (e.g., login, password reset).")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class);

    @Autowired private ResponseGenerator responseGenerator;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private MessageSource messageSource;
    @Autowired private RoleRepository roleRepository;
    @Autowired private MessagePropertyService messagePropertySource;
    @Autowired private ApplicationEventPublisher applicationEventPublisher;
    @Autowired private JavaMailSender emailSender;
    
    // =========================== LOGIN ===========================

    @ApiOperation(value = "Logs the user in to the system and returns auth tokens")
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(
            @ApiParam(value = "The LoginRequest payload") 
            @RequestBody LoginRequest request,
            @RequestHeader HttpHeaders httpHeader) throws Exception {

        System.out.println(request.getUserName());
        Map<String, Object> response = new HashMap<>();
        ErrorDto errorDto = new ErrorDto();

        if (request == null) {
            errorDto.setCode("400");
            errorDto.setMessage("Invalid Request Payload.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        // 1️⃣ Check username
        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
        if (!userOptional.isPresent()) {
            errorDto.setCode("400");
            errorDto.setMessage("Invalid Username.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        User user = userOptional.get();

        // 2️⃣ Status validation
        if (user.getStatus() != Status.ACTIVE) {
            errorDto.setCode("400");
            errorDto.setMessage("User is not active. Please contact the administrator.");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        // 3️⃣ Password validation
        String encryptedPassword = PasswordUtil.getEncryptedPassword(request.getPassword());
        if (!user.getPassword().equals(encryptedPassword)) {
            errorDto.setCode("400");
            errorDto.setMessage("Password is wrong.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        // 4️⃣ Generate JWT Token
        final String token = jwtTokenUtil.generateToken(user);
        Optional<UserType> role = roleRepository.findRoleNameById(user.getUserRoleId());

        response.put("status", 1);
        response.put("message", "Logged in Successfully.!");
        response.put("jwt", token);
        response.put("roleName",role);
        response.put("isOtpVerified", true);

        TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
        return responseGenerator.successResponse(context, response, HttpStatus.OK);
    }

    // ======================= USER DETAILS =========================

    @ApiOperation(value = "Get logged in user details based on JWT")
    @GetMapping(value = "/user-details", produces = "application/json")
    public ResponseEntity<?> getUserDetails(Principal principal, 
                                            @RequestHeader HttpHeaders httpHeader) throws Exception {

        TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOptional = userRepository.findByUserName(principal.getName());
        if (!userOptional.isPresent()) {
            return responseGenerator.errorResponse(context, "User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFullName(user.getFullName());
        loginResponse.setPhone(user.getPhoneNo());
        loginResponse.setUserId(user.getId());
        loginResponse.setUserName(user.getUserName());
        loginResponse.setChangePassword(user.getChangePassword());
        loginResponse.setAddress(user.getAddress());
        loginResponse.setRoleId(user.getUserRoleId());

        response.put("loginObj", loginResponse);
        return responseGenerator.successResponse(context, response, HttpStatus.OK);
    }

    // =================== CHANGE PASSWORD =========================

    @ApiOperation(value = "Allows user to change current password", response = Response.class)
    @PutMapping(value = "/change/password", produces = "application/json")
    public ResponseEntity<?> updatePassword(
            @ApiParam(value = "Payload for change current password")
            @RequestBody PasswordUpdateDTO request,
            @RequestHeader HttpHeaders httpHeaders,
            Principal principal) throws Exception {

        TransactionContext context = responseGenerator.generateTransationContext(httpHeaders);
        String userName = principal.getName();

        Optional<User> userOptional = userService.findByPassword(
                PasswordUtil.getEncryptedPassword(request.getOldPassword()), userName);

        if (!userOptional.isPresent()) {
            return responseGenerator.errorResponse(context,
                    messagePropertySource.getMessage("invalid.user.password"),
                    HttpStatus.BAD_REQUEST);
        }

        User userObj = userOptional.get();

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return responseGenerator.errorResponse(context,
                    messagePropertySource.getMessage("invalid.new.and.confirm.password"),
                    HttpStatus.BAD_REQUEST);
        }

        if (request.getConfirmPassword().equals(request.getOldPassword())) {
            return responseGenerator.errorResponse(context,
                    messagePropertySource.getMessage("invalid.old.and.new.password"),
                    HttpStatus.BAD_REQUEST);
        }

        userObj.setPassword(PasswordUtil.getEncryptedPassword(request.getNewPassword()));
        userObj.setChangePassword(true);
        userService.saveOrUpdate(userObj);

        return responseGenerator.successResponse(context,
                messagePropertySource.getMessage("password.update"),
                HttpStatus.OK);
    }

    // =================== SEND NEW PASSWORD =======================

    @ApiOperation(value = "Send new password to user", response = Response.class)
    @PutMapping(value = "/send/password", produces = "application/json")
    public ResponseEntity<?> sendPassword(
            @ApiParam(value = "OTP DTO request payload")
            @RequestBody OtpDTO request,
            @RequestHeader HttpHeaders httpHeader) throws Exception {

        TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

        if (request.getUserNameOrEmail() == null || request.getUserNameOrEmail().isEmpty()) {
            return responseGenerator.errorResponse(context,
                    messagePropertySource.getMessage("payload.invalid"),
                    HttpStatus.BAD_REQUEST);
        }

        User userObj = userService.getUserByUserNameOrEmail(request.getUserNameOrEmail());
        if (userObj == null) {
            return responseGenerator.errorResponse(context,
                    messagePropertySource.getMessage("username.email.invalid"),
                    HttpStatus.BAD_REQUEST);
        }

        // Generate new random password
        String prefix = RandomString.make(4);
        int suffix = 100000 + new Random().nextInt(900000);
        String newPassword = prefix + suffix;

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        PasswordSendEvent mailEvent = new PasswordSendEvent(this, newPassword);
        mailEvent.setEmailTo(userObj.getEmail());
        applicationEventPublisher.publishEvent(mailEvent);

        message.setTo(userObj.getEmail());
        message.setSubject("Your New Temporary Password");
        message.setText("Your new password is: " + newPassword);
        emailSender.send(message);

        userObj.setPassword(PasswordUtil.getEncryptedPassword(newPassword));
        userService.update(userObj);

        return responseGenerator.successResponse(context,
                messagePropertySource.getMessage("password.send"),
                HttpStatus.OK);
    }
}