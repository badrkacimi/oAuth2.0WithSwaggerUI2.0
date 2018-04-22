package com.authentication.api;

import com.authentication.model.UserTokenSession;
import com.authentication.model.Users;
import com.authentication.service.UserTokenSessionService;
import com.authentication.service.UserTokenSessionServiceImpl;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/oauth")
@Api(value="Hivepod Authentication API")
public class AuthenticationAPI {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationAPI.class);

    @ApiOperation(value = "all users ")
    @GetMapping("/stagiares")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public Users users() {
        Users users = new Users();
        users.setId(1);
        users.setName("BADR");
        users.setSalary(3400);
        users.setTeamName("GRP");


        return users;
    }
    
/*
 * Token generation, session and cookies management
 * */

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Value("${config.oauth2.tokenTimeout}")
    private long tokenExpiryTime;

    @Autowired
    private UserTokenSessionServiceImpl userTokenSessionService;


    @ApiOperation(value = "Authenticated User Login", response = UserTokenSession.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserTokenSession.class),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public ResponseEntity<UserTokenSession> login(@RequestHeader HttpHeaders httpHeaders, Principal principal, HttpServletRequest httpServletRequest) {

        String username = principal.getName();
        UserTokenSession userTokenSession = buildUserTokenSession(principal, httpHeaders);
        userTokenSession = userTokenSessionService.saveUserTokenSessionMapping(userTokenSession);

        LOGGER.info("User "+username+" successfully logged in. User, Token and Session mapping stored."+userTokenSession);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Message", "Success");
        responseHeaders.add("Set-Cookie", userTokenSession.getSessionId());

        return new ResponseEntity(userTokenSession, responseHeaders, HttpStatus.OK);
    }

    /**
     * Build Token session using {@link Principal} and {@link HttpHeaders}
     */
    private UserTokenSession buildUserTokenSession(Principal principal, HttpHeaders httpHeaders) {

        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) ((OAuth2Authentication) principal).getDetails();
        String tokenValue = oAuth2AuthenticationDetails.getTokenValue();
        String username = principal.getName();
        String [] sessionId = new String[1];

        if (Objects.nonNull(httpHeaders.get("cookie"))) {
            sessionId = httpHeaders.get("cookie").get(0).split(";");
        }else {
            LOGGER.info("User " + username + " cookie not found. JSessionId not set.");
     /**
             * Swagger2 does not support cookie for that putting default session id.
             */
            sessionId[0] = "JSEESION-ID";
        }
        UserTokenSession userTokenSession = new UserTokenSession(username, tokenValue, sessionId[0], tokenExpiryTime);
        return userTokenSession;
    }
}
