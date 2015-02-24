/*
 * Copyright (C) 2015 ludde
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.luan.thermospy.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.ServerStatus;
import com.luan.thermospy.server.core.ThermospyController;

import com.luan.thermospy.server.db.Session;
import com.luan.thermospy.server.db.Temperatureentry;

import com.luan.thermospy.server.db.dao.SessionDAO;
import com.luan.thermospy.server.db.dao.TemperatureEntryDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import java.util.Date;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Transaction;

/**
 *
 * @author ludde
 */

@Path("/thermospy-server/log-session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final SessionDAO sessionDao;
    private final ThermospyController controller;
    private final TemperatureEntryDAO temperatureEntryDao;
    public SessionResource(SessionDAO dao, TemperatureEntryDAO temperatureEntryDao, ThermospyController controller) {
        sessionDao = dao;
        this.controller = controller;
        this.temperatureEntryDao = temperatureEntryDao;
        closeOpenLogSessions();
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/active")
    public Response getActiveSession() {
        Session s = controller.getLogSession();
        if (s != null) return Response.ok(s).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Session findSessionId(@PathParam("id") IntParam id) {
        return sessionDao.findById(id.get());
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/list")
    public List<Session> find() {
        return sessionDao.findAll();
    }
    
    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response delete(@PathParam("id") IntParam id)
    {
        Session s = controller.getLogSession();
        if (s != null && s.getId() == id.get())
        {
            controller.setLogSession(null);
        }
        boolean result = temperatureEntryDao.deleteAllBySessionId(id.get());  
        result = result && sessionDao.delete(id.get());
        
        if (result) return Response.ok(id.get()).build();
        else return Response.serverError().build();
    }
    
    @POST
    @Timed
    @UnitOfWork
    @Path("/start")
    public Response startSession(Session session)
    {
        Session s = sessionDao.create(session);
        s.setIsOpen(true);
        s.setStartTimestamp(new Date());
        controller.setLogSession(s);
        return Response.ok(s).build();
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/stop")
    public Response stopSession()
    {
        Session s = controller.getLogSession();
        controller.setLogSession(null);
        if (s != null) {
            s.setEndTimestamp(new Date());
            s.setIsOpen(false);
            s = sessionDao.create(s);
            return Response.ok(s).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void closeOpenLogSessions() {
        
    }
}
