package edu.miu.backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private HttpServletRequest httpServletRequest;
    static Handler fileHandler = null;
    private static final Logger logger = Logger.getLogger("");

    @Around("execution(* edu.miu.backend.service.impl..*(..))\"")
    public Object logging(ProceedingJoinPoint jp) throws Throwable {
        String username = "anonymous";
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) httpServletRequest.getUserPrincipal();
        if (token != null) {
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        username = accessToken.getPreferredUsername();
        }

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = jp.proceed();
        stopWatch.stop();
        logger.info("Logging... User: " + username + " Method: " +jp.getSignature() + " executed in " + stopWatch.getTotalTimeMillis() + " ms.");
        return proceed;
    }

    @Bean
    public static void setup() {

        try {
            fileHandler = new FileHandler("./logfile.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleFormatter simple = new SimpleFormatter();
        fileHandler.setFormatter(simple);

        logger.addHandler(fileHandler);

    }
}
