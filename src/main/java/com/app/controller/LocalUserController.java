package com.app.controller;
import com.app.entity.LocalUser;
import com.app.service.FirebaseService;
import com.app.service.LocalUserService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")

@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin(origins = "http://127.0.0.1:5501", allowedHeaders = "*")
public class LocalUserController {

    @Autowired
    private LocalUserService userService;

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/register")
    public LocalUser registerUser(@RequestBody LocalUser user, @RequestHeader("Authorization") String authHeader) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");

        firebaseService.verifyIdToken(idToken);

        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload, @RequestHeader("Authorization") String authHeader) {
        try {

            String idToken = authHeader.replace("Bearer ", "");

            FirebaseToken decodedToken = firebaseService.verifyIdToken(idToken);
            String uid = decodedToken.getUid();


            String phoneNumber = payload.get("phoneNumber");
            String firebaseId = payload.get("firebaseId");


            if (!uid.equals(firebaseId)) {
                return ResponseEntity.status(401).body("Unauthorized: Firebase ID mismatch.");
            }


            UserRecord userRecord = firebaseService.getUserByUid(uid);
            String verifiedPhoneNumber = userRecord.getPhoneNumber();


            if (verifiedPhoneNumber == null || !verifiedPhoneNumber.equals(phoneNumber)) {
                return ResponseEntity.status(401).body("Unauthorized: Phone number mismatch.");
            }


            LocalUser existingUser = userService.findByPhoneNumber(phoneNumber);
            if (existingUser == null) {

                return ResponseEntity.status(404).body("User not found.");
            }

            Map<String, Object> success = new LinkedHashMap<>();
            success.put("message", "Successfully logged in");
            success.put("details", existingUser);

            return new ResponseEntity<>(success, HttpStatus.OK);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Unauthorized: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }


}

