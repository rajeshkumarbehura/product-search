package com.sellde.reward.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellde.reward.config.support.JsonToMapConverter;
import com.sellde.reward.config.support.MapToJsonConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableR2dbcAuditing
@EnableTransactionManagement
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ObjectMapper objectMapper;

    private final ConnectionFactory connectionFactory;


    public R2dbcConfig(@Qualifier("connectionFactory") ConnectionFactory connectionFactory,
                       ObjectMapper objectMapper){
        this.connectionFactory=connectionFactory;
        this.objectMapper=objectMapper;
    }

    /**
     * Adding converters for Json To Hashmap database column
     *
     * @return
     */
    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JsonToMapConverter(objectMapper));
        converters.add(new MapToJsonConverter(objectMapper));
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

}
