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
package com.luan.thermospy.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.ServerStatus;
import com.luan.thermospy.server.core.ThermospyController;

import com.luan.thermospy.server.db.LogSession;
import com.luan.thermospy.server.db.Temperatureentry;

import com.luan.thermospy.server.db.dao.SessionDAO;
import com.luan.thermospy.server.db.dao.TemperatureEntryDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Query;
import org.hibernate.Transaction;

@Path("/thermospy-server/log-session")
@Produces(MediaType.APPLICATION_JSON)
public class LogSessionResource {
    private final SessionDAO sessionDao;
    private final ThermospyController controller;
    private final TemperatureEntryDAO temperatureEntryDao;
    public LogSessionResource(SessionDAO dao, TemperatureEntryDAO temperatureEntryDao, ThermospyController controller) {
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
        LogSession s = controller.getLogSession();
        if (s != null) return Response.ok(s).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public LogSession findSessionId(@PathParam("id") IntParam id) {
        return sessionDao.findById(id.get());
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/list")
    public List<LogSession> find() {
        return sessionDao.findAll();
    }
    
    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response delete(@PathParam("id") IntParam id)
    {
        LogSession s = controller.getLogSession();
        if (s != null && s.getId() == id.get())
        {
            controller.setLogSession(null);
        }
        temperatureEntryDao.deleteAllBySessionId(id.get());  
        boolean result = sessionDao.delete(id.get());
        
        if (result) return Response.ok(id.get()).build();
        else return Response.serverError().build();
    }
    @POST
    @Timed
    @UnitOfWork
    @Path("/update/{id}")
    public Response update(LogSession s)
    {
        LogSession session = sessionDao.create(s);
        if (session != null) {
            if (controller.getLogSession().getId() == session.getId())
            {
               controller.setLogSession(session);
            }
            return Response.ok(session).build();
        }
        
        return Response.serverError().build();
        
    }
    @POST
    @Timed
    @UnitOfWork
    @Path("/start")
    public Response startSession(LogSession session)
    {
        LogSession s = sessionDao.create(session);
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
        LogSession s = controller.getLogSession();
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

    /**
     * Helper, closes any open log sessions.
     * 1. Fetch all open log sessions.
     * 2. Set isOpen = false and close them.
     * 
     */
    private void closeOpenLogSessions() {
        org.hibernate.Session dbSession = null;
        try {
            dbSession = controller.getSessionFactory().openSession();
            Transaction tx = null;
            final List<LogSession> list = new LinkedList<>();
            try {

                tx = dbSession.beginTransaction();
                Query q = dbSession.createQuery("FROM Session S WHERE isOpen = TRUE");

                q.list().stream().forEach((o) -> {
                    list.add((LogSession)o);
                });

                tx.commit();

            } catch (Exception e) {
                if (tx != null) 
                    tx.rollback();
            }
            dbSession.close();
            dbSession = null;    
            // Update 
            if (list.size() > 0) {
                dbSession = controller.getSessionFactory().openSession();
                try {
                    tx = dbSession.beginTransaction();
                    for (LogSession session : list) {
                        String hql = "UPDATE Session set isOpen = false "  +
                                "WHERE id = :session_id";
                        Query query = dbSession.createQuery(hql);
                        query.setParameter("session_id", session.getId());
                        query.executeUpdate();
                    }
                    tx.commit();
                } catch (Exception e) {
                if (tx != null) 
                    tx.rollback();
                }
            }
        } 
        catch (Exception e)
        {
        }
        finally {
            if (dbSession != null)
            {
                dbSession.close();
            }
      }
    }
}
