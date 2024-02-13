package com.sellde.reward.config;

import com.sellde.reward.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Slf4j
public class CustomWebFluxConfig implements WebFluxConfigurer {

    private final Environment env;

    public CustomWebFluxConfig(Environment env) {
        this.env = env;
        addDefaultActiveProfile();
    }

    /**
     * Cors setting
     *
     * @param corsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name())
                .maxAge(3600);
    }

    /**
     * Set datetime to ISO format
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        var registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    /**
     * Set default profile to Local when no profile defined to
     */
    public void addDefaultActiveProfile() {
        var activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length < 1) {
            log.info("Application is running with default profile : local");
            System.setProperty(Constant.ACTIVE_PROFILE, Constant.ENV_LOCAL);
        }
    }
}
