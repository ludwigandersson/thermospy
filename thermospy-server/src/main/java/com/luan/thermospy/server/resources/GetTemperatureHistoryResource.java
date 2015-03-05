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
import com.luan.thermospy.server.core.Temperature;
import com.luan.thermospy.server.core.ThermospyController;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author ludde
 */
@Path("/thermospy-server/get-temperature-history")
@Produces(MediaType.APPLICATION_JSON)
public class GetTemperatureHistoryResource {
    
    private final ThermospyController controller;
    public GetTemperatureHistoryResource(ThermospyController controller) {
        this.controller = controller;
    }
    
    @GET
    @Timed
    @UnitOfWork
    public List<Temperature> find() {
        return controller.getTemperatureHistory();
    }
}
