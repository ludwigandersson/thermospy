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

import com.luan.thermospy.server.db.LogSession;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class SessionDAO  extends AbstractDAO<LogSession> {

    public SessionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public LogSession findById(int id) {
        return get(id);
    }

    public LogSession create(LogSession session) {
        return persist(session);
    }

    public List<LogSession> findAll() {
        return list(namedQuery("Session.findAll"));
    }
    
    public boolean delete(int sessionId)
    {
       Query q = currentSession().createQuery("delete Session where id = "+sessionId);
       return q.executeUpdate() >= 1;
    }

    public List<LogSession> findAllOpen() {
       Query q = currentSession().createQuery("SELECT * FROM SESSION WHERE isOpen = TRUE");
       return q.list();
    }
}
