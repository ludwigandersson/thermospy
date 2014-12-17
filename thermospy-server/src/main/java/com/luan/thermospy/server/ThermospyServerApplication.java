package com.luan.thermospy.server;

import com.luan.thermospy.server.configuration.ThermospyServerConfiguration;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.luan.thermospy.server.health.TemplateHealthCheck;

public class ThermospyServerApplication extends Application<ThermospyServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new ThermospyServerApplication().run(args);
    }

    @Override
    public void run(ThermospyServerConfiguration configuration,
                    Environment environment) {

        ThermospyController controller = new ThermospyController();


        final GetTempResource tempResource = new GetTempResource(controller);
        final CameraControlResource cameraResource = new CameraControlResource(controller);
        final GetLastImage getLastImage = new GetLastImage(controller);
        final ImageBoundaryResource imgBoundaryResource = new ImageBoundaryResource(controller);
        final RefreshRateResource refreshRateResource = new RefreshRateResource(controller);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);


        environment.jersey().register(tempResource);
        environment.jersey().register(cameraResource);
        environment.jersey().register(getLastImage);
        environment.jersey().register(imgBoundaryResource);
        environment.jersey().register(refreshRateResource);
    }

    @Override
    public String getName() {
        return "thermospy-server";
    }

    @Override
    public void initialize(Bootstrap<ThermospyServerConfiguration> bootstrap) {
        // nothing to do yet
    }

}