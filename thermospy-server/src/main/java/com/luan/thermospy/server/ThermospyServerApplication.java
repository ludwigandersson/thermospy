package com.luan.thermospy.server;

import com.luan.thermospy.server.actions.TakePhotoAction;
import com.luan.thermospy.server.configuration.ThermospyServerConfiguration;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.hal.impl.SevenSegmentOpticalRecognizer;
import com.luan.thermospy.server.hal.impl.WebcamDevice;
import com.luan.thermospy.server.resources.*;
import com.luan.thermospy.server.worker.WebcamWorker;
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

        ThermospyController controller = configuration.getController();
        SevenSegmentOpticalRecognizer recognizer = new SevenSegmentOpticalRecognizer();
        recognizer.setConfig(configuration.getDigitRecognizerConfig());
        WebcamDevice webcamDevice = new WebcamDevice();
        webcamDevice.setConfig(configuration.getCameraDeviceConfig());
        WebcamWorker worker = new WebcamWorker(controller, webcamDevice, recognizer);

        TakePhotoAction actionHandler = new TakePhotoAction(worker);

        controller.setCameraAction(actionHandler);

        final GetTempResource tempResource = new GetTempResource(controller);
        final CameraControlResource cameraResource = new CameraControlResource(controller);
        final GetLastImage getLastImage = new GetLastImage(webcamDevice);
        final ImageBoundaryResource imgBoundaryResource = new ImageBoundaryResource(controller);
        final RefreshRateResource refreshRateResource = new RefreshRateResource(controller);
        final CameraDeviceConfigResource cameraDeviceConfigResource = new CameraDeviceConfigResource(webcamDevice);
        final DigitRecognizerConfigResource drcResource = new DigitRecognizerConfigResource(recognizer);
        final ServiceStatusResource serviceStatusResource = new ServiceStatusResource(controller);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck("TEST");
        
        environment.healthChecks().register("template", healthCheck);


        environment.jersey().register(tempResource);
        environment.jersey().register(cameraResource);
        environment.jersey().register(getLastImage);
        environment.jersey().register(imgBoundaryResource);
        environment.jersey().register(refreshRateResource);
        environment.jersey().register(cameraDeviceConfigResource);
        environment.jersey().register(drcResource);
        environment.jersey().register(serviceStatusResource);
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