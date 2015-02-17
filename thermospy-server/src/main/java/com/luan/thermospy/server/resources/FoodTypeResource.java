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
import com.luan.thermospy.server.db.Cut;
import com.luan.thermospy.server.db.Foodtype;
import com.luan.thermospy.server.db.dao.FoodTypeDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
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
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author ludde
 */
@Path("/thermospy-server/food-type")
@Produces(MediaType.APPLICATION_JSON)
public class FoodTypeResource {
    private final FoodTypeDAO foodTypeDAO;

    public FoodTypeResource(FoodTypeDAO foodTypeDAO) {
        this.foodTypeDAO = foodTypeDAO;
    }
    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Foodtype findFoodtypeById(@PathParam("id") IntParam id) {
        return foodTypeDAO.findById(id.get());
    }
    
    @GET
    @Timed
    @UnitOfWork
    @Path("/list")
    public List<Foodtype> findFoodtypes() {
        return foodTypeDAO.findAll();
    }
    
    @POST
    @Timed
    @UnitOfWork
    public Response createFoodType(Foodtype type)
    {
        Foodtype foodType = foodTypeDAO.create(type);
        return Response.ok(foodType).build();
    }
    
    @DELETE
    @Timed
    @UnitOfWork
    public Response deleteFoodType(Foodtype type)
    {
        boolean result = foodTypeDAO.delete(type);
        
        if (result) return Response.ok().build();
        else return Response.serverError().build();
    }
}
