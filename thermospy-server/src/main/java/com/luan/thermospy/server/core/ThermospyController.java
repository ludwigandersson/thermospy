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
package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.actions.CameraAction;
import com.luan.thermospy.server.db.Session;
import com.luan.thermospy.server.db.Temperatureentry;
import com.luan.thermospy.server.db.dao.TemperatureEntryDAO;
import io.dropwizard.db.DataSourceFactory;
import java.io.File;
import java.util.Date;
import java.util.List;
import org.eclipse.jetty.util.log.Log;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * The man in the middle who connects all the dots.
 * The Controller contains the settings and actions and is accessed fromm
 * different threads.
 */
public class ThermospyController {
    @JsonProperty
    private int refreshRate = 0;
    @JsonIgnore
    final private Object myLock = new Object();
    @JsonIgnore
    CameraAction camera = null;
    @JsonIgnore
    private int temperature = Integer.MIN_VALUE;
    @JsonIgnore
    private Boundary displayBoundary = new Boundary(0,0,0,0);
    @JsonIgnore
    private ServerStatus serverStatus = ServerStatus.OK;
    @JsonIgnore
    private Session logSession = null;
    @JsonIgnore
    TemperatureEntryDAO temperatureEntryDao = null;
    @JsonIgnore
    private SessionFactory sessionFactory;
    
    
    public int getTemperature() {
        synchronized(myLock){
            return temperature;
        }
    }

    public void setTemperature(int temperature) {
        synchronized(myLock){
            if (this.temperature != temperature)
            {
                String fromTemperature = this.temperature == Integer.MIN_VALUE ? "--" : Integer.toString(this.temperature);
                String toTemperature = temperature == Integer.MIN_VALUE ? "--" : Integer.toString(temperature);
                Log.getLog().info("Temperature changed from " + fromTemperature + " to " + toTemperature );
                this.temperature = temperature;
            }
            
            if (this.logSession != null && this.temperature != Integer.MIN_VALUE)
            {
                org.hibernate.Session s = sessionFactory.openSession();
                Transaction tx = null;
                try {
                    
                    tx = s.beginTransaction();
                    
                    Temperatureentry entry = new Temperatureentry();
                    entry.setTimestamp(new Date());
                    entry.setFkSessionId(logSession.getId());
                    entry.setTemperature((double)temperature);
                    
                    s.save(entry);
                    tx.commit();
                   
                } catch (Exception e) {
                    
                    if (tx != null) tx.rollback();
                    this.serverStatus = ServerStatus.INTERNAL_SERVER_ERROR;
                }
                s.close();
                
            }
        }
    }

    public Boundary getDisplayBoundary() {
        synchronized(myLock){
            return displayBoundary;
        }
    }

    public void setDisplayBoundary(Boundary displayBoundary) {
        synchronized(myLock){
            this.displayBoundary = displayBoundary;
        }
    }

    public void start() {
        camera.start();
    }
    public boolean singleshot() {
        return camera.singleshot();
    }
    
    public void stop() {
        camera.stop();
        temperature = Integer.MIN_VALUE;
        
    }
    public void setCameraAction(CameraAction actionHandler)
    {
        this.camera = actionHandler;
    }

    public int getRefreshRate() {
        synchronized(myLock) {
            return refreshRate;
        }
    }

    public void setRefreshRate(int refreshRate) {
        synchronized(myLock) {
            this.refreshRate = refreshRate;
        }
    }

    public boolean getServiceStatus() {
        return camera.isRunning();
    }

    /**
     * @return the serverStatus
     */
    public ServerStatus getServerStatus() {
        synchronized(myLock) {
            return serverStatus;
        }
    }

    /**
     * @param serverStatus the serverStatus to set
     */
    public void setServerStatus(ServerStatus serverStatus) {
        synchronized(myLock) {
            this.serverStatus = serverStatus;
        }
    }

    public Session getLogSession() {
        synchronized (myLock) {
            return this.logSession;
        }
    }

    public void setLogSession(Session session) {
        synchronized(myLock) {
            this.logSession = session;
        }
    }

    public void setTemperatureDao(TemperatureEntryDAO tempDAO) {
        this.temperatureEntryDao = tempDAO;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
    
    
    
    
    
}
