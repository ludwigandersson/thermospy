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
import com.luan.thermospy.server.db.Foodtype;
import com.luan.thermospy.server.db.Session;
import com.luan.thermospy.server.db.Temperatureentry;
import com.luan.thermospy.server.db.dao.SessionDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ludde
 */

@Path("/thermospy-server/log-sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final SessionDAO sessionDao;
    public SessionResource(SessionDAO dao) {
        sessionDao = dao;
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
    public List<Session> find() {
        return sessionDao.findAll();
    }
    
    @DELETE
    @Timed
    @UnitOfWork
    public Response delete(Session session)
    {
        boolean result = sessionDao.delete(session);
        
        if (result) return Response.ok().build();
        else return Response.serverError().build();
    }
}
