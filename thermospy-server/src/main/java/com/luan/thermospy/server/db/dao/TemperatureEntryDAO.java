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
package com.luan.thermospy.server.db.dao;

import com.luan.thermospy.server.db.Temperatureentry;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class TemperatureEntryDAO extends AbstractDAO<Temperatureentry> {

    public TemperatureEntryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public Temperatureentry findById(int id) {
        return get(id);
    }

    public List<Temperatureentry> findAll(int i) {
        Query q = namedQuery("Temperatureentry.findBySessionId").setParameter("fkSessionId", i);
        return list(q);
    }

    public boolean deleteAllBySessionId(int id) {
       Query q = currentSession().createQuery("delete Temperatureentry where fkSessionId = "+id);
       return q.executeUpdate() >= 1;
    }
    
}
