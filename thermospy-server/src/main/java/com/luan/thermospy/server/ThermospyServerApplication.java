package com.luan.thermospy.server;

import com.luan.thermospy.server.actions.SingleShotAction;
import com.luan.thermospy.server.configuration.ThermospyServerConfiguration;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.db.Cut;
import com.luan.thermospy.server.db.Foodtype;
import com.luan.thermospy.server.db.Session;
import com.luan.thermospy.server.db.Temperatureentry;
import com.luan.thermospy.server.db.dao.CutDAO;
import com.luan.thermospy.server.db.dao.FoodTypeDAO;
import com.luan.thermospy.server.db.dao.SessionDAO;
import com.luan.thermospy.server.db.dao.TemperatureEntryDAO;
import com.luan.thermospy.server.db.util.ThermospyHibernateUtil;
import com.luan.thermospy.server.hal.impl.SevenSegmentOpticalRecognizer;
import com.luan.thermospy.server.hal.impl.WebcamDevice;
/**
 * 
 * Copyright 2015 Ludwig Andersson
 * 
 * This file is part of Thermospy-server.
 *
 *  Thermospy-server is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-server.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import com.luan.thermospy.server.resources.*;
import com.luan.thermospy.server.worker.WebcamWorker;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.luan.thermospy.server.health.TemplateHealthCheck;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.eclipse.jetty.util.log.Log;
import org.hibernate.Query;

/**
 * Main app entrance. Setting up all relationships etc
 */
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

        SingleShotAction actionHandler = new SingleShotAction(worker);

        controller.setCameraAction(actionHandler);
        controller.setSessionFactory(hibernate.getSessionFactory());
        

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
        
        // Hibernate DAO types
        final SessionDAO dao = new SessionDAO(hibernate.getSessionFactory());
        final TemperatureEntryDAO tempDAO = new TemperatureEntryDAO(hibernate.getSessionFactory());
        final CutDAO cutDAO = new CutDAO(hibernate.getSessionFactory());
        final FoodTypeDAO foodTypeDAO = new FoodTypeDAO(hibernate.getSessionFactory());
        
        controller.setTemperatureDao(tempDAO);
        environment.jersey().register(new TemperatureEntryResource(tempDAO));
        environment.jersey().register(new SessionResource(dao, controller));
        environment.jersey().register(new CutResource(cutDAO));
        environment.jersey().register(new FoodTypeResource(foodTypeDAO));
        
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
        
        bootstrap.addBundle(hibernate);
    }
    
    private final HibernateBundle<ThermospyServerConfiguration> hibernate = new HibernateBundle<ThermospyServerConfiguration>(Session.class, Temperatureentry.class, Cut.class, Foodtype.class) {
    @Override
    public DataSourceFactory getDataSourceFactory(ThermospyServerConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
};

}
