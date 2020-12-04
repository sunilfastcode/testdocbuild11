package com.fastcode.testdocbuild11.addons.registration.restcontrollers;

import com.fastcode.testdocbuild11.addons.email.application.mail.IEmailService;
import com.fastcode.testdocbuild11.application.core.authorization.tokenverification.ITokenVerificationAppService;
import com.fastcode.testdocbuild11.application.core.authorization.user.IUserAppService;
import com.fastcode.testdocbuild11.application.core.authorization.user.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.core.authorization.tokenverification.TokenverificationEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private PasswordEncoder pEncoder;

    @Autowired
    private ITokenVerificationAppService _tokenAppService;

    @Autowired
    @Qualifier("userAppService")
    private IUserAppService _userAppService;

    @Autowired
    private LoggingHelper logHelper;

    @Autowired
    private IEmailService _emailService;

    public static final long ACCOUNT_VERIFICATION_TOKEN_EXPIRATION_TIME = 86_400_000;

    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<HashMap<String, String>> registerUserAccount(
        @RequestBody CreateUserInput user,
        @RequestParam("clientUrl") final String clientUrl
    ) {
        FindUserByNameOutput foundUser = _userAppService.findByUserName(user.getUserName());

        if (foundUser != null) {
            logHelper.getLogger().error("There already exists a user with a name=%s", user.getUserName());
            throw new EntityExistsException(
                String.format("There already exists a user with a name=%s", user.getUserName())
            );
        }

        foundUser = _userAppService.findByEmailAddress(user.getEmailAddress());
        if (foundUser != null) {
            logHelper.getLogger().error("There already exists a user with a email=%s", user.getEmailAddress());
            throw new EntityExistsException(
                String.format("There already exists a user with a email=%s", user.getEmailAddress())
            );
        }

        user.setIsActive(true);
        user.setIsEmailConfirmed(false);
        user.setPassword(pEncoder.encode(user.getPassword()));

        CreateUserOutput output = _userAppService.create(user);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        sendVerificationEmail(clientUrl, output.getEmailAddress(), output.getId());
        String msg = "Account verfication link has been sent to " + user.getEmailAddress();
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/verifyEmail",
        method = RequestMethod.POST,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> verifyEmail(@RequestParam("token") final String token) {
        TokenverificationEntity tokenEntity = _tokenAppService.findByTokenAndType(token, "registration");

        Optional
            .ofNullable(tokenEntity)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid verification link.")));

        FindUserWithAllFieldsByIdOutput output = _userAppService.findWithAllFieldsById(tokenEntity.getUserId());
        Optional
            .ofNullable(output)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid verification link.")));

        if (new Date().after(tokenEntity.getExpirationTime())) {
            _tokenAppService.deleteToken(tokenEntity);
            _userAppService.delete(tokenEntity.getUserId());

            logHelper.getLogger().error("Token has expired, please register again");
            throw new EntityNotFoundException(String.format("Token has expired, please register again"));
        }

        output.setIsEmailConfirmed(true);
        _userAppService.updateUserData(output);
        _tokenAppService.deleteToken(tokenEntity);

        String msg = "User Verified!";
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/resendVerificationEmail/{username}",
        method = RequestMethod.POST,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> resendVerificationEmail(
        @PathVariable final String username,
        @RequestParam("clientUrl") final String clientUrl
    ) {
        FindUserByNameOutput foundUser = _userAppService.findByUserName(username);

        if (foundUser == null) {
            logHelper.getLogger().error("User does not exist with username=%s", username);
            throw new EntityExistsException(String.format("User does not exist with username=%s", username));
        }

        if (foundUser != null && Boolean.TRUE.equals(foundUser.getIsEmailConfirmed())) {
            logHelper.getLogger().error("User with username=%s is already verified.", username);
            throw new EntityExistsException(String.format("User with username=%s is already verified.", username));
        }

        sendVerificationEmail(clientUrl, foundUser.getEmailAddress(), foundUser.getId());

        String msg = "Account verfication link has been resent to " + foundUser.getEmailAddress();

        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    private void sendVerificationEmail(String clientUrl, String emailAddress, Long userId) {
        TokenverificationEntity tokenEntity = _tokenAppService.generateToken("registration", userId);

        String subject = "Account Verfication";
        String emailText =
            "To verify your account, click the link below:\n" +
            clientUrl +
            "/verify-email?token=" +
            tokenEntity.getToken();

        _emailService.sendEmail(_emailService.buildEmail(emailAddress, subject, emailText));
    }
}
