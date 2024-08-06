package com.cognito.volunteer_managment_system.core.security.controller;

import com.cognito.volunteer_managment_system.core.security.controller.validation.ValidPassword;
import com.cognito.volunteer_managment_system.core.security.dto.password.ChangePassword;
import com.cognito.volunteer_managment_system.core.security.dto.user.UserResponse;
import com.cognito.volunteer_managment_system.core.security.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePassword changePassword,
            Authentication connectedUser
    ) {
        userService.changePassword(changePassword,connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password/forgetPassword")
    public ResponseEntity<?> forgetPassword(
            Authentication connectedUser
    ) throws MessagingException {
        userService.forgetPassword(connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/password/resetPassword")
    public ResponseEntity<?> resetPassword(
            Authentication connectedUser,
            @RequestParam String code,
            @RequestParam @ValidPassword String newPassword,
            @RequestParam String confirmationPassword
    ) throws MessagingException {
        userService.resetPassword(connectedUser,code,newPassword,confirmationPassword);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivatedAccount")
    public ResponseEntity<?> deactivateAccount(
            Authentication connectedUser
    ) {
        userService.deactivateAccount(connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/showUserInformation")
    public ResponseEntity<UserResponse> showUserInformation(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(userService.showUserInformation(connectedUser));
    }
}
