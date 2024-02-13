package com.sellde.reward;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class SupplyRewardEngineApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(SupplyRewardEngineApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        var protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                .map(key -> "https").orElse("http");
        var serverPort = env.getProperty("server.port");
        var contextPath = (env.getProperty("spring.webflux.base-path")+"/");
        var hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("""   
                                                
                        ----------------------------------------------------------
                                Application '{}' is running! Access URLs:
                                Local: {}://localhost:{}{}swagger-ui.html
                                External: {}://{}:{}{}
                                Profile(s): {}
                        ----------------------------------------------------------
                                """,
                env.getProperty("spring.application.name"),
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                env.getActiveProfiles()
        );
    }
}
