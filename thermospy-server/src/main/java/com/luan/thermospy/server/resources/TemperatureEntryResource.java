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
import com.luan.thermospy.server.db.Temperatureentry;
import com.luan.thermospy.server.db.dao.TemperatureEntryDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/thermospy-server/get-temperature-entries")
@Produces(MediaType.APPLICATION_JSON)
public class TemperatureEntryResource {
    private final TemperatureEntryDAO temperatureDao;
    
    public TemperatureEntryResource(TemperatureEntryDAO dao) {
        this.temperatureDao = dao;
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/list/{id}")
    public List<Temperatureentry> findTemperatureEntry(@PathParam("id") IntParam id) {
        return temperatureDao.findAll(id.get());
    }
}
