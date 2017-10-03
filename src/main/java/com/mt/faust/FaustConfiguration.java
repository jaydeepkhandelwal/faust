package com.mt.faust;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt.faust.config.DatabaseConfig;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaustConfiguration extends Configuration{
    @Getter
    @Setter
    @NotNull
    private SwaggerBundleConfiguration swagger;

    @Getter
    @Setter
    @NotNull
    @JsonProperty("database")
    private DatabaseConfig databaseConfig;
}
