package com.mt.faust;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mt.faust.comman.FaustExceptionMapper;
import com.mt.faust.dao.DAOObjFactory;
import com.mt.faust.resource.CRUDResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;


/**
 * Created by jaydeep.k on 03/10/17.
 */
@Slf4j
public class FaustApplication extends Application<FaustConfiguration> {


    public FaustApplication() {

    }

    @Override
    public void initialize(Bootstrap<FaustConfiguration> bootstrap) {
        super.initialize(bootstrap);

        // Enable configuration variable substitution with system property values
        final StrSubstitutor systemPropertyStrSubstitutor = new StrSubstitutor(StrLookup.systemPropertiesLookup());
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), systemPropertyStrSubstitutor));

        // Setting configuration from env variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        );


        bootstrap.addBundle(new SwaggerBundle<FaustConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(FaustConfiguration configuration) {
                SwaggerBundleConfiguration swaggerBundleConfiguration = configuration.getSwagger();
                return swaggerBundleConfiguration;
            }
        });

    }

    @Override
    public void run(FaustConfiguration faustConfiguration, Environment environment)
        throws Exception {
        environment.getObjectMapper().setPropertyNamingStrategy
            (PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DAOObjFactory.init(faustConfiguration.getDatabaseConfig());
        environment.jersey().register(CRUDResource.getInstance());
        environment.jersey().register(new FaustExceptionMapper());


    }

    public static void main(String[] args) throws Exception {
        new FaustApplication().run(args);
    }

}
